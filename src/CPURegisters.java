
public class CPURegisters
{
    public byte[] reg;
    public short[] regw;
    public short pc = 0;
    public short pl = 0;
    public CPURegisters()
    {
        reg = new byte[128];
        regw = new short[256];
    }
}
