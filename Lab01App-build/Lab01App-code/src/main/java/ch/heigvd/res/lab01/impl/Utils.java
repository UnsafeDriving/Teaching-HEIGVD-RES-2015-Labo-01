package ch.heigvd.res.lab01.impl;

import java.util.logging.Logger;
import javassist.compiler.TokenId;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    /**
     * This method looks for the next new line separators (\r, \n, \r\n) to
     * extract the next line in the string passed in arguments.
     *
     * @param lines a string that may contain 0, 1 or more lines
     * @return an array with 2 elements; the first element is the next line with
     * the line separator, the second element is the remaining text. If the
     * argument does not contain any line separator, then the first element is
     * an empty string.
     */
    public static String[] getNextLine(String lines) {
        if (lines.contains("\r") || lines.contains("\n")) {
            for (int character = 0; character < lines.length(); character++) {
                if (lines.charAt(character) == '\r') {
                    if ((character + 1 < lines.length()) && lines.charAt(character + 1) == '\n') {
                        return new String[]{lines.substring(0, character + 2), lines.substring(character + 2)};
                    } else {
                        return new String[]{lines.substring(0, character + 1), lines.substring(character + 1)};
                    }
                }else if (lines.charAt(character) == '\n') {
                    return new String[]{lines.substring(0, character+1), lines.substring(character + 1)};
                }
            }
        }
        return new String[]{"", lines};
    }

}
