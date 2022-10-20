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
public class cipherAlgorithm {
    private CaesarAlgorithm caesarAlgorithm;
    private VigenereAlgorithm vigenereAlgorithm;

    public cipherAlgorithm() {
        this.caesarAlgorithm = new CaesarAlgorithm();
        this.vigenereAlgorithm = new VigenereAlgorithm();
    }
    
    public void outputCaesarCipher(String input, String output, int decalage) throws IOException{
        caesarAlgorithm.outputCaesarCipher(input, output, decalage);
    }
    
    public void outputCaesarDecipher(String input, String output, int decalage) throws IOException {
        caesarAlgorithm.outputCaesarDecipher(input, output, decalage);
    }
    
    public void outputCaesarBreakCipher(String input, String output) throws IOException{
        caesarAlgorithm.outputCaesarBreakCipher(input, output);
    }
    
    public void outputVigenereCipher(String input, String output, String key) throws IOException {
        vigenereAlgorithm.outputVigenereCipher(input, output, key);
    }
    
    public void outputVigenereDecipher(String input, String output, String key) throws IOException {
        vigenereAlgorithm.outputVigenereDecipher(input, output, key);
    }
    
    public void outputVigenereBreakCipher(String input, String output) throws IOException {
        vigenereAlgorithm.outputVigenereBreakCipher(input, output);
    }
}
