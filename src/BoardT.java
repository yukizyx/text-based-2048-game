/**
 * @file: BoardT.java
 * @Author: Yuki Zhao - zhaoy243
 * @Date: 2021/4/4
 * @Description: An ADT representing the game board of the game.
 */

package src;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @brief Representing the game board of the game.
 */
public class BoardT{
    public ArrayList<ArrayList<Integer>> board;
    public boolean status;
    public int marks;

    public final static int size = 4;

    /**
     * @brief A constructor for a new game board.
     * @return An empty game board with all cells equal to 0.
     */
    public BoardT() {
        this.status = true;
        this.marks = 0;
        this.board = new ArrayList<>();
        Integer[] row = new Integer[size];
        for (int i = 0; i < size; i ++) {
            row[i] = 0;
        }
        for (int i = 0; i < size; i++) {
            this.board.add(new ArrayList<>(Arrays.asList(row)));
        }
    }

    /**
     * @brief Generating a random starting board.
     * @return A random starting board.
     */
    public void startBoard() {
        double random = Math.random();
        this.addCell(2);
        if (random <= 0.1){
            this.addCell(4);
        } else {
            this.addCell(2);
        }
    }

    /**
     * @brief Randomly assign a 2 or 4 among all empty cells.
     * @param num - An int that we need to assign.
     * @throws RuntimeException - if the game is over.
     */
    public void addCell(int num) {
        if (! this.isFull()) {
            int x = randomNum();
            int y = randomNum();
            if (this.isEmpty(x, y)){
                this.setCell(x, y, num);
            } else {
                addCell(num);
            }
        }else{
            throw new RuntimeException();
        }
    }

    /**
     * @brief A getter for the status of the game.
     * @return The status of the game where false for game over and true for game on.
     */
    public boolean getStatus(){
        return this.status;
    }

    /**
     * @brief A getter for the current marks of the game.
     * @return The current marks of the game.
     */
    public int getMarks(){
        return this.marks;
    }

