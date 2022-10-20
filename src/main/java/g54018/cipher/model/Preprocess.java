/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package g54018.cipher.model;
import java.io.FileNotFoundException;
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
public class Preprocess {
    public final String DIACRITICS = "àâäéèêëîïôùûç";
    public final String DIACRITICS_REPLACE = "aaaeeeeiiouuc";
    public final String TO_REMOVE = "\n\t\"+-*/=&|'’`()[]{},;.!?#~_";
    
    public int indexOf(char c, String str){
        for(int i = 0; i < str.length(); i++)
        if(str.charAt(i) == c)
            return i;
        return -1;
    }
    
    public String preprocessLine(String line){
        line = line.toLowerCase();
        String out = "";
        for(int i = 0; i < line.length(); i++){
            char c = line.charAt(i);            
            
            int index = indexOf(c, DIACRITICS);
            if(index != -1) //char is not a diacritic
                out += DIACRITICS_REPLACE.charAt(index);
            else if(indexOf(c, TO_REMOVE) != -1) //char has to be removed
            {}
            else if(c == 'æ')
                out += "ae";
            else if(c == 'œ')
                out += "oe";
            else
                out += c;
        }
        return out;
    }
    
    public void preprocessFile(String input, String output, boolean ignoreLines) throws FileNotFoundException, IOException {
        Scanner in = new Scanner(new FileReader(input, StandardCharsets.UTF_8));
        PrintStream out = new PrintStream(new FileOutputStream(output));
        
        while(in.hasNextLine())
        {
            if(!ignoreLines)
                out.println(preprocessLine(in.nextLine()));
            else
                out.print(preprocessLine(in.nextLine()));
        }
    }
}
