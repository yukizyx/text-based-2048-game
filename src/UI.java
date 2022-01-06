/**
 * @file: UI.java
 * @Author: Yuki Zhao - zhaoy243
 * @Date: 2021/4/4
 * @Description: The user interface (view) of the game.
 */

package src;

import java.util.ArrayList;

/**
 * @brief Some printing methods representing the user interface (view) of the game.
 */
public class UI {

    /**
     * @brief The constructor of a UI object.
     */
    public UI(){}

    /**
     * @brief Displaying a welcome message
     */
    public void printWelcomeMessage(){
        System.out.println("-------------------------------------------------");
        System.out.println("                 Welcome to 2048                 ");
        System.out.println("               ---> NEW GAME <---                ");
        System.out.println("-------------------------------------------------");
    }

    /**
     * @brief Display the game board.
     * @param board - the game board
     */
    public void printBoard(BoardT board) {
        System.out.println("-------------------------------------------------");
        System.out.println("Marks: " + board.getMarks());
        System.out.println("-------------------------------------------------");
        for (ArrayList<Integer> row : board.board){
            printRow(row);
            System.out.println();
        }
    }

    /**
     * @brief Display a row in the game board.
     * @param row - a row in the game board
     */
    public void printRow(ArrayList<Integer> row) {
        for (Integer num : row) {
            if (num == 0 || num == 2 || num == 4 ||num == 8) {
                System.out.print(num);
                System.out.print("      ");
            } else if (num == 16 || num == 32 || num == 64) {
                System.out.print(num);
                System.out.print("     ");
            } else if (num == 128 || num == 256 || num == 512) {
                System.out.print(num);
                System.out.print("    ");
            } else {
                System.out.print(num);
                System.out.print("   ");
            }
        }
    }

    /**
     * @brief Displaying an ending message
     */
    public void printEndingMessage(BoardT board){
        System.out.println("-------------------------------------------------");
        System.out.println("                    GAME OVER                    ");
        System.out.println("Marks: " + board.getMarks());
        System.out.println("             Thank You For Playing !!!           ");
        System.out.println("-------------------------------------------------");
    }


    }
