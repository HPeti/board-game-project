package boardgame.model;

import boardgame.player.Player;
import boardgame.player.PlayerState;
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
        assertEquals(new Position(0,0), testModel.getPiecePosition(0));
    }

    @Test
    void isValidMove() {
    }

    @Test
    void getValidMoves() {
    }

    @Test
    void gameOverProperty() {
        assertFalse(testModel.gameOverProperty().get());
    }

    @Test
    void isGameOver() {
        assertFalse(testModel.isGameOver());
    }

    @Test
    void getWinCondition() {
        assertFalse(testModel.getWinCondition(PieceType.BLUE));
        assertFalse(testModel.getWinCondition(PieceType.RED));
    }

    @Test
    void move() {
    }

    @Test
    void isOnBoard() {
        assertTrue(BoardGameModel.isOnBoard(new Position(0,0)));
        assertFalse(BoardGameModel.isOnBoard(new Position(-1,-1)));

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