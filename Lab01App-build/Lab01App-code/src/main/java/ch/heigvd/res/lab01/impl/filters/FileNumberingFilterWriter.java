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
    private char previous = ' ';
    private boolean r = false;

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
        int positionInitial = off;
        boolean noSeparators = true;
        String tmp = "";
        len = len + off;
        char now = str.charAt(0);
        
        if(first) {
            tmp = (i++) + "\t";
            first = false;
        }
        if(str.compareTo("\r") != 0) {        
            if((str.substring(positionInitial, len).contains("\n") ||
                str.substring(positionInitial, len).contains("\r"))) {

                noSeparators = false;
            }

            for(int character = positionInitial; character < len; character++) {
                now = str.charAt(character);
                if(now == '\n') {                    
                    tmp += str.substring(positionInitial, character+1) + (i++) + "\t";
                    positionInitial = character+1;
                }else if(previous == '\r' || r == true) {
                    tmp += str.substring(positionInitial, character) + (i++) + "\t";                    
                    positionInitial = character;
                }
                r = false;
                previous = now;
            }
        }else {
            r = true;
        }
        
        if(noSeparators  || positionInitial < len) {
            tmp += str.substring(positionInitial, len);
        } 
        
        previous = now;
        super.write(tmp, 0, tmp.length());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
        this.write(String.valueOf(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
        String value = Character.toString((char) c);
        this.write(value, 0, 1);
  }

}
