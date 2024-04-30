package cz.cuni.mff.sadovsm;

import cz.cuni.mff.sadovsm.ioHandler.*;
import cz.cuni.mff.sadovsm.sudoku.*;

public class App 
{
    static String helpGuide = """
            Commands:
             restart - generate new sudoku
             solve - solve the sudoku
             quit - quit the game
             help - show this message
             <coordinates> <value> - fill the cell with value
             possible <coordinates> - show possible values for the cell
             hint - show hint for the next move
             auto - fill one cell automatically
             undo - undo the last move""";

    public static void main( String[] args ) {

        Game game = new Game();
        boolean isFinished = false;
        Reader reader = new Reader();

        while(!isFinished) {
            Writer.writeGrid(9, 9, game.getGrid());

            Writer.writeln(game.isSolvable() ? "Sudoku is solvable" : "Sudoku is not solvable");

            Writer.write("Enter command: ");
            Option option = reader.readlnOpt();
            Writer.writeln();   //to separate the output from the input

            switch(option) {
                case RESTART -> game.restart();
                case SOLVE -> game.solve();
                case QUIT -> isFinished = true;
                case HELP -> Writer.writeln(helpGuide);
                case NONE-> {
                    Writer.writeln("Invalid command");
                    Writer.writeln(helpGuide);
                }
                case FILL -> Writer.writeln(game.fill(reader.pos[0], reader.pos[1], reader.value));
                case POSSIBLE -> {
                    int[] possible = game.getPossible(reader.pos[0], reader.pos[1]);
                    Writer.write("Possible values: ");
                    Writer.writeArray(possible);
                }
                case HINT -> {
                    int[] hint = game.getHint();
                    if (hint[0] == -1) {
                        Writer.writeln("No hint available");
                    } else {
                        Writer.writeln("Hint: " + (char) (hint[1] + 'A') + (char) (hint[0] + '1') + " try " + hint[2]);   //convert to coordinates
                    }
                }
                case UNDO -> Writer.writeln(game.undo() ? "Move undone" : "No move to undo");
                case AUTO -> Writer.writeln(game.autoFill());
            }

            if(game.isFinished()){
                Writer.writeGrid(9, 9, game.getGrid());
                Writer.writeln("Sudoku solved");
                isFinished = true;
            }
        }
    }
}
