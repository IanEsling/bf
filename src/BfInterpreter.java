/**
 */
public interface BfInterpreter
{
    void reset();
    
    void setByte(byte newContents);

    void incrementByte();

    void decrementByte();

    void decrementPointer();

    void incrementPointer();

    byte getByte();

    int getPointer();

    byte[] getMemory();
}
