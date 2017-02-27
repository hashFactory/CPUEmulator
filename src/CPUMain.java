import java.applet.Applet;
import java.awt.*;
import java.io.IOException;
import java.nio.file.*;

// 8-bit instructions with 8 bit data
// Go through program file, send instruction and data to CPUInstructions, depending on instruction, send pointer along with it

public class CPUMain
{
    // TODO: Implement 32-bit arithmetic
    // TODO: Implement read and write from file (pointer to filename & length of data segment)
    // TODO: Priority low: literal instructions vs pointer instructions
    // TODO: Implement dynamic references to memory locations in program
    // TODO NEVER: - Implement floating point
    // TODO: Video buffer - next thing to do
    // TODO: Implement interupts that output essential program data
    // TODO: Make a basic operation system
    // TODO: Virtual hard drive
    // Pat yourself on the back bud, you did great!

    public static final byte MOV = 0x01;
    public static final byte PUSH = 0x02;
    public static final byte POP = 0x03;
    public static final byte ADD = 0x04;
    public static final byte SUB = 0x05;
    public static final byte PLACE = 0x06;
    public static final byte PRINT = 0x07;
    public static final byte MUL = 0x08;
    public static final byte DIV = 0x09;
    public static final byte AND = 0x0a;
    public static final byte OR = 0x0b;
    public static final byte NOT = 0x0c;
    public static final byte NAND = 0x0d;
    public static final byte NOR = 0x0e;
    public static final byte XOR = 0x0f;
    public static final byte PRINTHEX = 0x10;
    public static final byte JMP = 0x11;
    public static final byte CMP = 0x12;
    public static final byte HLT = 0x13;
    public static final byte RND = 0x14;
    public static final byte PRINTCHAR = 0x15;
    public static final byte PLACEW = 0x16;
    public static final byte ADDW = 0x17;
    public static final byte SUBW = 0x18;
    public static final byte MULW = 0x19;
    public static final byte DIVW = 0x1a;
    public static final byte PRINTW = 0x1b;
    public static final byte PRINTHEXW = 0x1c;
    public static final byte CMPW = 0x1d;
    public static final byte GTW = 0x1e;
    public static final byte LTW = 0x1f;
    public static final byte JMPW = 0x20;
    public static final byte INPUT = 0x21;
    public static final byte RNDW = 0x22;
    public static final byte CP = 0x23; // TODO
    public static final byte PRINTREG = 0x25;
    public static final byte CASTWB = 0x26;
    public static final byte CASTBW = 0x27;
    public static final byte RNDRANGE = 0x28;
    public static final byte INT = 0x29; // TODO
    public static final byte SLEEP = 0x2a;
    public static final byte MOD = 0x2b;
    public static final byte MODW = 0x2c;
    public static final byte INPUTNUM = 0x2d;
    public static final byte INPUTNUMW = 0x2e;

    private static CPUInstructions set = new CPUInstructions();

    public static void main(String [] args) throws IOException
    {
        // TODO TEMPORARY FOR DEBUGGING PROGRAMS
        TextCompiler.main(new String[0]);

        String filename = "Examples/mod_example.wor.out";
        Path path_to_program = Paths.get(filename);
        System.out.println((char)27 + "[32mRunning " + (char)27 + "[1m" + (char)27 + "[34m" + filename + (char)27 + "[0m");

        set.setMemory((int)Files.size(path_to_program) + 44096);
        System.out.println((char)27 + "[32mSuccessfully initialized Registers, Memory, and Instruction Set" + (char)27 + "[0m");

        print_program(path_to_program);
        run_program(path_to_program);
        print_registers(set.registers);
        print_registers_w(set.registers);
        System.exit(0);
    }

