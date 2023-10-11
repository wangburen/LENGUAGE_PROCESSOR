package Lex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

    private BufferedReader br;
    private FileReader file;

    public Reader (String filename){
        try{
            file = new FileReader(filename);
            br = new BufferedReader(file);
        }
        catch(FileNotFoundException a){
            a.printStackTrace();/*error */
        }

    }

    public char read(){
        char c = '\0'; //EOF
        int n;
        try{
            if((n = br.read()) != -1){
                c = (char) n;
            }
        }
        catch(IOException a){
            a.printStackTrace();/*error */
        }
        return c;
    }

    public void close(){
        try{
            br.close();
        }
        catch(IOException a){
            a.printStackTrace(); /*error */
        }
    }

}
