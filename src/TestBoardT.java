/**
 * Author: Yuki Zhao
 * File: TestBoardT.java
 * Revised: 2021/4/5
 * Description: A class testing BoardT objects.
 */

package src;

import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class TestBoardT {
    public int boardSum(BoardT board) {
        int sum = 0;
        for (ArrayList<Integer> row : board.board) {
            for (Integer cell : row) {
                sum += cell;
            }
        }
        return sum;
    }

    private int randomNum(){
        double random = Math.random() * 10;
        return (int) (random);
    }

    @Test
    public void test_startBoard() {
        for (int i = 0; i < 5; i++) {
            BoardT board1 = new BoardT();
            board1.startBoard();
            assertTrue(boardSum(board1) == 4 || boardSum(board1) == 6);
        }
    }

    @Test
    public void test_addCell() {
        BoardT board1 = new BoardT();
        for (int i = 0; i < 16; i++) {
            board1.addCell(2);
        }
        assertEquals(32, boardSum(board1));

        BoardT board2 = new BoardT();
        for (int i = 0; i < 16; i++) {
            board2.addCell(4);
        }
        assertEquals(64, boardSum(board2));
    }

    @Test (expected = RuntimeException.class)
    public void test_exception_1() {
        BoardT board1 = new BoardT();
        for (int i = 0; i < 17; i++) {
            board1.addCell(2);
        }
    }

    @Test (expected = RuntimeException.class)
    public void test_exception_2() {
        BoardT board1 = new BoardT();
        for (int i = 0; i < 17; i++) {
            board1.addCell(4);
        }
    }

    @Test
    public void test_getStatus() {
        BoardT board1 = new BoardT();
        assertTrue(board1.getStatus());
        board1.startBoard();
        assertTrue(board1.getStatus());
    }

    @Test
    public void test_getMarks() {
        BoardT board1 = new BoardT();
        assertEquals(0, board1.getMarks());
        board1.startBoard();
        assertEquals(0, board1.getMarks());
    }

    @Test
    public void test_getCell() {
        BoardT board1 = new BoardT();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(0, board1.getCell(i, j));
            }
        }
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void test_exception_3() {
        BoardT board1 = new BoardT();
        board1.getCell(4, 3);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void test_exception_4() {
        BoardT board1 = new BoardT();
        board1.getCell(3, 4);
    }

    @Test
    public void test_setCell() {
        BoardT board1 = new BoardT();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = randomNum();
                board1.setCell(i, j, num);
                assertEquals(num, board1.getCell(i, j));
            }
        }
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void test_exception_5() {
        BoardT board1 = new BoardT();
        board1.setCell(4, 3, 8);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void test_exception_6() {
        BoardT board1 = new BoardT();
        board1.setCell(3, 4, 8);
    }

    @Test
    public void test_isEmpty() {
        BoardT board1 = new BoardT();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertTrue(board1.isEmpty(i, j));
            }
        }
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void test_exception_7() {
        BoardT board1 = new BoardT();
        board1.isEmpty(3, 4);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void test_exception_8() {
        BoardT board1 = new BoardT();
        board1.isEmpty(4, 3);
    }

    @Test
    public void test_isFull() {
        BoardT board1 = new BoardT();
        for (int i = 0; i < 15; i++) {
            board1.addCell(2);
            assertFalse(board1.isFull());
        }
        board1.addCell(2);
        assertTrue(board1.isFull());

        BoardT board2 = new BoardT();
        for (int i = 0; i < 15; i++) {
            board2.addCell(4);
            assertFalse(board2.isFull());
        }
        board2.addCell(4);
        assertTrue(board2.isFull());
    }

    @Test
    public void test_isOver() {
        BoardT board1 = new BoardT();
        for (int i = 0; i < 16; i++) {
            board1.addCell(2);
        }
        assertFalse(board1.isOver());

        BoardT board2 = new BoardT();
        board2.setCell(0, 0, 2);
        board2.setCell(0, 1, 16);
        board2.setCell(0, 2, 4);
        board2.setCell(0, 3, 2);

        board2.setCell(1, 0, 8);
        board2.setCell(1, 1, 128);
        board2.setCell(1, 2, 256);
        board2.setCell(1, 3, 8);

        board2.setCell(2, 0, 32);
        board2.setCell(2, 1, 8);
        board2.setCell(2, 2, 4);
        board2.setCell(2, 3, 2);

        board2.setCell(3, 0, 64);
        board2.setCell(3, 1, 16);
        board2.setCell(3, 2, 2);
        board2.setCell(3, 3, 8);

        assertTrue(board2.isOver());
    }

    @Test (expected = RuntimeException.class)
    public void test_exception_9() {
        BoardT board1 = new BoardT();
        for (int i = 0; i < 4; i++) {
            board1.setCell(0, i, 2);
            board1.setCell(1, i, 4);
            board1.setCell(2, i, 8);
            board1.setCell(3, i, 16);
        }
        board1.down();
    }

    @Test
    public void test_mergeColumnDown() {
        BoardT board1 = new BoardT();
        board1.setCell(0, 0, 2);
        board1.setCell(1, 0, 8);
        board1.setCell(2, 0, 32);
        board1.setCell(3, 0, 64);
        board1.mergeColumnDown(0);
        assertEquals(board1.getCell(0, 0), 2);
        assertEquals(board1.getCell(1, 0), 8);
        assertEquals(board1.getCell(2, 0), 32);
        assertEquals(board1.getCell(3, 0), 64);

        board1.setCell(0, 0, 8);
        board1.setCell(1, 0, 8);
        board1.setCell(2, 0, 32);
        board1.setCell(3, 0, 64);
        board1.mergeColumnDown(0);
        assertEquals(board1.getCell(0, 0), 0);
        assertEquals(board1.getCell(1, 0), 16);
        assertEquals(board1.getCell(2, 0), 32);
        assertEquals(board1.getCell(3, 0), 64);

        board1.setCell(0, 0, 32);
        board1.setCell(1, 0, 32);
        board1.setCell(2, 0, 32);
        board1.setCell(3, 0, 64);
        board1.mergeColumnDown(0);
        assertEquals(board1.getCell(0, 0), 0);
        assertEquals(board1.getCell(1, 0), 32);
        assertEquals(board1.getCell(2, 0), 64);
        assertEquals(board1.getCell(3, 0), 64);
    }

    @Test (expected = RuntimeException.class)
    public void test_exception_10() {
        BoardT board1 = new BoardT();
        for (int i = 0; i < 4; i++) {
            board1.setCell(0, i, 2);
            board1.setCell(1, i, 4);
            board1.setCell(2, i, 8);
            board1.setCell(3, i, 16);
        }
        board1.up();
    }

    @Test
    public void test_mergeColumnUp() {
        BoardT board1 = new BoardT();
        board1.setCell(0, 0, 2);
        board1.setCell(1, 0, 8);
        board1.setCell(2, 0, 32);
        board1.setCell(3, 0, 64);
        board1.mergeColumnUp(0);
        assertEquals(board1.getCell(0, 0), 2);
        assertEquals(board1.getCell(1, 0), 8);
        assertEquals(board1.getCell(2, 0), 32);
        assertEquals(board1.getCell(3, 0), 64);

        board1.setCell(0, 0, 8);
        board1.setCell(1, 0, 8);
        board1.setCell(2, 0, 32);
        board1.setCell(3, 0, 64);
        board1.mergeColumnUp(0);
        assertEquals(board1.getCell(0, 0), 16);
        assertEquals(board1.getCell(1, 0), 32);
        assertEquals(board1.getCell(2, 0), 64);
        assertEquals(board1.getCell(3, 0), 0);

        board1.setCell(0, 0, 32);
        board1.setCell(1, 0, 32);
        board1.setCell(2, 0, 32);
        board1.setCell(3, 0, 64);
        board1.mergeColumnUp(0);
        assertEquals(board1.getCell(0, 0), 64);
        assertEquals(board1.getCell(1, 0), 32);
        assertEquals(board1.getCell(2, 0), 64);
        assertEquals(board1.getCell(3, 0), 0);
    }

    @Test (expected = RuntimeException.class)
    public void test_exception_11() {
        BoardT board1 = new BoardT();
        for (int i = 0; i < 4; i++) {
            board1.setCell(i, 0, 2);
            board1.setCell(i, 1, 4);
            board1.setCell(i, 2, 8);
            board1.setCell(i, 3, 16);
        }
        board1.left();
    }

    @Test
    public void test_mergeRowLeft() {
        BoardT board1 = new BoardT();
        board1.setCell(0, 0, 2);
        board1.setCell(0, 1, 8);
        board1.setCell(0, 2, 32);
        board1.setCell(0, 3, 64);
        board1.mergeRowLeft(0);
        assertEquals(board1.getCell(0, 0), 2);
        assertEquals(board1.getCell(0, 1), 8);
        assertEquals(board1.getCell(0, 2), 32);
        assertEquals(board1.getCell(0, 3), 64);

        board1.setCell(0, 0, 8);
        board1.setCell(0, 1, 8);
        board1.setCell(0, 2, 32);
        board1.setCell(0, 3, 64);
        board1.mergeRowLeft(0);
        assertEquals(board1.getCell(0, 0), 16);
        assertEquals(board1.getCell(0, 1), 32);
        assertEquals(board1.getCell(0, 2), 64);
        assertEquals(board1.getCell(0, 3), 0);

        board1.setCell(0, 0, 32);
        board1.setCell(0, 1, 32);
        board1.setCell(0, 2, 32);
        board1.setCell(0, 3, 64);
        board1.mergeRowLeft(0);
        assertEquals(board1.getCell(0, 0), 64);
        assertEquals(board1.getCell(0, 1), 32);
        assertEquals(board1.getCell(0, 2), 64);
        assertEquals(board1.getCell(0, 3), 0);
    }

    @Test (expected = RuntimeException.class)
    public void test_exception_12() {
        BoardT board1 = new BoardT();
        for (int i = 0; i < 4; i++) {
            board1.setCell(i, 0, 2);
            board1.setCell(i, 1, 4);
            board1.setCell(i, 2, 8);
            board1.setCell(i, 3, 16);
        }
        board1.right();
    }

    @Test
    public void test_mergeRowRight() {
        BoardT board1 = new BoardT();
        board1.setCell(0, 0, 2);
        board1.setCell(0, 1, 8);
        board1.setCell(0, 2, 32);
        board1.setCell(0, 3, 64);
        board1.mergeRowRight(0);
        assertEquals(board1.getCell(0, 0), 2);
        assertEquals(board1.getCell(0, 1), 8);
        assertEquals(board1.getCell(0, 2), 32);
        assertEquals(board1.getCell(0, 3), 64);

        board1.setCell(0, 0, 8);
        board1.setCell(0, 1, 8);
        board1.setCell(0, 2, 32);
        board1.setCell(0, 3, 64);
        board1.mergeRowRight(0);
        assertEquals(board1.getCell(0, 0), 0);
        assertEquals(board1.getCell(0, 1), 16);
        assertEquals(board1.getCell(0, 2), 32);
        assertEquals(board1.getCell(0, 3), 64);

        board1.setCell(0, 0, 32);
        board1.setCell(0, 1, 32);
        board1.setCell(0, 2, 32);
        board1.setCell(0, 3, 64);
        board1.mergeRowRight(0);
        assertEquals(board1.getCell(0, 0), 0);
        assertEquals(board1.getCell(0, 1), 32);
        assertEquals(board1.getCell(0, 2), 64);
        assertEquals(board1.getCell(0, 3), 64);
    }

}


