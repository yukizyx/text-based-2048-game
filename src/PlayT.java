/**
 * @file: PlayT.java
 * @Author: Yuki Zhao - zhaoy243
 * @Date: 2021/4/4
 * @Description: The play tool of 2048.
 */

package src;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @brief Simulate the game procedure.
 */
public class PlayT {
    /**
     * @brief Starting a new 2048 game.
     */
    public PlayT(){
        BoardT gameBoard = new BoardT();
        UI gameInterface = new UI();

        // displaying the welcome message.
        gameInterface.printWelcomeMessage();

        // create a new game.
        gameBoard.startBoard();
        gameInterface.printBoard(gameBoard);

        // game procedure: check if the board is full --> (not full) --> next move --> perform move --> add random tile --> check if the board is full
        while (! gameBoard.isOver()){
            // read a move
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter a move:");
            String move = scan.nextLine();

            // perform move   invalid input error!!!
            if (move.equals("Down")){
                gameBoard.moveBoard(MoveT.Down);
            } else if (move.equals("Up")){
                gameBoard.moveBoard(MoveT.Up);
            } else if (move.equals("Left")){
                gameBoard.moveBoard(MoveT.Left);
            } else if (move.equals("Right")){
                gameBoard.moveBoard(MoveT.Right);
            } else {
                throw new InputMismatchException();
            }

            // add tile
            double random = Math.random();
            if (random <= 0.1) {
                gameBoard.addCell(4);
            } else {
                gameBoard.addCell(2);
            }

            gameInterface.printBoard(gameBoard);

        }

        // displaying the ending messages & marks
        gameInterface.printEndingMessage(gameBoard);

    }

}
