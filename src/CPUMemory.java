
public class CPUMemory
{
    public byte[] stack;

    public CPUMemory(int byte_length)
    {
        // 0x1000
        stack = new byte[byte_length];
    }
}
