/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.cipher.model;

import java.io.IOException;

/**
 *
 * @author basile
 */
public interface Vigenere {
    public void outputVigenereCipher(String input, String output, String key) throws IOException ;
    public void outputVigenereDecipher(String input, String output, String key) throws IOException ;
    public void outputVigenereBreakCipher(String input, String output) throws IOException ;
    public String cipherVigenere(String message, String key);
    public String decipherVigenere(String message, String key);
}