    /**
     * @brief A getter for the cell type at given x and y.
     * @param x - row number
     * @param y - column number
     * @throws IndexOutOfBoundsException - if entered coordinate is located outside of the board
     * @return A CellT object.
     */
    public int getCell(int x, int y){
        if (x > size - 1 || y > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        return this.board.get(x).get(y);
    }

    /**
     * @brief A setter for the cell type at given x and y.
     * @param x - row number
     * @param y - column number
     * @param num - the new number of the cell
     * @throws IndexOutOfBoundsException - if entered coordinate is located outside of the board
     */
    public void setCell(int x, int y, int num){
        if (x > size - 1 || y > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        this.board.get(x).set(y, num);
    }

    /**
     * @brief Determining whether a cell is empty.
     * @param x - row number
     * @param y - column number
     * @throws IndexOutOfBoundsException - if entered coordinate is located outside of the board
     * @return A CellT object.
     */
    public boolean isEmpty(int x, int y){
        if (x > size - 1 || y > size - 1) {
            throw new IndexOutOfBoundsException();
        } else {
            return getCell(x, y) == 0;
        }
    }

    // is full --> no 0 cell, is over --> is full & no possible moves
    /**
     * @brief Determining whether a game board is full.
     * @return Return true if there is no 0 cell, otherwise false.
     */
    public boolean isFull(){
        for (ArrayList<Integer> row : this.board) {
            for (Integer num : row) {
                if (num == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @brief Determining whether a game board has possible horizontal moves.
     * @return Return true if there is, otherwise false.
     */
    public boolean horizontalCheck(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                int a = this.getCell(i, j);
                int b = this.getCell(i, j + 1);
                if (a == b) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @brief Determining whether a game board has possible vertical moves.
     * @return Return true if there is, otherwise false.
     */
    public boolean verticalCheck(){
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size; j++) {
                int a = this.getCell(i, j);
                int b = this.getCell(i + 1, j);
                if (a == b) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @brief Determining whether a game board has possible moves, if no possible moves, change game status to false.
     * @return Return true if the game is over, otherwise false.
     */
    public boolean isOver(){
        if (this.isFull()){
            // no possible horizontal & vertical moves --> game over
            if (! this.verticalCheck() && ! this.horizontalCheck()) {
                this.status = false;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @brief Performing a move.
     * @param move - a MoveT object
     */
    public void moveBoard(MoveT move) {
        if (move == MoveT.Down) {
            this.down();
        } else if (move == MoveT.Up) {
            this.up();
        } else if (move == MoveT.Left) {
            this.left();
        } else {
            this.right();
        }
    }

    // perform move
    /**
     * @brief Performing the Down move to current game board, merge all possible cells and update marks.
     * @throws RuntimeException - if there is no possible moves vertically
     */
    public void down(){
        if (! this.isFull() || this.verticalCheck()) {
            for (int col = 0; col < size; col ++) {
                mergeColumnDown(col);
            }
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * @brief Merge a column if we have a Down move.
     * @param colIndex - the index of merged column
     */
    public void mergeColumnDown(int colIndex){
        int first = board.get(0).get(colIndex);
        int second = board.get(1).get(colIndex);
        int third = board.get(2).get(colIndex);
        int fourth = board.get(3).get(colIndex);

        ArrayList<Integer> numIndex = numIndex(first, second, third, fourth);
        int numOfNum = numIndex.size();

        // four 0 --> do nothing, three zero --> move the num to the bottom, two zero --> merge(optional) & move to bottom
        // one 0 --> merge(optional) & move to bottom, no zeros --> merge(optional) & move to bottom or dp nothing
        if (numOfNum == 1) {
            int temp = this.getCell(numIndex.get(0), colIndex);
            this.setCell(numIndex.get(0), colIndex, 0);
            this.setCell(3, colIndex, temp);
        } else if (numOfNum == 2) {
            // whether the two non-zero int are equal
            // equal --> merge, not equal --> move
            int upper = this.getCell(numIndex.get(0), colIndex);
            int lower = this.getCell(numIndex.get(1), colIndex);
            if (upper == lower) {
                this.marks += upper * 2;
                this.setCell(numIndex.get(0), colIndex, 0);
                this.setCell(numIndex.get(1), colIndex, 0);
                this.setCell(3, colIndex, upper * 2);
            } else {
                this.setCell(numIndex.get(1), colIndex, 0);
                this.setCell(3, colIndex, lower);
                this.setCell(numIndex.get(0), colIndex, 0);
                this.setCell(2, colIndex, upper);
            }
        } else if (numOfNum == 3) {
            // upper 2 cells equal or lower 2 cells equal
            int upper = this.getCell(numIndex.get(0), colIndex);
            int middle = this.getCell(numIndex.get(1), colIndex);
            int lower = this.getCell(numIndex.get(2), colIndex);
            if (middle == lower) {
                //middle = lower = 0, bottom = 2 * lower, top = upper
                this.marks += lower * 2;
                this.setCell(numIndex.get(2), colIndex, 0);
                this.setCell(numIndex.get(1), colIndex, 0);
                this.setCell(numIndex.get(0), colIndex, 0);
                this.setCell(3, colIndex, lower * 2);
                this.setCell(2, colIndex, upper);
            } else if (upper == middle){
                // bottom = lower, second bottom = upper * 2, upper = middle = 0
                this.marks += upper * 2;
                this.setCell(numIndex.get(0), colIndex, 0);
                this.setCell(numIndex.get(1), colIndex, 0);
                this.setCell(numIndex.get(2), colIndex, 0);
                this.setCell(3, colIndex, lower);
                this.setCell(2, colIndex, upper * 2);
            } else {
                this.setCell(numIndex.get(0), colIndex, 0);
                this.setCell(numIndex.get(1), colIndex, 0);
                this.setCell(numIndex.get(2), colIndex, 0);
                this.setCell(3, colIndex, lower);
                this.setCell(2, colIndex, middle);
                this.setCell(1, colIndex, upper);
            }
        } else if (numOfNum == 4) {
            // no merge or one merge or two merges
            if (first == second && third == fourth) {
                this.marks += first * 2;
                this.marks += third * 2;
                this.setCell(numIndex.get(0), colIndex, 0);
                this.setCell(numIndex.get(1), colIndex, 0);
                this.setCell(numIndex.get(2), colIndex, 0);
                this.setCell(numIndex.get(3), colIndex, 0);
                this.setCell(3, colIndex, third * 2);
                this.setCell(2, colIndex, first * 2);
            } else if (third == fourth) {
                this.marks += third * 2;
                this.setCell(3, colIndex, third * 2);
                this.setCell(2, colIndex, second);
                this.setCell(1, colIndex, first);
                this.setCell(0, colIndex, 0);
            } else if (second == third) {
                this.marks += third * 2;
                this.setCell(2, colIndex, third * 2);
                this.setCell(1, colIndex, first);
                this.setCell(0, colIndex, 0);
            } else if (first == second) {
                this.marks += first * 2;
                this.setCell(1, colIndex, first * 2);
                this.setCell(0, colIndex, 0);
            }
        }
    }

    /**
     * @brief Performing the Up move to current game board, merge all possible cells and update marks.
     * @throws RuntimeException - if there is no possible moves vertically
     */
    public void up(){
        if (! this.isFull() || this.verticalCheck()) {
            for (int col = 0; col < size; col ++) {
                mergeColumnUp(col);
            }
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * @brief Merge a column if we have a Up move.
     * @param colIndex - the index of merged column
     */
    public void mergeColumnUp(int colIndex){
        int first = board.get(0).get(colIndex);
        int second = board.get(1).get(colIndex);
        int third = board.get(2).get(colIndex);
        int fourth = board.get(3).get(colIndex);

        ArrayList<Integer> numIndex = numIndex(first, second, third, fourth);
        int numOfNum = numIndex.size();

        if (numOfNum == 1) {
            int temp = this.getCell(numIndex.get(0), colIndex);
            this.setCell(numIndex.get(0), colIndex, 0);
            this.setCell(0, colIndex, temp);
        } else if (numOfNum == 2) {
            // whether the two non-zero int are equal
            // equal --> merge, not equal --> move
            int upper = this.getCell(numIndex.get(0), colIndex);
            int lower = this.getCell(numIndex.get(1), colIndex);
            if (upper == lower) {
                this.marks += upper * 2;
                this.setCell(numIndex.get(0), colIndex, 0);
                this.setCell(numIndex.get(1), colIndex, 0);
                this.setCell(0, colIndex, upper * 2);
            } else {
                this.setCell(numIndex.get(1), colIndex, 0);
                this.setCell(1, colIndex, lower);
                this.setCell(numIndex.get(0), colIndex, 0);
                this.setCell(0, colIndex, upper);
            }
        } else if (numOfNum == 3) {
            // upper 2 cells equal or lower 2 cells equal
            int upper = this.getCell(numIndex.get(0), colIndex);
            int middle = this.getCell(numIndex.get(1), colIndex);
            int lower = this.getCell(numIndex.get(2), colIndex);
            if (middle == upper) {
                this.marks += upper * 2;
                this.setCell(numIndex.get(2), colIndex, 0);
                this.setCell(numIndex.get(1), colIndex, 0);
                this.setCell(numIndex.get(0), colIndex, 0);
                this.setCell(0, colIndex, upper * 2);
                this.setCell(1, colIndex, lower);
            } else if (lower == middle){
                this.marks += lower * 2;
                this.setCell(numIndex.get(0), colIndex, 0);
                this.setCell(numIndex.get(1), colIndex, 0);
                this.setCell(numIndex.get(2), colIndex, 0);
                this.setCell(0, colIndex, upper);
                this.setCell(1, colIndex, lower * 2);
            } else {
                this.setCell(numIndex.get(0), colIndex, 0);
                this.setCell(numIndex.get(1), colIndex, 0);
                this.setCell(numIndex.get(2), colIndex, 0);
                this.setCell(2, colIndex, lower);
                this.setCell(1, colIndex, middle);
                this.setCell(0, colIndex, upper);
            }
        } else if (numOfNum == 4) {
            // no merge or one merge or two merges
            if (first == second && third == fourth) {
                this.marks += first * 2;
                this.marks += third * 2;
                this.setCell(numIndex.get(0), colIndex, 0);
                this.setCell(numIndex.get(1), colIndex, 0);
                this.setCell(numIndex.get(2), colIndex, 0);
                this.setCell(numIndex.get(3), colIndex, 0);
                this.setCell(1, colIndex, third * 2);
                this.setCell(0, colIndex, first * 2);
            } else if (first == second) {
                this.marks += first * 2;
                this.setCell(0, colIndex, first * 2);
                this.setCell(1, colIndex, third);
                this.setCell(2, colIndex, fourth);
                this.setCell(3, colIndex, 0);
            } else if (second == third) {
                this.marks += second * 2;
                this.setCell(1, colIndex, third * 2);
                this.setCell(2, colIndex, fourth);
                this.setCell(3, colIndex, 0);
            } else if (third == fourth) {
                this.marks += third * 2;
                this.setCell(2, colIndex, third * 2);
                this.setCell(3, colIndex, 0);
            }
        }
    }

    /**
     * @brief Performing the Left move to current game board, merge all possible cells and update marks.
     * @throws RuntimeException - if there is no possible moves horizontally
     */
    public void left(){
        if (! this.isFull() || this.horizontalCheck()) {
            for (int row = 0; row < size; row ++) {
                mergeRowLeft(row);
            }
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * @brief Merge a row if we have a Left move.
     * @param rowIndex - the index of merged column
     */
    public void mergeRowLeft(int rowIndex){
        ArrayList<Integer> row = board.get(rowIndex);

        int first = row.get(0);
        int second = row.get(1);
        int third = row.get(2);
        int fourth = row.get(3);

        ArrayList<Integer> numIndex = numIndex(first, second, third, fourth);
        int numOfNum = numIndex.size();

        if (numOfNum == 1) {
            int temp = this.getCell(rowIndex, numIndex.get(0));
            this.setCell(rowIndex, numIndex.get(0), 0);
            this.setCell(rowIndex, 0, temp);
        } else if (numOfNum == 2) {
            int left = this.getCell(rowIndex, numIndex.get(0));
            int right = this.getCell(rowIndex, numIndex.get(1));
            if (left == right) {
                this.marks += left * 2;
                this.setCell(rowIndex, numIndex.get(0), 0);
                this.setCell(rowIndex, numIndex.get(1), 0);
                this.setCell(rowIndex, 0, left * 2);
            } else {
                this.setCell(rowIndex, numIndex.get(0), 0);
                this.setCell(rowIndex, numIndex.get(1), 0);
                this.setCell(rowIndex, 0, left);
                this.setCell(rowIndex, 1, right);
            }
        } else if (numOfNum == 3) {
            int left = this.getCell(rowIndex, numIndex.get(0));
            int middle = this.getCell(rowIndex, numIndex.get(1));
            int right = this.getCell(rowIndex, numIndex.get(2));
            if (middle == left) {
                this.marks += left * 2;
                this.setCell(rowIndex, numIndex.get(0), 0);
                this.setCell(rowIndex, numIndex.get(1), 0);
                this.setCell(rowIndex, numIndex.get(2), 0);
                this.setCell(rowIndex, 0, left * 2);
                this.setCell(rowIndex, 1, right);
            } else if (right == middle){
                this.marks += right * 2;
                this.setCell(rowIndex, numIndex.get(0), 0);
                this.setCell(rowIndex, numIndex.get(1), 0);
                this.setCell(rowIndex, numIndex.get(2), 0);
                this.setCell(rowIndex, 0, left);
                this.setCell(rowIndex, 1, right * 2);
            } else {
                this.setCell(rowIndex, numIndex.get(0), 0);
                this.setCell(rowIndex, numIndex.get(1), 0);
                this.setCell(rowIndex, numIndex.get(2), 0);
                this.setCell(rowIndex, 0, left);
                this.setCell(rowIndex, 1, middle);
                this.setCell(rowIndex, 2, right);
            }
        } else if (numOfNum == 4) {
            // no merge or one merge or two merges
            if (first == second && third == fourth) {
                this.marks += first * 2;
                this.marks += third * 2;
                this.setCell(rowIndex, numIndex.get(0), 0);
                this.setCell(rowIndex, numIndex.get(1), 0);
                this.setCell(rowIndex, numIndex.get(2), 0);
                this.setCell(rowIndex, numIndex.get(3), 0);
                this.setCell(rowIndex, 0, 2 * first);
                this.setCell(rowIndex, 1, 2 * third);
            } else if (first == second) {
                this.marks += first * 2;
                this.setCell(rowIndex, 0, 2 * first);
                this.setCell(rowIndex, 1, third);
                this.setCell(rowIndex, 2, fourth);
                this.setCell(rowIndex, 3, 0);
            } else if (second == third) {
                this.marks += third * 2;
                this.setCell(rowIndex, 0, first);
                this.setCell(rowIndex, 1, 2 * third);
                this.setCell(rowIndex, 2, fourth);
                this.setCell(rowIndex, 3, 0);
            } else if (third == fourth) {
                this.marks += third * 2;
                this.setCell(rowIndex, 0, first);
                this.setCell(rowIndex, 1, second);
                this.setCell(rowIndex, 2, third * 2);
                this.setCell(rowIndex, 3, 0);
            }
        }
    }

    /**
     * @brief Performing the Right move to current game board, merge all possible cells and update marks.
     * @throws RuntimeException - if there is no possible moves horizontally
     */
    public void right(){
        if (! this.isFull() || this.horizontalCheck()) {
            for (int row = 0; row < size; row ++) {
                mergeRowRight(row);
            }
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * @brief Merge a row if we have a Right move.
     * @param rowIndex - the index of merged column
     */
    public void mergeRowRight(int rowIndex){
        ArrayList<Integer> row = board.get(rowIndex);

        int first = row.get(0);
        int second = row.get(1);
        int third = row.get(2);
        int fourth = row.get(3);

        ArrayList<Integer> numIndex = numIndex(first, second, third, fourth);
        int numOfNum = numIndex.size();

        if (numOfNum == 1) {
            int temp = this.getCell(rowIndex, numIndex.get(0));
            this.setCell(rowIndex, numIndex.get(0), 0);
            this.setCell(rowIndex, 3, temp);
        } else if (numOfNum == 2) {
            int left = this.getCell(rowIndex, numIndex.get(0));
            int right = this.getCell(rowIndex, numIndex.get(1));
            if (left == right) {
                this.marks += left * 2;
                this.setCell(rowIndex, numIndex.get(0), 0);
                this.setCell(rowIndex, numIndex.get(1), 0);
                this.setCell(rowIndex, 3, left * 2);
            } else {
                this.setCell(rowIndex, numIndex.get(0), 0);
                this.setCell(rowIndex, numIndex.get(1), 0);
                this.setCell(rowIndex, 2, left);
                this.setCell(rowIndex, 3, right);
            }
        } else if (numOfNum == 3) {
            int left = this.getCell(rowIndex, numIndex.get(0));
            int middle = this.getCell(rowIndex, numIndex.get(1));
            int right = this.getCell(rowIndex, numIndex.get(2));
            if (middle == right) {
                this.marks += right * 2;
                this.setCell(rowIndex, numIndex.get(0), 0);
                this.setCell(rowIndex, numIndex.get(1), 0);
                this.setCell(rowIndex, numIndex.get(2), 0);
                this.setCell(rowIndex, 3, right * 2);
                this.setCell(rowIndex, 2, left);
            } else if (left == middle){
                this.marks += left * 2;
                this.setCell(rowIndex, numIndex.get(0), 0);
                this.setCell(rowIndex, numIndex.get(1), 0);
                this.setCell(rowIndex, numIndex.get(2), 0);
                this.setCell(rowIndex, 3, right);
                this.setCell(rowIndex, 2, left * 2);
            } else {
                this.setCell(rowIndex, numIndex.get(0), 0);
                this.setCell(rowIndex, numIndex.get(1), 0);
                this.setCell(rowIndex, numIndex.get(2), 0);
                this.setCell(rowIndex, 1, left);
                this.setCell(rowIndex, 2, middle);
                this.setCell(rowIndex, 3, right);
            }
        } else if (numOfNum == 4) {
            // no merge or one merge or two merges
            if (first == second && third == fourth) {
                this.marks += first * 2;
                this.marks += third * 2;
                this.setCell(rowIndex, numIndex.get(0), 0);
                this.setCell(rowIndex, numIndex.get(1), 0);
                this.setCell(rowIndex, numIndex.get(2), 0);
                this.setCell(rowIndex, numIndex.get(3), 0);
                this.setCell(rowIndex, 2, 2 * first);
                this.setCell(rowIndex, 3, 2 * third);
            } else if (third == fourth) {
                this.marks += fourth * 2;
                this.setCell(rowIndex, 0, 0);
                this.setCell(rowIndex, 1, first);
                this.setCell(rowIndex, 2, second);
                this.setCell(rowIndex, 3, fourth * 2);
            } else if (second == third) {
                this.marks += third * 2;
                this.setCell(rowIndex, 0, 0);
                this.setCell(rowIndex, 1, first);
                this.setCell(rowIndex, 2, second * 2);
                this.setCell(rowIndex, 3, fourth);
            } else if (first == second) {
                this.marks += second * 2;
                this.setCell(rowIndex, 0, 0);
                this.setCell(rowIndex, 1, first  * 2);
                this.setCell(rowIndex, 2, third);
                this.setCell(rowIndex, 3, fourth);
            }
        }
    }

    /**
     * @brief Generating a random number between 0 and 3 (inclusive).
     * @return A generated int.
     */
    private int randomNum(){
        double random = Math.random() * 4;
        return (int) (random);
    }

    /**
     * @brief Determine the position of non-zero cells.
     * @param a - an int
     * @param b - an int
     * @param c - an int
     * @param d - an int
     * @return A list of index where the corresponding cell is not 0.
     */
    private ArrayList<Integer> numIndex(int a, int b, int c, int d){
        ArrayList<Integer> result = new ArrayList<>();
        if (a != 0) {
            result.add(0);
        }
        if (b != 0) {
            result.add(1);
        }
        if (c != 0) {
            result.add(2);
        }
        if (d != 0) {
            result.add(3);
        }
        return result;
    }

}
