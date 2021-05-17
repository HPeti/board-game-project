package boardgame.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {

    static BoardGameModel testModel;

    @BeforeAll
    static void init(){
        testModel = new BoardGameModel();
    }

    @Test
    void getPieceCount() {
        assertEquals(8, testModel.getPieceCount());
    }

    @Test
    void getPieceType() {
        /*
        assertEquals(PieceType.BLUE, testModel.getPieceType(0));
        assertEquals(PieceType.RED, testModel.getPieceType(0));
        //TODO: illegal argument exception
         */
        //assertThrows(IndexOutOfBoundsException.class, testModel.getPieceType() )
    }

    @Test
    void getPiecePosition() {
    }

    @Test
    void isValidMove() {
    }

    @Test
    void getValidMoves() {
    }

    @Test
    void gameOverProperty() {
    }

    @Test
    void isGameOver() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void getWinCondition() {
    }

    @Test
    void move() {
    }

    @Test
    void isOnBoard() {

    }

    @Test
    void getPiecePositions() {
    }

    @Test
    void getPieceNumber() {
    }

    @Test
    void testToString() {
    }
}