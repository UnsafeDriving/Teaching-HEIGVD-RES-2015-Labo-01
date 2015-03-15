package ch.heigvd.res.lab01.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {
    private int i = 1;
    private boolean first = true;

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
        int positionInitial = off;
        boolean stop = false;
        boolean noSeparators = true;
        String tmp = "";
        len = len + off;
        
        if(first) {
            tmp = (i++) + "\t";
            first = false;
        }
        
        /*if(str.length() > len) {
            len = str.length();
        }
        
        if(off > len) {
            int tmpLen = len;
            len = off;
            off = tmpLen;
        }*/
        while((str.substring(positionInitial, len).contains("\n"))&& !stop) {
            noSeparators=false;
            for(int character = positionInitial; character < len; character++) {
                if(str.charAt(character) == '\n') {
                    
                    /*if(str.charAt(character) == '\r' && str.charAt(character+1) == '\n') {
                        tmp += str.substring(positionInitial, character+2) + (i++) + "\t";
                    
                        positionInitial = character+2;
                    }else {*/
                        tmp += str.substring(positionInitial, character+1) + (i++) + "\t";
                    
                        positionInitial = character+1;
                    //}
                    
                    
                    
                    if(positionInitial > len) {
                        stop = true;
                    }
                    
                    break;
                }
            }
        }
        
        
        if(noSeparators  || positionInitial < len) {
            tmp += str.substring(positionInitial, len);
        } 
        
        super.write(tmp, 0, tmp.length());
        //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
        super.write(cbuf, off, len);
        //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

  @Override
  public void write(int c) throws IOException {
        String value = Character.toString((char) c);
        this.write(value, 0, 1);
        //throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }

}
