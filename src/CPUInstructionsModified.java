import java.util.Scanner;

public class CPUInstructionsModified
{
    public static CPURegisters registers = null;
    public static CPUMemory memory = null;
    public static Scanner kb = new Scanner(System.in);

    public CPUInstructionsModified()
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

    @Deprecated
    public void push(byte first)
    {
        memory.stack[registers.pl + registers.sh] = first;
        registers.sh++;
    }

    @Deprecated
    public void pop(byte first)
    {
        registers.reg[0x02] = memory.stack[registers.pl + registers.sh - first];
        registers.sh--;
    }

    @Deprecated
    public void add(byte first, byte second)
    {
        registers.reg[first] += registers.reg[second];
    }

    @Deprecated
    public void sub(byte first, byte second)
    {
        registers.reg[first] -= registers.reg[second];
    }

    @Deprecated
    public void place(byte first, byte second)
    {
        registers.reg[first] = second;
    }

    @Deprecated
    public void print(byte first)
    {
        System.out.println("Register " +String.format("0x%02x", first) + ": " + registers.reg[first]);
    }

    @Deprecated
    public void mul(byte first, byte second)
    {
        registers.reg[first] *= registers.reg[second];
    }

    @Deprecated
    public void div(byte first, byte second)
    {
        registers.reg[first] /= registers.reg[second];
    }

    @Deprecated
    public void and(byte first, byte second)
    {
        registers.reg[first] &= registers.reg[second];
    }

    @Deprecated
    public void or(byte first, byte second)
    {
        registers.reg[first] |= registers.reg[second];
    }

    @Deprecated
    public void not(byte first)
    {
        registers.reg[first] = (byte)~(int)(registers.reg[first]);
    }

    @Deprecated
    public void nand(byte first, byte second)
    {
        registers.reg[first] = (byte)~(registers.reg[first] & registers.reg[second]);
    }

    @Deprecated
    public void nor(byte first, byte second)
    {
        registers.reg[first] = (byte)~(registers.reg[first] | registers.reg[second]);
    }

    @Deprecated
    public void xor(byte first, byte second)
    {
        registers.reg[first] ^= registers.reg[second];
    }

    @Deprecated
    public void printhex(byte first)
    {
        System.out.println("Register " + String.format("0x%02x", first) + ": " + String.format("0x%02x", registers.reg[first]));
    }

    @Deprecated
    public void jmp(byte first)
    {
        registers.pc = (byte)(first - 1);
    }

    @Deprecated
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
        kb.close();
        System.exit(0);
    }

    @Deprecated
    public void rnd(byte first)
    {
        registers.reg[first] = (byte)(Math.random() * 256);
    }

    @Deprecated
    public void printchar(byte first)
    {
        System.out.print((char)registers.reg[first]);
    }

    public void placew(short first, short second)
    {
        registers.regw[first] = second;
    }

    public void addw(short first, short second)
    {
        registers.regw[first] += registers.regw[second];
    }

    public void subw(short first, short second)
    {
        registers.regw[first] -= registers.regw[second];
    }

    public void mulw(short first, short second)
    {
        registers.regw[first] *= registers.regw[second];
    }

    public void divw(short first, short second)
    {
        registers.regw[first] /= registers.regw[second];
    }

    public void printw(short first)
    {
        System.out.println("Register " + String.format("0x%04x", first) + ": " + registers.regw[first]);
    }

    public void printhexw(short first)
    {
        System.out.println("Register " + String.format("0x%04x", first) + ": " + String.format("0x%04x", registers.regw[first]));
    }

    public void cmpw(short first, short second, short third, short fourth)
    {
        if (registers.regw[first] == registers.regw[second])
        {
            jmpw(third);
        }
        else
        {
            jmpw(fourth);
        }
    }

    public void gtw(short first, short second, short third, short fourth)
    {
        if (registers.regw[first] > registers.regw[second])
        {
            jmpw(third);
        }
        else
        {
            jmpw(fourth);
        }
    }

    public void ltw(short first, short second, short third, short fourth)
    {
        if (registers.regw[first] <= registers.regw[second])
        {
            jmpw(third);
        }
        else
        {
            jmpw(fourth);
        }
    }

    public void jmpw(short first)
    {
        registers.pc = first;
    }

    @Deprecated
    public void input(byte first)
    {
        registers.reg[first] = kb.nextByte();
    }

    public void rndw(byte first, byte second)
    {
        registers.regw[first + 256 * second] = (byte)(Math.random() * 65536);
    }

    @Deprecated
    public void cp(byte first, byte second)
    {
        registers.reg[second] = registers.reg[first];
    }

    public void printreg(byte first, byte second)
    {
        for (int i = first; i <= second; i++)
        {
            System.out.print((char)registers.reg[i]);
        }
    }
}
