/**
 */
public class ByteOutputFormatter
{
    StringBuilder formattedOutput;

    ByteOutputFormatter()
    {
        reset();
    }

    void addByte(Byte addMe)
    {
        formattedOutput.append((char)addMe.byteValue());
    }

    String getOutput()
    {
        return formattedOutput.toString().trim();
    }

    void reset()
    {
        formattedOutput = new StringBuilder();
    }
}
