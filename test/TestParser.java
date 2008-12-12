import static junit.framework.Assert.*;
import org.junit.*;

import java.util.*;

/**
 */
public class TestParser
{
    String program;
    List<String> actions;
    Parser testee;

    @Before
    public void setUp()
    {
        actions = new ArrayList<String>();
    }

    @Test
    public void testFormattedParserOutput() throws ParserException
    {
        program = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
        testee = newParser();
        assertEquals("formatted output incorrect",
                "Hello World!", testee.formattedOutput());
    }

    @Test(expected = StartBracketNotFoundException.class)
    public void testExceptionIfStartBracketNotFound() throws ParserException
    {
        program = "+>>[+-<]+]";
        newParser();
    }

    @Test(expected = EndBracketNotFoundException.class)
    public void testExceptionIfEndBracketNotFound() throws ParserException
    {
        program = ">[++";
        newParser();
    }

    @Test
    public void testFindCorrespondingOpenBracketIfByteAtPointerIsNonZeroAtCloseBracket() throws ParserException
    {
        program = "++++[>>>++[>++<-]<<<-]";
        testee = newParser();

        testMemory(testee, new byte[]{0, 0, 0, 0, 16});
    }

    private void testMemory(Parser parser, byte[] testBytes)
    {
        for (int i = 0; i < testBytes.length; i++)
        {
            assertEquals(testBytes[i], parser.interpreter.getMemory()[i]);
        }
    }

    @Test
    public void testJumpToCloseBracketIfByteAtPointerIsZeroAtOpenBracket() throws ParserException
    {
        program = ">[++[[+]+[+]++]]-";
        testee = new Parser(new MockInterpreter()
        {
            public byte getByte()
            {
                return interpreter.getByte();
            }
        });
        testee.parse(program);
        checkActions(2);
        assertEquals("pointer not incremented", "Pointer Incremented", actions.get(0));
        assertEquals("byte not decremented", "Byte Decremented", actions.get(1));
    }

    @Test
    public void testParseProgramFromOpenBracketIfByteAtPointerIsNonZero() throws ParserException
    {
        program = ">[+<]-";
        testee = new Parser(new MockInterpreter()
        {
            public byte getByte()
            {
                return interpreter.getByte();
            }
        });
        testee.parse(program);
        checkActions(2);
        assertEquals("byte not incremented", "Pointer Incremented", actions.get(0));
        assertEquals("byte not decremented", "Byte Decremented", actions.get(1));
    }

    @Test
    public void testLoopBackToOpenBracketAtCloseBracketIfByteAtPointerIsNonZero() throws ParserException
    {
        program = "+++[>+++>++>+<<<-]-";
        testee = newParser();
        testee.parse(program);
        testMemory(testee, new byte[]{-1, 9, 6, 3});
    }

    @Test
    public void testAllInstructions() throws ParserException
    {
        program = "+-<>.";
        newParser();
        checkActions(5);
        assertEquals("byte not incremented", "Byte Incremented", actions.get(0));
        assertEquals("byte not decremented", "Byte Decremented", actions.get(1));
        assertEquals("pointer not decremented", "Pointer Decremented", actions.get(2));
        assertEquals("pointer not incremented", "Pointer Incremented", actions.get(3));
        assertEquals("byte not got", "Byte Got", actions.get(4));
    }

    @Test
    public void testDecrementByte() throws ParserException
    {
        program = "-";
        newParser();
        checkActions(1);
        assertEquals("byte not decremented", "Byte Decremented", actions.get(0));
    }

    @Test
    public void testIncrementByte() throws ParserException
    {
        program = "+";
        newParser();
        checkActions(1);
        assertEquals("byte not incremented", "Byte Incremented", actions.get(0));
    }

    @Test
    public void testIncrementPointer() throws ParserException
    {
        program = ">";
        newParser();
        checkActions(1);
        assertEquals("pointer not incremented", "Pointer Incremented", actions.get(0));
    }

    @Test
    public void testDecrementPointer() throws ParserException
    {
        program = "<";
        newParser();
        checkActions(1);
        assertEquals("pointer not decremented", "Pointer Decremented", actions.get(0));
    }

    @Test
    public void testGetByte() throws ParserException
    {
        program = ".";
        newParser();
        checkActions(1);
        assertEquals("byte not got", "Byte Got", actions.get(0));
    }

    private void checkActions(int numberOfActions)
    {
        assertEquals("wrong number of actions present", numberOfActions, actions.size());
    }

    private Parser newParser() throws ParserException
    {
        testee = new Parser(new MockInterpreter());
        testee.parse(program);
        return testee;
    }

    class MockInterpreter implements BfInterpreter
    {
        BfInterpreter interpreter = new Interpreter();

        public void reset()
        {
            interpreter.reset();
        }

        public void setByte(byte newContents)
        {
        }

        public void incrementByte()
        {
            actions.add("Byte Incremented");
            interpreter.incrementByte();
        }

        public void decrementByte()
        {
            actions.add("Byte Decremented");
            interpreter.decrementByte();
        }

        public void decrementPointer()
        {
            actions.add("Pointer Decremented");
            interpreter.decrementPointer();
        }

        public void incrementPointer()
        {
            actions.add("Pointer Incremented");
            interpreter.incrementPointer();
        }

        public byte getByte()
        {
            actions.add("Byte Got");
            return interpreter.getByte();
        }

        public int getPointer()
        {
            return interpreter.getPointer();
        }

        public byte[] getMemory()
        {
            return interpreter.getMemory();
        }
    }
}
