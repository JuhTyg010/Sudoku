package cz.cuni.mff.sadovsm.ioHandler;

import java.io.BufferedReader;

public class Reader {

    /**
     * Reads a line from the standard input
     * @return the line read from the standard input
     */
    public static String readln() {
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (java.io.IOException e) {
            return null;
        }
    }
    public int [] pos = new int[2];
    public int value;

    /**
     * Reads a line from the standard input and returns the corresponding option
     * @return the option corresponding to the line read from the standard input
     */
    public Option readlnOpt(){
        String line = readln();
        if (line == null) {
            return Option.NONE;
        }
        switch (line) {
            case "restart":
                return Option.RESTART;
            case "solve":
                return Option.SOLVE;
            case "quit":
                return Option.QUIT;
            case "help":
                return Option.HELP;
            case "hint":
                return Option.HINT;
            case "undo":
                return Option.UNDO;
            case "auto":
                return Option.AUTO;

            default:
                if (line.contains("possible")) {
                    try {
                        pos = getPosition(line.split(" ")[1]);
                        return Option.POSSIBLE;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return Option.NONE;
                    }
                } else {
                    String[] parts = line.split(" ");
                    if (parts.length == 2) {
                        try {
                            pos = getPosition(parts[0]);
                            value = Integer.parseInt(parts[1]);
                            if(value < 1 || value > 9) return Option.NONE;
                            return Option.FILL;
                        } catch (NumberFormatException e) {
                            return Option.NONE;
                        }
                    } else {
                        return Option.HELP;
                    }
                }
           }
        }

    /**
     * Converts the position code to row and column
     * @param posCode position code
     * @return row and column
     */
    private static int[] getPosition(String posCode) throws NumberFormatException{
        posCode = posCode.toUpperCase();
        if(posCode.length() != 2) throw new NumberFormatException();
        char x = posCode.charAt(0);
        char y = posCode.charAt(1);
        if(x < 'A' || x > 'I' || y < '1' || y > '9') throw new NumberFormatException();
        return new int[]{y - '1', x - 'A'}; //I want row col format and first char is A-I which is col
    }
}
