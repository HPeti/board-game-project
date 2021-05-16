package boardgame.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tinylog.Logger;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    Position testPosition;

    @BeforeEach
    void init(){
        testPosition = new Position(0,0);
    }

    @Test
    void moveTo() {
        assertEquals(new Position(1,0),testPosition.moveTo(SimpleDirection.DOWN));
        assertEquals(new Position(-1,0),testPosition.moveTo(SimpleDirection.UP));
        assertEquals(new Position(0,-1),testPosition.moveTo(SimpleDirection.LEFT));
        assertEquals(new Position(0,1),testPosition.moveTo(SimpleDirection.RIGHT));
    }

    @Test
    void testToString() {
        assertEquals("(0,0)",testPosition.toString());
        assertEquals("(1,0)",testPosition.moveTo(SimpleDirection.DOWN).toString());
        assertEquals("(0,1)",testPosition.moveTo(SimpleDirection.RIGHT).toString());
    }

}