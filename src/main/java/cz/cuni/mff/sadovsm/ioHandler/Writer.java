package cz.cuni.mff.sadovsm.ioHandler;

public class Writer {
    /**
     * Writes a message to the standard output
     * @param message the message to be written
     */
    public static void write(String message) {
        System.out.print(message);
    }

    /**
     * Writes a message to the standard output
     * @param message the message to be written
     */
    public static void writeln(String message) {
        System.out.println(message);
    }

    /**
     * Writes a newline to the standard output
     */
    public static void writeln() {
        System.out.println();
    }


    /**
     * Writes an array in one line separated by spaces
     * @param args array of the integers
     */
    public static void writeArray(int[] args){
        for(int arg: args) write(arg + " ");
        writeln();
    }

    /**
     * Writes the grid to the standard output
     * @param width the width of the grid
     * @param height the height of the grid
     * @param grid the grid to be written
     */
    public static void writeGrid(int width, int height, int[][] grid) {
        String border = "  + - - - + - - - + - - - +";
        writeln("    A B C   D E F   G H I");

        for (int i = 0; i < height; i++) {
            if(i % 3 == 0) writeln(border);
            write((i + 1) + " ");
            for (int j = 0; j < width; j++) {
                if(j % 3 == 0) write("| ");
                if(grid[i][j] == 0) write(". ");
                else write(grid[i][j] + " ");
            }
            writeln("|");
        }
        writeln(border);
    }

}
