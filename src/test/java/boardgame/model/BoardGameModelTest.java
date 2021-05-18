package boardgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {

    BoardGameModel testModel;

    @BeforeEach
    void init(){
        testModel = new BoardGameModel();
    }

    void makeBlueWinInRows(){
        testModel.move(5, SimpleDirection.UP);
        testModel.move(1, SimpleDirection.DOWN);
        testModel.move(5, SimpleDirection.UP);
        testModel.move(1, SimpleDirection.LEFT);
        testModel.move(5, SimpleDirection.UP);
        testModel.move(1, SimpleDirection.DOWN);
        testModel.move(5, SimpleDirection.UP);
    }
    void makeRedWinInRows(){
        testModel.move(2, SimpleDirection.DOWN);
        testModel.move(6, SimpleDirection.UP);
        testModel.move(2, SimpleDirection.LEFT);
        testModel.move(6, SimpleDirection.UP);
        testModel.move(2, SimpleDirection.DOWN);
        testModel.move(6, SimpleDirection.UP);
        testModel.move(2, SimpleDirection.DOWN);
        testModel.move(6, SimpleDirection.UP);
    }

    void makeBlueWinInCols(){
        testModel.move(5, SimpleDirection.UP);
        testModel.move(3, SimpleDirection.DOWN);
        testModel.move(5, SimpleDirection.UP);
        testModel.move(3, SimpleDirection.UP);
        testModel.move(5, SimpleDirection.LEFT);
        testModel.move(3, SimpleDirection.DOWN);
        testModel.move(2, SimpleDirection.DOWN);
        testModel.move(3, SimpleDirection.UP);
        testModel.move(2, SimpleDirection.LEFT);
        testModel.move(3, SimpleDirection.DOWN);
        testModel.move(2, SimpleDirection.LEFT);
    }
    void makeRedWinInCols(){
        testModel.move(0, SimpleDirection.DOWN);
        testModel.move(6, SimpleDirection.UP);
        testModel.move(0, SimpleDirection.UP);
        testModel.move(6, SimpleDirection.UP);
        testModel.move(0, SimpleDirection.DOWN);
        testModel.move(6, SimpleDirection.RIGHT);
        testModel.move(0, SimpleDirection.UP);
        testModel.move(1, SimpleDirection.DOWN);
        testModel.move(0, SimpleDirection.DOWN);
        testModel.move(1, SimpleDirection.RIGHT);
        testModel.move(0, SimpleDirection.UP);
        testModel.move(1, SimpleDirection.RIGHT);
    }

    @Test
    void getPieceCount() {
        assertEquals(8, testModel.getPieceCount());
    }

    @Test
    void getPieceType() {
        assertEquals(PieceType.BLUE, testModel.getPieceType(0));
        assertEquals(PieceType.RED, testModel.getPieceType(1));
        assertThrows(IndexOutOfBoundsException.class, () -> {testModel.getPieceType(-1);});
    }

    @Test
    void getPieceNumber() {
        assertEquals(0, testModel.getPieceNumber(new Position(0,0)).getAsInt());
        assertEquals(1, testModel.getPieceNumber(new Position(0,1)).getAsInt());
        assertEquals(OptionalInt.empty(), testModel.getPieceNumber(new Position(1, 1)));
    }

    @Test
    void getPiecePosition() {
        assertEquals(new Position(0,0), testModel.getPiecePosition(0));
        assertEquals(new Position(0,1), testModel.getPiecePosition(1));
        assertEquals(new Position(4,0), testModel.getPiecePosition(4));
        assertNotEquals(new Position(0,0), testModel.getPiecePosition(7));
    }

    @Test
    void positionProperty(){
        assertEquals(new Position(0,0), testModel.positionProperty(0).get());
        assertEquals(new Position(0,1), testModel.positionProperty(1).get());
        assertEquals(new Position(4,0), testModel.positionProperty(4).get());
        assertNotEquals(new Position(0,0), testModel.positionProperty(7).get());
    }

    @Test
    void getPiecePositions() {
        assertEquals(new Position(0,0), testModel.getPiecePositions().get(0)); // blue

        testModel.move(0, SimpleDirection.DOWN);
        assertEquals(new Position(0,1), testModel.getPiecePositions().get(0)); // red
    }

    @Test
    void isValidMove() {
        assertTrue(testModel.isValidMove(0,SimpleDirection.DOWN));
        assertFalse(testModel.isValidMove(0,SimpleDirection.LEFT));
        assertThrows(IllegalArgumentException.class, () ->
        {
            testModel.isValidMove(-1,SimpleDirection.DOWN);
        });
    }

    @Test
    void getValidMoves() {
        assertTrue(testModel.getValidMoves(0).contains(SimpleDirection.DOWN));
        assertFalse(testModel.getValidMoves(0).contains(SimpleDirection.LEFT));
    }

    @Test
    void move() {
        testModel.move(0,SimpleDirection.DOWN);
        assertEquals(new Position(1,0),testModel.getPiecePosition(0));

        testModel.move(0,SimpleDirection.RIGHT);
        assertEquals(new Position(1,1),testModel.getPiecePosition(0));

        testModel.move(0,SimpleDirection.LEFT);
        assertEquals(new Position(1,0),testModel.getPiecePosition(0));

        testModel.move(0,SimpleDirection.UP);
        assertEquals(new Position(0,0),testModel.getPiecePosition(0));
    }

    @Test
    void gameOverProperty() {
        assertFalse(testModel.gameOverProperty().get());
        makeBlueWinInRows();
        assertTrue(testModel.gameOverProperty().get());

        init();
        makeBlueWinInCols();
        assertTrue(testModel.gameOverProperty().get());

        init();
        makeRedWinInRows();
        assertTrue(testModel.gameOverProperty().get());

        init();
        makeRedWinInCols();
        assertTrue(testModel.gameOverProperty().get());
    }

    @Test
    void isGameOver() {
        assertFalse(testModel.isGameOver());
        makeRedWinInRows();
        assertTrue(testModel.isGameOver());

        init();
        makeBlueWinInCols();
        assertTrue(testModel.isGameOver());

        init();
        makeRedWinInRows();
        assertTrue(testModel.isGameOver());

        init();
        makeRedWinInCols();
        assertTrue(testModel.isGameOver());
    }

    @Test
    void getWinCondition() {
        assertFalse(testModel.getWinCondition(PieceType.BLUE));
        assertFalse(testModel.getWinCondition(PieceType.RED));
        makeBlueWinInRows();
        assertTrue(testModel.getWinCondition(PieceType.BLUE));
        assertFalse(testModel.getWinCondition(PieceType.RED));

        init();
        makeBlueWinInCols();
        assertTrue(testModel.getWinCondition(PieceType.BLUE));
        assertFalse(testModel.getWinCondition(PieceType.RED));

        init();
        makeRedWinInRows();
        assertTrue(testModel.getWinCondition(PieceType.RED));
        assertFalse(testModel.getWinCondition(PieceType.BLUE));

        init();
        makeRedWinInCols();
        assertTrue(testModel.getWinCondition(PieceType.RED));
        assertFalse(testModel.getWinCondition(PieceType.BLUE));
    }

    @Test
    void isOnBoard() {
        assertTrue(BoardGameModel.isOnBoard(new Position(0,0)));
        assertTrue(BoardGameModel.isOnBoard(new Position(0,3)));
        assertTrue(BoardGameModel.isOnBoard(new Position(4,0)));
        assertTrue(BoardGameModel.isOnBoard(new Position(4,3)));
        assertFalse(BoardGameModel.isOnBoard(new Position(-1,-1)));
    }
}