    public static void run_program(Path path_to_program) throws IOException
    {
        byte[] program_data = Files.readAllBytes(path_to_program);
        System.arraycopy(program_data, 0, set.memory.stack, 0, program_data.length);
        int program_data_length = program_data.length;

        set.registers.pl = (short)program_data_length;

        boolean is_instruction = true;
        for (; set.registers.pc < program_data_length; set.registers.pc++)
        {
            byte current_byte = set.memory.stack[set.registers.pc];
            if (is_instruction)
            {
                switch (current_byte)
                {
                    case MOV:
                        set.mov(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3]);
                        set.registers.pc += 2;
                        break;
                    case PUSH:
                        set.push(set.memory.stack[set.registers.pc + 1]);
                        set.registers.pc ++;
                        break;
                    case POP:
                        set.pop(set.memory.stack[set.registers.pc + 1]);
                        set.registers.pc ++;
                        break;
                    case ADD:
                        set.add(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case SUB:
                        set.sub(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case PLACE:
                        set.place(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case PRINT:
                        set.print(set.memory.stack[set.registers.pc + 1]);
                        set.registers.pc ++ ;
                        break;
                    case MUL:
                        set.mul(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case DIV:
                        set.div(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case AND:
                        set.and(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case OR:
                        set.or(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case NOT:
                        set.not(set.memory.stack[set.registers.pc + 1]);
                        set.registers.pc ++ ;
                        break;
                    case NAND:
                        set.nand(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case NOR:
                        set.nor(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case XOR:
                        set.xor(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case PRINTHEX:
                        set.printhex(set.memory.stack[set.registers.pc + 1]);
                        set.registers.pc ++ ;
                        break;
                    case JMP:
                        set.jmp(set.memory.stack[set.registers.pc + 1]);
                        break;
                    case CMP:
                        set.cmp(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3], set.memory.stack[set.registers.pc + 4]);
                        break;
                    case HLT:
                        set.hlt();
                        break;
                    case RND:
                        set.rnd(set.memory.stack[set.registers.pc + 1]);
                        set.registers.pc ++;
                        break;
                    case PRINTCHAR:
                        set.printchar(set.memory.stack[set.registers.pc + 1]);
                        set.registers.pc ++;
                        break;
                    case PLACEW:
                        set.placew(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3], set.memory.stack[set.registers.pc + 4]);
                        set.registers.pc += 4;
                        break;
                    case ADDW:
                        set.addw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3], set.memory.stack[set.registers.pc + 4]);
                        set.registers.pc += 4;
                        break;
                    case SUBW:
                        set.subw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3], set.memory.stack[set.registers.pc + 4]);
                        set.registers.pc += 4;
                        break;
                    case MULW:
                        set.mulw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3], set.memory.stack[set.registers.pc + 4]);
                        set.registers.pc += 4;
                        break;
                    case DIVW:
                        set.divw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3], set.memory.stack[set.registers.pc + 4]);
                        set.registers.pc += 4;
                        break;
                    case PRINTW:
                        set.printw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case PRINTHEXW:
                        set.printhexw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case CMPW:
                        set.cmpw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3], set.memory.stack[set.registers.pc + 4], set.memory.stack[set.registers.pc + 5], set.memory.stack[set.registers.pc + 6], set.memory.stack[set.registers.pc + 7], set.memory.stack[set.registers.pc + 8]);
                        break;
                    case JMPW:
                        set.jmpw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        break;
                    case GTW:
                        set.gtw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3], set.memory.stack[set.registers.pc + 4], set.memory.stack[set.registers.pc + 5], set.memory.stack[set.registers.pc + 6], set.memory.stack[set.registers.pc + 7], set.memory.stack[set.registers.pc + 8]);
                        break;
                    case LTW:
                        set.ltw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3], set.memory.stack[set.registers.pc + 4], set.memory.stack[set.registers.pc + 5], set.memory.stack[set.registers.pc + 6], set.memory.stack[set.registers.pc + 7], set.memory.stack[set.registers.pc + 8]);
                        break;
                    case INPUT:
                        set.input(set.memory.stack[set.registers.pc + 1]);
                        set.registers.pc ++;
                        break;
                    case RNDW:
                        set.rndw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc+=2;
                        break;
                    case CP:
                        set.cp(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case PRINTREG:
                        set.printreg(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case CASTWB:
                        set.castwb(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3]);
                        set.registers.pc += 3;
                        break;
                    case CASTBW:
                        set.castbw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3]);
                        set.registers.pc += 3;
                        break;
                    case RNDRANGE:
                        set.rndrange(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3]);
                        set.registers.pc += 3;
                        break;
                    case SLEEP:
                        set.sleep(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case MOD:
                        set.mod(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case MODW:
                        set.modw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2], set.memory.stack[set.registers.pc + 3], set.memory.stack[set.registers.pc + 4]);
                        set.registers.pc += 4;
                        break;
                    case INPUTNUM:
                        set.inputnum(set.memory.stack[set.registers.pc + 1]);
                        set.registers.pc ++;
                        break;
                    case INPUTNUMW:
                        set.inputnumw(set.memory.stack[set.registers.pc + 1], set.memory.stack[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                }
                //System.out.println("Executed " + String.format("0x%04x", set.registers.pc) + " correctly");
            }
        }

        System.out.println((char)27 + "[33mProgram did " + (char)27 + "[31mNOT " + (char)27 + "[33mexit cleanly." + (char)27 + "[0m");
    }

    public static void print_program(Path path_to_program) throws IOException
    {
        System.out.println("Program Data: " + (char)27 + "[1m\t");
        byte[] program_data = Files.readAllBytes(path_to_program);
        System.out.print("\t\t");
        for (int i = 0; i < 0x10; i++)
        {
            System.out.print(String.format("0x%02x", i) + "\t");
        }

        System.out.print((char)27 + "[0m");
        for (int i = 0; i < program_data.length; i++)
        {
            if (i % 0x10 == 0)
            {
                System.out.print("\n" + (char)27 + "[1m" + String.format("0x%04x", i) + (char)27 + "[0m\t");
            }
            System.out.print(String.format("0x%02x", program_data[i]) + "\t");
        }
        System.out.print("\n");
    }

    private static void print_registers(CPURegisters reg)
    {
        for (int i = 0; i < reg.reg.length; i++)
        {
            System.out.println("Register " + (char)27 + "[1m" + String.format("0x%02x", i) + (char)27 + "[0m: " + String.format("0x%02x", reg.reg[i]) + " ");
        }
    }

    private static void print_registers_w(CPURegisters reg)
    {
        for (int i = 0; i < reg.regw.length; i++)
        {
            System.out.println("Register " + (char)27 + "[1m" + String.format("0x%04x", i) + (char)27 + "[0m: " + String.format("0x%04x", reg.regw[i]) + " ");
        }
    }

    public static void error_out(String error)
    {
        System.out.println((char)27 + "[31m" + error + (char)27 + "[0m");
    }

    public static int unsigned_to_byte(byte b)
    {
        return b & 0xff;
    }
}