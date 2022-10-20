/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.cipher.model;

import g54018.cipher.Model.BreakCipherVigenere;
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
public class VigenereAlgorithm implements Vigenere{
    
    private String alphabetDefault = "abcdefghijklmnopqrstuvwxyz";
    private BreakCipherVigenere breakVigenere;

    public VigenereAlgorithm() {
        breakVigenere = new BreakCipherVigenere();
    }
    
    /**
     * Cipher all the text inside the input file and output the result inside the destination
     * It encrypt the line text for wich line with a key give
     * @param input String: readable file path
     * @param output String: destination of output after cipher the text
     * @param key String: the key for the cipher
     * @throws IOException exception launch when we couldn't open or read the intput or output file
     */
    @Override
    public void outputVigenereCipher(String input, String output, String key) throws IOException {
        Scanner in = new Scanner(new FileReader(input, StandardCharsets.UTF_8));
        PrintStream out = new PrintStream(new FileOutputStream(output));

        var pr = new Preprocess();
        while (in.hasNextLine()) {
            String message = pr.preprocessLine(in.nextLine());
            out.println(cipherVigenere(message, key));
        }
    }
    
    /**
     * Decipher all the text inside the input file and output the result inside the destination
     * It decrypt the line text for wich line with a key give
     * @param input String: readable file path
     * @param output String: destination of output after cipher the text
     * @param key String: the key for the cipher
     * @throws IOException exception launch when we couldn't open or read the intput or output file
     */
    @Override
    public void outputVigenereDecipher(String input, String output, String key) throws IOException {
        Scanner in = new Scanner(new FileReader(input, StandardCharsets.UTF_8));
        PrintStream out = new PrintStream(new FileOutputStream(output));
        
        var pr = new Preprocess();
        while (in.hasNextLine()) {
            String message = pr.preprocessLine(in.nextLine());
            out.println(decipherVigenere(message, key));
        }
    }
    
    /**
     * Decipher all the text inside the input file and output the result inside the destination
     * It decrypt the line text for wich line with a probable key finded with frequential analysis
     * @param input String: readable file path
     * @param output String: destination of output after cipher the text
     * @throws IOException exception launch when we couldn't open or read the intput or output file
     */
    @Override
    public void outputVigenereBreakCipher(String input, String output) throws IOException {
        Scanner in = new Scanner(new FileReader(input, StandardCharsets.UTF_8));
        PrintStream out = new PrintStream(new FileOutputStream(output));
        
        var pr = new Preprocess();
        while (in.hasNextLine()) {
            String message = pr.preprocessLine(in.nextLine());
            String key = breakVigenere.decipherWithSizeOfKey(message);
            out.println(decipherVigenere(message, key));
        }
    }
    
    
    /**
     * Cipher the message with the key give in parameter.
     * browse all the character of the message and check if the character si not a space
     * if it is, it will get the current position of the character and add the position of the character key inside the new position.
     * Store the new character inside a String
     * If it is a space bar, it will store it too
     * @param message String: message of the line that will be cipher
     * @param key String: the key for the cipher
     * @return the the new string message who are cipher
     */
    public String cipherVigenere(String message, String key){
        String cipherText = "";
        int k=0;
        for (var i = 0; i < message.length(); i++) {
            if (message.charAt(i) != ' ') {
                System.out.println(message);
                int positionCharacterMsg = alphabetDefault.indexOf(message.charAt(i));
                int positionCharacterKey=0;
                if(key.charAt(k%key.length())== ' '){
                    int j=k;
                    while(key.charAt(j%key.length())== ' '){
                        j++;
                    }
                    positionCharacterKey = alphabetDefault.indexOf(key.charAt(j%key.length()));
                }else{
                    positionCharacterKey = alphabetDefault.indexOf(key.charAt(k%key.length()));
                }
                int characterPos = (positionCharacterKey + positionCharacterMsg)% alphabetDefault.length();
                char character = alphabetDefault.charAt(characterPos);
                System.out.println("La position de la pos : "+positionCharacterMsg + "   autre pos : " + positionCharacterKey);
                System.out.println(character);
                cipherText = cipherText + character;
                k++;
            } else {
                cipherText = cipherText + " ";
            }
        }
        return cipherText;
    }
    
    /**
     * Decipher the message with the key give in parameter.
     * browse all the character of the message and check if the character si not a space
     * if it is, it will get the current position of the character and substract the position of the character key inside the new position.
     * Store the new character inside a String
     * If it is a space bar, it will store it too
     * @param message String: message of the line that will be decipher
     * @param key String: the key for the cipher
     * @return the the new string message who are decipher
     */
    public String decipherVigenere(String message, String key){
        String decipherText="";
        int k=0;
        for (var i = 0; i < message.length(); i++) {
            if (message.charAt(i) != ' ') {
                int positionCharacterMsg = alphabetDefault.indexOf(message.charAt(i));
                int positionCharacterKey = 0;
                if(key.charAt(k%key.length())== ' '){
                    int j=k;
                    while(key.charAt(j%key.length())== ' '){
                        j++;
                    }
                    positionCharacterKey = alphabetDefault.indexOf(key.charAt(j%key.length()));
                }else{
                    positionCharacterKey = alphabetDefault.indexOf(key.charAt(k%key.length()));
                }
                int characterPos =((positionCharacterMsg - positionCharacterKey)% alphabetDefault.length());;
                if(characterPos<0){
                    characterPos = ((positionCharacterMsg - positionCharacterKey + alphabetDefault.length() )% alphabetDefault.length());
                }
                char character = alphabetDefault.charAt(characterPos);
                decipherText = decipherText + character;
                k++;
            } else {
                decipherText = decipherText + " ";
            }
        }
        return decipherText;
    }
}
