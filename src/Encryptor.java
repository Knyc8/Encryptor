public class Encryptor
{
    /** A two-dimensional array of single-character strings, instantiated in the constructor */
    private String[][] letterBlock;

    /** The number of rows of letterBlock, set by the constructor */
    private int numRows;

    /** The number of columns of letterBlock, set by the constructor */
    private int numCols;

    /** Constructor*/
    public Encryptor(int r, int c)
    {
        letterBlock = new String[r][c];
        numRows = r;
        numCols = c;
    }

    public String[][] getLetterBlock()
    {
        return letterBlock;
    }

    /** Places a string into letterBlock in row-major order.
     *
     *   @param str  the string to be processed
     *
     *   Postcondition:
     *     if str.length() < numRows * numCols, "A" in each unfilled cell
     *     if str.length() > numRows * numCols, trailing characters are ignored
     */
    public void fillBlock(String str)
    {
        String temp = str;
        if (temp.length() < numRows * numCols)
        {
            for (int i = 0; i < numRows * numCols - str.length(); i++)
            {
                temp += "A";
            }
        }

        int indexOfStr = 0;
        for (int row = 0; row < numRows; row++)
        {
            for (int col = 0; col < numCols; col++)
            {
                letterBlock[row][col] = "" + temp.substring(indexOfStr, indexOfStr+1);
                indexOfStr++;
            }
        }
    }

    /** Extracts encrypted string from letterBlock in column-major order.
     *
     *   Precondition: letterBlock has been filled
     *
     *   @return the encrypted string from letterBlock
     */
    public String encryptBlock()
    {
        String encryptedStr = "";
        for (int col = 0; col < letterBlock[0].length; col++)
        {
            for (int row = 0; row < letterBlock.length; row++)
            {
                encryptedStr += letterBlock[row][col];
            }
        }
        return encryptedStr;
    }

    /** Encrypts a message.
     *
     *  @param message the string to be encrypted
     *
     *  @return the encrypted message; if message is the empty string, returns the empty string
     */
    public String encryptMessage(String message)
    {
        int arrayArea = numRows*numCols;
        String temp = message;
        if (message.length()%arrayArea != 0) {
            for (int i = 0; i < arrayArea - message.length() % arrayArea; i++) {
                temp += "A";
            }
        }

        String encryptedMessage = "";
        for (int i = arrayArea; i <= temp.length(); i+=arrayArea)
        {
            fillBlock(temp.substring(i-arrayArea, i));
            encryptedMessage += encryptBlock();
        }

        return encryptedMessage;
    }

    /**  Decrypts an encrypted message. All filler 'A's that may have been
     *   added during encryption will be removed, so this assumes that the
     *   original message (BEFORE it was encrypted) did NOT end in a capital A!
     *
     *   NOTE! When you are decrypting an encrypted message,
     *         be sure that you have initialized your Encryptor object
     *         with the same row/column used to encrypted the message! (i.e.
     *         the “encryption key” that is necessary for successful decryption)
     *         This is outlined in the precondition below.
     *
     *   Precondition: the Encryptor object being used for decryption has been
     *                 initialized with the same number of rows and columns
     *                 as was used for the Encryptor object used for encryption.
     *
     *   @param encryptedMessage  the encrypted message to decrypt
     *
     *   @return  the decrypted, original message (which had been encrypted)
     *
     *   TIP: You are encouraged to create other helper methods as you see fit
     *        (e.g. a method to decrypt each section of the decrypted message,
     *         similar to how encryptBlock was used)
     */
    public String decryptMessage(String encryptedMessage)
    {
        int arrayArea = numCols * numRows;
        String message = "";
        for (int i = arrayArea; i <= encryptedMessage.length(); i+=arrayArea)
        {
            String temp = encryptedMessage.substring(i-arrayArea, i);
            String[][] decryptBlock = new String[numCols][numRows];
            int indexOfStr = 0;
            for (int row = 0; row < numCols; row++)
            {
                for (int col = 0; col < numRows; col++)
                {
                    decryptBlock[row][col] = "" + temp.substring(indexOfStr, indexOfStr+1);
                    indexOfStr++;
                }
            }
            for (int col = 0; col < decryptBlock[0].length; col++)
            {
                for (int row = 0; row < decryptBlock.length; row++)
                {
                    message += decryptBlock[row][col];
                }
            }
        }

        while(message.endsWith("A"))
        {
            message = message.substring(0, message.length()-1);
        }
        return message;
    }
}