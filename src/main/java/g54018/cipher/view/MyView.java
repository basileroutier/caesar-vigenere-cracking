/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.cipher.view;

import g54018.cipher.model.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author basile
 */
public class MyView {
    private cipherAlgorithm cipherAlgo;

    public MyView() {
        cipherAlgo = new cipherAlgorithm();
    }
    
    private String path(String utilisation){
        String basePath = new File("").getAbsolutePath();
        String path = new File("data/"+utilisation+".txt").getAbsolutePath();
        return path;
    }
    
    
    /**
     * Check wich chain is largest and return the size of the largest chain
     *
     * @param title String[] : array of all word of a String array
     * @return the size of the largest chain
     */
    private int indexMaxTile(String[] title) {
        int maxLgtTile = 0;
        for (var i = 0; i < title.length; i++) {
            if (title[i].length() > maxLgtTile) {
                maxLgtTile = title[i].length();
            }
        }
        return maxLgtTile;
    }

    /**
     * Display all the message put in the parameter of String array 
     * Format all the String to put in the ordre of size and have a non-straight display 
     * All the code is reusable and dynamic It check if the current word length size is different 
     * than the largest size of word to put in the center (not the same line) of the largest word
     *
     * @param title String[] : array of all word of a String array
     * @param largestLgtWord int: the largest size of a chain in a array of String
     * @param currentIndex
     */
    private void displayOnlyMessage(String[] title, int largestLgtWord, int currentIndex) {
        int i = currentIndex;
        if (title[i].length() != largestLgtWord) {
            int currentLgtWord = largestLgtWord - title[i].length();
            for (var j = 0; j < currentLgtWord; j++) {
                if (j == currentLgtWord / 2) {
                    System.out.print("  " + title[i] + "  ");
                }
                System.out.print(" ");
            }
        } else {
            System.out.print("  " + title[i] + "  ");
        }
    }

    /**
     * Display the hyphen to make a square in the border 
     * Get the largest length size chain to make a nice display
     *
     * @param largestLgtWord int: the largest size of a chain in a array of String
     */
    private void displayOnlyHyphen(int largestLgtWord) {
        for (var j = 0; j < largestLgtWord + 4; j++) {
            System.out.print("-");
        }
    }

    /**
     * Display the Message if the the current index (i) is between 1 and the String array length, 
     * If it is true it will substract the current index by 1 to start at the beginning of the String array 
     * It is the content of the square 
     * If not it will only display the hyphen, it is the upper and lower bordre
     *
     * @param title String[] : array of all word of a String array
     * @param largestLgtWord int: the largest size of a chain in a array of String
     * @param i : current index of the loop
     */
    private void displayMessageHyphen(String[] title, int largestLgtWord, int i) {
        boolean isInsideTile = (i >= 1 && i <= title.length);
        if (isInsideTile) {
            i--;
            displayOnlyMessage(title, largestLgtWord, i);
        } else {
            displayOnlyHyphen(largestLgtWord);
        }
    }

    /**
     * Display all the formatted message 
     * It display only straight bar at the beginning and end of the String array
     *
     * @param title String[] : array of all word of a String array
     */
    private void welcomeMessage(String... title) {
        int largestLgtWord = indexMaxTile(title);
        for (var i = 0; i < title.length + 2; i++) {
            System.out.print("\n|");
            displayMessageHyphen(title, largestLgtWord, i);
            System.out.print("|");
        }
    }
    
    public void algoSelection() throws IOException{
        Scanner sc = new Scanner(System.in);
        welcomeMessage("Hello and WELCOME to the program of Basile Routier <54018> and Koray Kutlu <52828>","","This program allows you to encrypt and decrypt messages with different algorithms. Other features such as frequency analysis cracking is implemented","You can choose between the Caesar or Vigenere algorithm", "", "You can either modify put your message in the fileText.txt in DATA directory project");
        List<String> inOutFile = new ArrayList<String>();
        inOutFile.add(path("fileText"));
        inOutFile.add(path("result"));
        choixGeneral(sc, inOutFile);
    }
    
    private void choixGeneral(Scanner scan, List<String> intOutFile) throws IOException{
        System.out.println("\n\tYou can choose two different cipher/decipher algorithm. Choose between Caesar or Vigenere");
        String choix = scan.nextLine();
        choix = choix.toLowerCase();
        while (!choix.equals("caesar") && !choix.equals("vigenere")) {
            System.out.println("\n\tYou can choose two different cipher/decipher algorithm. Choose between Caesar or Vigenere");
            choix = scan.nextLine();
        }
        if (choix.equals("caesar")) {
            choixSpecific(scan, true, intOutFile);
        }else{
            choixSpecific(scan, false, intOutFile);
        }
    }
    
    private void choixSpecific(Scanner sc, boolean estCaesar, List<String> intOutFile) throws IOException{
        List<String> choixAlgo = new ArrayList<String>();
        choixAlgo.add("cipher");
        choixAlgo.add("decipher");
        choixAlgo.add("breaker");
        System.out.println("\n\tYou can choose three different algorithm. Choose between cipher, decipher or breaker");
        String choix = sc.nextLine();
        choix = choix.toLowerCase();
        while(!choixAlgo.contains(choix)){
            System.out.println("\n\tYou can choose three different algorithm. Choose between cipher, decipher or breaker");
            choix = sc.nextLine();
        }
        String inputFile = intOutFile.get(0);
        String outputFile = intOutFile.get(1);
        System.out.println("For information the relative/absolute path for input file is : " + inputFile + "\nThe relative/absolute path for ouput file is : " + outputFile);
        if(estCaesar){
            int shift=0;
            if(choix.equals("cipher")){
                System.out.println("\tChoose the shift for the cipher text");
                shift = sc.nextInt();
                cipherAlgo.outputCaesarCipher(inputFile, outputFile, shift);
            }else if(choix.equals("decipher")){
                System.out.println("\tChoose the shift for the decipher text");
                shift = sc.nextInt();
                cipherAlgo.outputCaesarDecipher(inputFile, outputFile, shift);
            }else{
                cipherAlgo.outputCaesarBreakCipher(inputFile, outputFile);
            }
        }else{
            String key;
            if(choix.equals("cipher")){
                System.out.println("\tEnter a key for cipher the text (without number)");
                key = sc.nextLine();
                cipherAlgo.outputVigenereCipher(inputFile, outputFile, key);
            }else if(choix.equals("decipher")){
                System.out.println("\tEnter a key for decipher the text (without number)");
                key = sc.nextLine();
                cipherAlgo.outputVigenereDecipher(inputFile, outputFile, key);
            }else{
                cipherAlgo.outputVigenereBreakCipher(inputFile, outputFile);
            }
        }
        
    }
}
