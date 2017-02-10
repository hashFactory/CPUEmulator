
public class CPURegisters
{
    public byte[] reg;
    public short[] regw;
    public short pc = 0;
    public short pl = 0;
    public short sh = 0;
    public CPURegisters()
    {
        reg = new byte[0x7f];
        regw = new short[0x0400];
    }
}
