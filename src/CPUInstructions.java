
public class CPUInstructions
{
    public static CPURegisters registers = null;
    public static CPUMemory memory = null;

    public CPUInstructions()
    {
        registers = new CPURegisters();
    }

    public void setMemory(int size)
    {
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
        registers.reg[0x02] = memory.stack[registers.reg[1] - first];
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
        System.out.println("Register " + first + ": " + registers.reg[first]);
    }

    public void mul(byte first, byte second)
    {
        registers.reg[first] *= registers.reg[second];
    }

    public void div(byte first, byte second)
    {
        registers.reg[first] /= registers.reg[second];
    }

    public void and(byte first, byte second)
    {
        registers.reg[first] &= registers.reg[second];
    }

    public void or(byte first, byte second)
    {
        registers.reg[first] |= registers.reg[second];
    }

    public void not(byte first)
    {
        registers.reg[first] = (byte)~(int)(registers.reg[first]);
    }

    public void nand(byte first, byte second)
    {
        registers.reg[first] = (byte)~(registers.reg[first] & registers.reg[second]);
    }

    public void nor(byte first, byte second)
    {
        registers.reg[first] = (byte)~(registers.reg[first] | registers.reg[second]);
    }

    public void xor(byte first, byte second)
    {
        registers.reg[first] ^= registers.reg[second];
    }

    public void printhex(byte first)
    {
        System.out.println("Register " + first + ": " + String.format("0x%02x", registers.reg[first]));
    }

    public void jmp(byte first)
    {
        registers.reg[0] = (byte)(first - 1);
    }

    public void cmp(byte first, byte second, byte third, byte fourth)
    {
        if (registers.reg[first] == registers.reg[second])
        {
            jmp(third);
        }
        else
        {
            jmp(fourth);
        }
    }

    public void hlt()
    {
        System.out.println((char)27 + "[32mSuccessfully exited from emulation." + (char)27 + "[0m");
        System.exit(0);
    }

    public void rnd(byte first)
    {
        registers.reg[first] = (byte)(Math.random() * 256);
    }

    public void printchar(byte first)
    {
        System.out.print((char)registers.reg[first]);
    }
}
