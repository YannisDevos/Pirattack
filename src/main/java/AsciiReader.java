
package main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AsciiReader {
    
    public static String read(String filepath) {
        String result = "";
        try(BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while(line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            result = sb.toString();
            result = result.substring(0, result.length() - 1);
        } catch(FileNotFoundException e) {
            System.out.println("File not found: "); e.printStackTrace();
        } catch(IOException e) {
            System.out.println("Reading error: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    } 

}
