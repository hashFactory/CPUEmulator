import java.util.Scanner;

public class CPUInstructions
{
    public static CPURegisters registers = null;
    public static CPUMemory memory = null;
    public static Scanner kb = new Scanner(System.in);

    public CPUInstructions()
    {
        registers = new CPURegisters();
    }

    public void setMemory(int size)
    {
        memory = new CPUMemory(size);
    }

    public void mov(byte first, byte second, byte third)
    {
        memory.stack[(first & 0xff) + 256 * (second & 0xff)] = third;
    }

    public void push(byte first)
    {
        memory.stack[registers.pl + registers.sh] = first;
        registers.sh++;
    }

    public void pop(byte first)
    {
        registers.reg[0x02] = memory.stack[registers.pl + registers.sh - (short)(first)];
        registers.sh--;
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
        System.out.println("Register " +String.format("0x%02x", first) + ": " + registers.reg[first]);
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
        System.out.println("Register " + String.format("0x%02x", first) + ": " + String.format("0x%02x", registers.reg[first]));
    }

    public void jmp(short first)
    {
        registers.pc = (short)(first - 1);
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
        kb.close();
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

    public void placew(byte first, byte second, byte third, byte fourth)
    {
        registers.regw[first + second * 256] = (short)(third + fourth * 256);
    }

    public void addw(byte first, byte second, byte third, byte fourth)
    {
        registers.regw[first + second * 256] += registers.regw[third + fourth * 256];
    }

    public void subw(byte first, byte second, byte third, byte fourth)
    {
        registers.regw[first + second * 256] -= registers.regw[third + fourth * 256];
    }

    public void mulw(byte first, byte second, byte third, byte fourth)
    {
        registers.regw[first + second * 256] *= registers.regw[third + fourth * 256];
    }

    public void divw(byte first, byte second, byte third, byte fourth)
    {
        registers.regw[first + second * 256] /= registers.regw[third + fourth * 256];
    }

    public void printw(byte first, byte second)
    {
        System.out.println("Register " + String.format("0x%04x", first + 256 * second) + ": " + registers.regw[first + 256 * second]);
    }

    public void printhexw(byte first, byte second)
    {
        System.out.println("Register " + String.format("0x%04x", first + 256 * second) + ": " + String.format("0x%04x", registers.regw[first + 256 * second]));
    }

    public void cmpw(byte first, byte second, byte third, byte fourth, byte fifth, byte sixth, byte seventh, byte eighth)
    {
        if (registers.regw[first + 256 * second] == registers.regw[third + fourth * 256])
        {
            jmpw(fifth, sixth);
        }
        else
        {
            jmpw(seventh, eighth);
        }
    }

    public void gtw(byte first, byte second, byte third, byte fourth, byte fifth, byte sixth, byte seventh, byte eighth)
    {
        if (registers.regw[first + 256 * second] > registers.regw[third + fourth * 256])
        {
            jmpw(fifth, sixth);
        }
        else
        {
            jmpw(seventh, eighth);
        }
    }

    public void ltw(byte first, byte second, byte third, byte fourth, byte fifth, byte sixth, byte seventh, byte eighth)
    {
        if (registers.regw[first + 256 * second] < registers.regw[third + fourth * 256])
        {
            jmpw(sixth, seventh);
        }
        else
        {
            jmpw(seventh, eighth);
        }
    }

    public void jmpw(byte first, byte second)
    {
        registers.pc = (short)((first & 0xff) + 256 * (second & 0xff) - 1);
    }

    public void input(byte first)
    {
        registers.reg[first] = (byte)kb.next().charAt(0);
    }

    public void rndw(byte first, byte second)
    {
        registers.regw[first + 256 * second] = (short)(Math.random() * 65536);
    }

    public void cp(byte first, byte second)
    {
        registers.reg[first] = registers.reg[second];
    }

    public void printreg(byte first, byte second)
    {
        for (int i = first; i <= second; i++)
        {
            System.out.print((char)registers.reg[i]);
        }
    }

    public void castwb(byte first, byte second, byte third)
    {
        registers.reg[first] = (byte)registers.regw[second + 256 * third];
    }

    public void castbw(byte first, byte second, byte third)
    {
        registers.regw[first + second * 256] = (short)registers.reg[third];
    }

    public void rndrange(byte first, byte second, byte third)
    {
        registers.reg[first] = (byte)((Math.random() * (third - second)) + second);
    }

    public void sleep(byte first, byte second)
    {
        try {
            Thread.sleep(first + second * 256);
        }
        catch (InterruptedException ex)
        {
            CPUMain.error_out("Could not make thread sleep");
            System.exit(0);
        }
    }

}