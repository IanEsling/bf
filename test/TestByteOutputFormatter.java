import org.junit.*;
import static junit.framework.Assert.assertEquals;

/**
 */
public class TestByteOutputFormatter
{
    @Test
    public void testByteOutputFormatter()
    {
        Byte[] testOutput = new Byte[]{72, 101, 108, 108, 111};
        ByteOutputFormatter testMe = new ByteOutputFormatter();

        for (Byte addMe : testOutput)
        {
            testMe.addByte(addMe);
        }
        
        assertEquals("Hello", testMe.getOutput());
    }
}
