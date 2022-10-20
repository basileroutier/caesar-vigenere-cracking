package g54018.cipher.Model;

import java.util.ArrayList;

/**
 * Break the cipher Of the Vigenere without the key.
 * @author kutlu x basile <54018>
 */
public class BreakCipherVigenere {
    public static final double COINCIDENCE_INDICATOR = 0.078; // E letter
    private final double[] ENGLISH_LETTERS_PROBABILITIES = {0.08167, 0.01492, 0.02502, 0.04253, 0.12702, 0.02228, 0.02015, 0.06094, 0.06966, 0.00153, 0.01292, 0.04025, 0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987, 0.06327, 0.09356, 0.02758, 0.00978, 0.02560, 0.00150, 0.01994, 0.00077};
    private final String alphabetDefault = "abcdefghijklmnopqrstuvwxyz";
    private final String chiffreDefault = "0123456789";
    private final int alphabetLgt = 26;
    
    public String decipherWithSizeOfKey(String ciphered) {
        ArrayList<String> v = stringDivided(ciphered, calculateSizeOfKey(ciphered));
        String key = "";

        for (int i = 0; i < v.size(); i++) {
            key += (char) (getKeyWithChi(v.get(i)) + 97);
        }
        return key;
    }

    /**
     * Loop to size of the key and ciphered text length
     * It check for wich letter in ciphered message if it is a letter and add the letter if the size of the key equal to i
     * return an array of max 26*length of the message with letter of message
     * @param ciphered String: message cipher
     * @param sizekey int: size of the key
     * @return the array with wich letter of the message
     */
    ArrayList<String> stringDivided(String ciphered, int sizekey) {
        ArrayList<String> v = new ArrayList<>();

        for (int i = 0; i < sizekey; i++) {
            String d = "";
            for (int j = 0; j < ciphered.length(); j++) {
                if (Character.isLetter(ciphered.charAt(j)) && j % sizekey == i) {
                    d += ciphered.charAt(j);
                }
            }
            v.add(d);
        }
        return v;
    }

    /**
     * Calculate the size of the key
     * Add one to the key such as the floor index is find for wich letter. That a supposition of the key length
     * Check the coincidence index of each 
     * @param ciphered
     * @return 
     */
    int calculateSizeOfKey(String ciphered) {
        int key = 0;
        double ic = 0.0;
        while (ic < 0.06) {
            key++;

            ArrayList<String> v = stringDivided(ciphered, key);
            ic = icAverageOfString(v);
        }

        return key;
    }

    /**
     * Check for every message in the array and add every the occurence of the message
     * Return the sum occurence for all the message in array
     * @param list Array String: contain the message of a string divided
     * @return the sum of the occurence for wich letter
     */
    double icAverageOfString(ArrayList<String> list) {
        double ic = 0.0;
        for (int i = 0; i < list.size(); i++) {
            ic += getIc(list.get(i));
        }
        ic = ic / list.size();
        return ic;
    }

    /**
     * Check for wich character of the message, if the charachter is different that a space
     * It check who many letter the message have and put the letter finded into a table occurence
     * And for wich letter in the alphabet, add the addition of every occurence letter with the number of letter in the text
     * To know what the observed letter into the text
     * @param msg
     * @return the coincidence index for the wich letter
     */
    double getIc(String msg) {
        int nbChar = 0;
        double ic = 0;
        int[] occurenceText = new int[26];
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) != ' ') {
                occurenceText[msg.charAt(i)-97]++;
                nbChar++;
            }
        }

        for (int i = 0; i < 26; i++) {
            double x = (occurenceText[i] * (occurenceText[i] - 1.0)) / (nbChar * (nbChar - 1.0));
            ic += x;
        }

        return ic;
    }

    /**
     * Get the max of chi for wich letter of the message
     * @param ciphered String: ciphered text 
     * @return the chi max finded
     */
    int getKeyWithChi(String ciphered) {
        double chiMax = 100000;
        int key = 0;
        double[] expectedLettersFrequencies = letterFrequencies(ciphered.length());
        for (int i = 0; i < 26; i++) {
            String decipheredMessage = decipherCesar(ciphered,i);
            long[] lettersFrequencieMessage = allLetterFrequenciesMessage(decipheredMessage);
            double chiSquare = chiSquareVal(expectedLettersFrequencies, lettersFrequencieMessage);
            if (chiSquare < chiMax) {
                chiMax = chiSquare;
                key = i;
            }
        }
        return key;
    }
    
     /**
     * Get for each letter of the alphabet the number of letter founded in the message
     * And return in a long array the number.
     * @param message String: message decrypt with the offset
     * @return a long array with the number of letter in the message for each letter in alphabet
     */
    private long[] allLetterFrequenciesMessage(String message) {
        var numberOfLetterMessage = new long[alphabetLgt];
        for(var i =0;i<numberOfLetterMessage.length;i++){
            numberOfLetterMessage[i] = numberLetterInMessage(alphabetDefault.charAt(i), message);
        }
        return numberOfLetterMessage;
    }

    /**
     * If the letter is equal to each character of the message it
     * Count the number of letter in the sentence
     * And return the number of letter in the sentence founded
     * @param letter char: letter that will be compared
     * @param message String: all the sentence
     * @return the number of letter in the message that correspond to the choose letter
     */
    private long numberLetterInMessage(char letter, String message) {
        long countLetterInMessage=0;
        for(var i=0;i<message.length();i++){
            if(message.charAt(i) == letter){
                countLetterInMessage++;
            }
        }
        return countLetterInMessage;
    }

    /**
     * Return a double array with the theorical letter frequencie for a message
     * Calculate the letter frequencies with a letter probabilities table
     * @param messageLength int: length of a sentence
     * @return double array with letter frequencies for the sentence length
     */
    private double[] letterFrequencies(int messageLength) {
        double[] letterFrequencies = new double[ENGLISH_LETTERS_PROBABILITIES.length];
        for(var i=0;i<ENGLISH_LETTERS_PROBABILITIES.length;i++){
            letterFrequencies[i] = ENGLISH_LETTERS_PROBABILITIES[i]*messageLength;
        }
        return letterFrequencies;
    }
    
    /**
     * Return the sum for a chiSquare test. 
     * This mathematical formula is a statistical hypothesis test that is valid to perform when the test statistic is chi-squared distributed under the null hypothesis.
     * It allows to test the adequacy of a series of data to a family of probability laws
     * @param exceptedValue double array: The excepted value for the letter frequencies
     * @param observedValue long array: the observed value for the letter frequencies
     * @return the sum of chiSquare
     */
    private double chiSquareVal(double[] exceptedValue, long[] observedValue){
        double formVal;
        double somme=0;
        for(var i=0;i<alphabetLgt;i++){
            double substractVal = (observedValue[i] - exceptedValue[i]);
            substractVal = substractVal * substractVal;
            double divisionVal = (substractVal/(exceptedValue[i]));
            somme += divisionVal;
        }
        return somme;
    }
    
    /**
     * Cipher a message with cesar.
     */
    private String caesarCipher(String message, int decalage, boolean isCipher) {
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
     * Decipher a message who are cipher with cesar with a key.
     * @param message A string, the message.
     * @param shift An integer who represent the shift.
     * @return 
     */
    public String decipherCesar(String message, int shift) {
        return caesarCipher(message, 26 - (shift % 26), true);
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
}
