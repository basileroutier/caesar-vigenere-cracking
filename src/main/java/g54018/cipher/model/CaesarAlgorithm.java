/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.cipher.model;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author basile
 */
public class CaesarAlgorithm implements Caeesar{

    private final String alphabetDefault = "abcdefghijklmnopqrstuvwxyz";
    private final String chiffreDefault = "0123456789";
    private final double[] letterProbabilitie = {0.08167, 0.01492, 0.02782, 0.04253, 0.12702, 0.02228, 0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025, 0.02406, 0.06749, 0.07507, 0.01929, 0.0095, 0.05987, 0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150, 0.01974, 0.00074};
    private breakCipher breakCiph;
    
    /**
     * Initliaze the instance of the class of break cipher to use the method to crack the cipher text
     */
    public CaesarAlgorithm() {
        this.breakCiph = new breakCipher();
    }

    /**
     * Cipher all the text inside the input file and output the result inside the destination
     * It encrypt the line text for wich line with a shift gives
     * @param input String: readable file path
     * @param output String: destination of output after cipher the text
     * @param decalage int: the shift for the cipher
     * @throws IOException exception launch when we couldn't open or read the intput or output file
     */
    public void outputCaesarCipher(String input, String output, int decalage) throws IOException {
        Scanner in = new Scanner(new FileReader(input, StandardCharsets.UTF_8));
        PrintStream out = new PrintStream(new FileOutputStream(output));

        var pr = new Preprocess();
        while (in.hasNextLine()) {
            String message = pr.preprocessLine(in.nextLine());
            out.println(caesarCipher(message, decalage, false));
        }
    }

    /**
     * Decipher all the text inside the input file and output the result inside the destination
     * It decrypt the line text for wich line with a shift gives
     * @param input String: readable file path
     * @param output String: destination of output after decipher the text
     * @param decalage int: the shift for the cipher
     * @throws IOException exception launch when we couldn't open or read the intput or output file
     */
    public void outputCaesarDecipher(String input, String output, int decalage) throws IOException {
        Scanner in = new Scanner(new FileReader(input, StandardCharsets.UTF_8));
        PrintStream out = new PrintStream(new FileOutputStream(output));
        while (in.hasNextLine()) {
            String message = in.nextLine();
            out.println(decipherCesar(message, decalage));
        }
    }

    /**
     * Decipher all the text with a frequential analysis of the text message inside the input file and output the result inside the destination
     * It decrypt the line text for wich line with a probable shift finded
     * @param input String: readable file path
     * @param output String: destination of output after decipher the text
     * @throws IOException exception launch when we couldn't open or read the intput or output file
     */
    public void outputCaesarBreakCipher(String input, String output) throws IOException {
        Scanner in = new Scanner(new FileReader(input, StandardCharsets.UTF_8));
        PrintStream out = new PrintStream(new FileOutputStream(output));
        
        while (in.hasNextLine()) {
            String message = in.nextLine();
            int decalage = probableOffsetMessage(message);
            out.println(decipherCesar(message, decalage));
        }
    }

    /**
     * Check the position of the number in the text. If the text is cipher, it will make a different check for decipher cause the number of letter is lower that the alphabet number
     * Make the same thing that the characterPos for the alphabet but only for number
     * @param actualPosNumber int: position of the number in the String number
     * @param decalage int: shift to know the real position of the index
     * @param isCipher boolean: to know if the text is cipher or not
     * @return the real position of the number in the String
     */
    private int characterNumberIndex(int actualPosNumber, int decalage, boolean isCipher) {
        int characterPos;
        if (isCipher) {
            characterPos = (actualPosNumber - (decalage)) % chiffreDefault.length();
            if (characterPos < 0) {
                characterPos += chiffreDefault.length();
            }
        } else {
            characterPos = (decalage + actualPosNumber) % chiffreDefault.length();
        }
        return characterPos;
    }

    /**
     * Cipher the message with the shift give in parameter.
     * browse all the character of the message and check if the character si not a space or a number
     * if it is, it will get the current position of the character and add the shift inside the new position.
     * Store the new character inside a String
     * Else if it is a number it will do the same
     * If it is a space bar, it will store it too
     * @param message String: message of the line that will be cipher
     * @param decalage int: shift to change the position of wich character
     * @param isCipher boolean: check if it is already cipher for the number position
     * @return the the new string message who are cipher
     */
    public String caesarCipher(String message, int decalage, boolean isCipher) {
        String cipherText = "";
        for (var i = 0; i < message.length(); i++) {
            if (message.charAt(i) != ' ' && chiffreDefault.indexOf(message.charAt(i)) == -1) {
                int positionCharacterAlph = alphabetDefault.indexOf(message.charAt(i));
                int characterPos = (decalage + positionCharacterAlph) % alphabetDefault.length();
                char character = alphabetDefault.charAt(characterPos);
                cipherText = cipherText + character;
            } else if (chiffreDefault.indexOf(message.charAt(i)) != -1) {
                int positionCharacterAlph = chiffreDefault.indexOf(message.charAt(i));
                int characterPos = characterNumberIndex(positionCharacterAlph, decalage, isCipher);
                char character = chiffreDefault.charAt(characterPos);
                cipherText = cipherText + character;
            } else {
                cipherText = cipherText + " ";
            }
        }
        return cipherText;
    }

    /**
     * Decipher the text with the caesar cipher algorithm
     * It juste give the ciphered message and change the shift by a new one
     * Cahnge the shift is for get the original position of the index of wich character of the message
     * @param message String: message cipher
     * @param decalage int: shift use
     * @return the decipher text
     */
    public String decipherCesar(String message, int decalage) {
        return caesarCipher(message, 26 - (decalage % 26), true);
    }

    /**
     * Get the probable offset of the message
     * @param cipherMessage String: ciphered text
     * @return the probable offset
     */
    private int probableOffsetMessage(String cipherMessage) {
        return breakCiph.breakCipher(cipherMessage);
    }
}
