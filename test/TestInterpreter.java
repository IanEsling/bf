import org.junit.*;
import static junit.framework.Assert.*;

/**
 */
public class TestInterpreter
{
    Interpreter interpreter;

    @Before
    public void setUp()
    {
        interpreter = new Interpreter();
    }

    @Test
    public void testSetByte()
    {
        interpreter.incrementPointer();
        interpreter.setByte((byte)5);
        interpreter.incrementPointer();
        interpreter.setByte((byte)7);
        assertEquals("memory[1] should be 5", interpreter.getByte(1), 5);
        assertEquals("memory[2] should be 7", interpreter.getByte(2), 7);
    }

    @Test
    public void testIncrementDecrementPointer()
    {
        interpreter.memory[1] = 1;
        interpreter.memory[2] = 2;
        assertEquals("memory[0] should be zero", interpreter.getByte(), 0);
        interpreter.incrementPointer();
        assertEquals("memory[1] should be 1", interpreter.getByte(), 1);
        interpreter.incrementPointer();
        assertEquals("memory[2] should be 2", interpreter.getByte(), 2);
        interpreter.decrementPointer();
        assertEquals("memory[1] should be 1", interpreter.getByte(), 1);
        interpreter.decrementPointer();
        assertEquals("memory[0] should be 0", interpreter.getByte(), 0);
    }

    @Test
    public void testIncrementAndDecrementByte()
    {
        interpreter.incrementByte();
        assertEquals(interpreter.getByte(0), 1);
        interpreter.decrementByte();
        assertEquals(interpreter.getByte(0), 0);
    }
}
