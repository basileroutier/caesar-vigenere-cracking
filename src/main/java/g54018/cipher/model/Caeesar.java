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
public interface Caeesar {
    public void outputCaesarCipher(String input, String output, int decalage) throws IOException;
    public void outputCaesarDecipher(String input, String output, int decalage) throws IOException;
    public void outputCaesarBreakCipher(String input, String output) throws IOException;
    public String caesarCipher(String message, int decalage, boolean isCipher);
    public String decipherCesar(String message, int decalage);
}
