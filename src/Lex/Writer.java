package Lex;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Writer {

    PrintWriter printw;

    public Writer (String fileName){
        try{
            printw = new PrintWriter(fileName, "UTF-8");
        }
        catch (FileNotFoundException a){
            a.printStackTrace(); /*error */
        }
        catch (UnsupportedEncodingException b){
            b.printStackTrace(); /*error */
        }
    }

    public void write(Object obj){
        printw.println(obj);
    }

    public void close(){
        printw.close();
    }
}
