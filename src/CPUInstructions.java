
public class CPUInstructions
{
    public static CPURegisters registers = null;
    public static CPUMemory memory = null;

    public CPUInstructions(int size)
    {
        registers = new CPURegisters();
        memory = new CPUMemory(size);
    }

    public void mov(byte first, byte second)
    {
        // TODO: Needs for program to be loaded from memory (not the case, file is in heaven)
    }

    public void push(byte first)
    {
        registers.reg[1]++;
        memory.stack[registers.reg[1]] = first;
    }

    public void pop(byte first)
    {
        registers.reg[0x02] = memory.stack[registers.reg[1]];
        registers.reg[1]--;
    }

    public void add(byte first, byte second)
    {
        registers.reg[first] += registers.reg[second];
    }

    public void sub(byte first, byte second)
    {
        registers.reg[first] -= registers.reg[second];
    }

    public void place(byte first, byte second)
    {
        registers.reg[first] = second;
    }

    public void print(byte first)
    {
        System.out.println("\nRegister " + first + ": " + registers.reg[first]);
    }

    public void mul(byte first, byte second)
    {
        registers.reg[first] *= registers.reg[second];
    }

    public void div(byte first, byte second)
    {
        registers.reg[first] /= registers.reg[second];
    }
}
