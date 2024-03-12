package encriptacion;

import java.util.Scanner;

public class Enigma {
    private static final String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public Enigma(){}

    public static final String ALPHABET = "abcdefghijklmnñopqrstuvwxyz";

    // create encryptData() method for encrypting user input string with given shift key
    public static String encryptData(String inputStr, int shiftKey) {
        // convert inputStr into lower case
        inputStr = inputStr.toLowerCase();
        // encryptStr to store encrypted data
        StringBuilder encryptStr = new StringBuilder();
        // use for loop for traversing each character of the input string
        for (int i = 0; i < inputStr.length(); i++) {
            // get position of each character of inputStr in ALPHABET
            int pos = ALPHABET.indexOf(inputStr.charAt(i));

            // check if the character is found in ALPHABET
            if (pos != -1) {
                // get encrypted char for each char of inputStr
                int encryptPos = (shiftKey + pos) % 27;
                char encryptChar = ALPHABET.charAt(encryptPos);
                // add encrypted char to encrypted string
                encryptStr.append(encryptChar);
            } else {
                // add the character unencrypted
                encryptStr.append(inputStr.charAt(i));
            }
        }
        // return encrypted string
        return encryptStr.toString();
    }


    // create decryptData() method for decrypting user input string with given shift key
    public static String decryptData(String inputStr, int shiftKey) {
        // convert inputStr into lower case
        inputStr = inputStr.toLowerCase();

        // decryptStr to store decrypted data
        StringBuilder decryptStr = new StringBuilder();

        // use for loop for traversing each character of the input string
        for (int i = 0; i < inputStr.length(); i++) {
            char currentChar = inputStr.charAt(i);

            // check if the character is empty
            if (Character.isWhitespace(currentChar) || currentChar == '\n' || currentChar == '\t') {
                // append empty character directly to the decrypted string
                decryptStr.append(currentChar);
            } else {
                // get position of each character of inputStr in ALPHABET
                int pos = ALPHABET.indexOf(currentChar);

                // get decrypted char for each char of inputStr
                int decryptPos = (pos - shiftKey) % 27;

                // if decryptPos is negative
                if (decryptPos < 0) {
                    decryptPos = ALPHABET.length() + decryptPos;
                }
                char decryptChar = ALPHABET.charAt(decryptPos);

                // add decrypted char to decrypted string
                decryptStr.append(decryptChar);
            }
        }
        // return decrypted string
        return decryptStr.toString();
    }



    // main() method start
    public static void main(String[] args)
    {
        // create an instance of Scanner class
        Scanner sc = new Scanner(System.in);

        // take input from the user
        System.out.println("Enter a string for encryption using Caesar Cipher: ");
        String inputStr = sc.nextLine();

        System.out.println("Enter the value by which each character in the plaintext message gets shifted: ");
        int shiftKey = Integer.valueOf(sc.nextLine());

        System.out.println("Encrypted Data ===> "+encryptData(inputStr, shiftKey));
        System.out.println("Decrypted Data ===> "+decryptData(encryptData(inputStr, shiftKey), shiftKey));

        // close Scanner class object
        sc.close();
    }

}
