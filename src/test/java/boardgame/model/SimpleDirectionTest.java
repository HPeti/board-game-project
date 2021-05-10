package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleDirectionTest {

    @Test
    void getRowChange() {
        assertEquals(1, SimpleDirection.DOWN.getRowChange());
        assertEquals(0, SimpleDirection.LEFT.getRowChange());
        assertEquals(-1, SimpleDirection.UP.getRowChange());
        assertEquals(0, SimpleDirection.RIGHT.getRowChange());
    }

    @Test
    void getColChange() {
        assertEquals(0, SimpleDirection.DOWN.getColChange());
        assertEquals(-1, SimpleDirection.LEFT.getColChange());
        assertEquals(0, SimpleDirection.UP.getColChange());
        assertEquals(1, SimpleDirection.RIGHT.getColChange());
    }

    @Test
    void of() {
        assertEquals(SimpleDirection.DOWN, SimpleDirection.of(1, 0));
        assertEquals(SimpleDirection.LEFT, SimpleDirection.of(0, -1));
        assertEquals(SimpleDirection.UP, SimpleDirection.of(-1, 0));
        assertEquals(SimpleDirection.RIGHT, SimpleDirection.of(0, 1));
        assertThrows(IllegalArgumentException.class, ()-> {
            SimpleDirection.of(1, 1);
        });
    }

    @Test
    void values() {
    }

    @Test
    void valueOf() {
    }
}