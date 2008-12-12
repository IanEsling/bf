/**
 */
public class Interpreter implements BfInterpreter
{
    byte[] memory;
    int index;

    Interpreter()
    {
        reset();
    }

    public void reset()
    {
        memory = new byte[30000];
        index = 0;
    }

    public void setByte(byte newContents)
    {
        memory[index] = newContents;
    }

    public void incrementByte()
    {
        memory[index] = ++memory[index];
    }

    public void decrementByte()
    {
        memory[index] = --memory[index];
    }

    public void decrementPointer()
    {
        index--;
    }
    
    public void incrementPointer()
    {
        index++;
    }

    public byte getByte()
    {
        return memory[index];
    }

    public int getPointer()
    {
        return index;
    }

    public byte[] getMemory()
    {
        return memory;
    }

    public byte getByte(int memoryIndex)
    {
        return memory[memoryIndex];
    }
}
