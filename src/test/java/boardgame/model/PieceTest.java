package boardgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    static Piece testPieceBlue;
    static Piece testPieceRed;

    @BeforeEach
    void init(){
        testPieceBlue = new Piece(PieceType.BLUE,new Position(0,0));
        testPieceRed = new Piece(PieceType.RED,new Position(1,1));
    }

    @Test
    void getType() {
        assertEquals(PieceType.BLUE, testPieceBlue.getType());
        assertEquals(PieceType.RED, testPieceRed.getType());
    }

    @Test
    void getPosition() {
        assertEquals(new Position(0,0), testPieceBlue.getPosition());
        assertEquals(new Position(1,1), testPieceRed.getPosition());
    }

    @Test
    void moveTo() {
        testPieceBlue.moveTo(SimpleDirection.DOWN);
        assertEquals(new Position(1, 0), testPieceBlue.getPosition());
        testPieceRed.moveTo(SimpleDirection.DOWN);
        assertEquals(new Position(2,1), testPieceRed.getPosition());
    }

    @Test
    void positionProperty() {
        assertEquals(new Position(0,0), testPieceBlue.positionProperty().get());
        assertEquals(new Position(1,1), testPieceRed.positionProperty().get());
    }

    @Test
    void testToString() {
        assertEquals("BLUE(0,0)", testPieceBlue.toString());
        assertEquals("RED(1,1)", testPieceRed.toString());
    }
}