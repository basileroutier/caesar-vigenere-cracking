/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.cipher.model;


/**
 *
 * @author basile
 */
public class breakCipher {

    private final double[] ENGLISH_LETTERS_PROBABILITIES = {0.08167, 0.01492, 0.02502, 
                                                            0.04253, 0.12702, 0.02228, 
                                                            0.02015, 0.06094, 0.06966, 
                                                            0.00153, 0.01292, 0.04025,
                                                            0.02406, 0.06749, 0.07507,
                                                            0.01929, 0.00095, 0.05987,
                                                            0.06327, 0.09356, 0.02758,
                                                            0.00978, 0.02560, 0.00150,
                                                            0.01994, 0.00077};
    private final String alphabetDefault = "abcdefghijklmnopqrstuvwxyz";
    private final int alphabetLgt;

    public breakCipher() {
        this.alphabetLgt = alphabetDefault.length();
    }

    /**
     * Find the probable offset for the cipher message
     * And return the offset of the cipher message
     * @param message String: message cipher
     * @return the offset for the decipher the message
     */
    public int breakCipher(String message) {
        return probableOffset(chiSquares(message));
    }
    
    /**
     * Decipher for each offset ranging from 1 to 26 the message
     * Put into a double array for each offset the Chi Square of theorical letter frequencies and letter frequencie in the real message
     * @param message String: cipher message
     * @return double array which contains all Chi Square for all offset 
     */
    private double[] chiSquares(String message) {
        double[] expectedLettersFrequencies = letterFrequencies(message.length());
        double[] chiSquares = new double[alphabetLgt];
        var ceasar = new CaesarAlgorithm();
        
        for (int offset = 0; offset < chiSquares.length; offset++) {
            String decipheredMessage = ceasar.decipherCesar(message,offset);
            long[] lettersFrequencieMessage = allLetterFrequenciesMessage(decipheredMessage);
            double chiSquare = chiSquareVal(expectedLettersFrequencies, lettersFrequencieMessage);
            chiSquares[offset] = chiSquare;
        }
        return chiSquares;
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
     * Compare all the Chi Square from all the offset and
     * Return the index (offset) of the lowest ChiSquare finded
     * @param chiSquares array double: double array that contains all ChiSquare
     * @return the index (offset) of the lowest ChiSquare
     */
    private int probableOffset(double[] chiSquares) {
        int probKey=0;
        double currentChiSquare;
        double maxChiSquare=0;
        for (int i = 0; i < chiSquares.length; i++) {
            currentChiSquare = chiSquares[i];
            maxChiSquare = chiSquares[probKey];
            if (currentChiSquare < maxChiSquare) {
                probKey = i;
            }
        }
        return probKey;
    }
}
