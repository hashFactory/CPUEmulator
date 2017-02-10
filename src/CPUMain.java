import java.io.IOException;
import java.nio.file.*;

// 8-bit instructions with 8 bit data
// Go through program file, send instruction and data to CPUInstructions, depending on instruction, send pointer along with it

public class CPUMain
{
    // TODO: Implement read and write from file (pointer to filename & length of data segment)
    // TODO: Priority low: literal instructions vs pointer instructions
    // TODO NEVER: - Implement floating point
    // TODO: Video buffer
    // TODO: Code the MOV command
    // TODO: Implement interupts that output essential program data
    // TODO: Implement input, greater than, and less than
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

    public static final byte JMPW = 0x20;

    private static CPUInstructions set = new CPUInstructions();

    public static void main(String [] args) throws IOException
    {
        // TODO TEMPORARY
        TextCompiler.main(new String[0]);

        String filename = "small_counting.wor.out";
        Path path_to_program = Paths.get(filename);
        System.out.println((char)27 + "[32mRunning " + (char)27 + "[1m" + (char)27 + "[34m" + filename + (char)27 + "[0m");

        set.setMemory((int)Files.size(path_to_program) + 4096);
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
                        set.mov(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case PUSH:
                        set.push(program_data[set.registers.pc + 1]);
                        set.registers.pc ++;
                        break;
                    case POP:
                        set.pop(program_data[set.registers.pc + 1]);
                        set.registers.pc ++;
                        break;
                    case ADD:
                        set.add(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case SUB:
                        set.sub(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case PLACE:
                        set.place(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case PRINT:
                        set.print(program_data[set.registers.pc + 1]);
                        set.registers.pc ++ ;
                        break;
                    case MUL:
                        set.mul(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case DIV:
                        set.div(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case AND:
                        set.and(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case OR:
                        set.or(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case NOT:
                        set.not(program_data[set.registers.pc + 1]);
                        set.registers.pc ++ ;
                        break;
                    case NAND:
                        set.nand(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case NOR:
                        set.nor(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case XOR:
                        set.xor(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case PRINTHEX:
                        set.printhex(program_data[set.registers.pc + 1]);
                        set.registers.pc ++ ;
                        break;
                    case JMP:
                        set.jmp(program_data[set.registers.pc + 1]);
                        break;
                    case CMP:
                        set.cmp(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2], program_data[set.registers.pc + 3], program_data[set.registers.pc + 4]);
                        break;
                    case HLT:
                        set.hlt();
                        break;
                    case RND:
                        set.rnd(program_data[set.registers.pc + 1]);
                        set.registers.pc ++;
                        break;
                    case PRINTCHAR:
                        set.printchar(program_data[set.registers.pc + 1]);
                        set.registers.pc ++;
                        break;
                    case PLACEW:
                        set.placew(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2], program_data[set.registers.pc + 3], program_data[set.registers.pc + 4]);
                        set.registers.pc += 4;
                        break;
                    case ADDW:
                        set.addw(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2], program_data[set.registers.pc + 3], program_data[set.registers.pc + 4]);
                        set.registers.pc += 4;
                        break;
                    case SUBW:
                        set.subw(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2], program_data[set.registers.pc + 3], program_data[set.registers.pc + 4]);
                        set.registers.pc += 4;
                        break;
                    case MULW:
                        set.mulw(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2], program_data[set.registers.pc + 3], program_data[set.registers.pc + 4]);
                        set.registers.pc += 4;
                        break;
                    case DIVW:
                        set.divw(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2], program_data[set.registers.pc + 3], program_data[set.registers.pc + 4]);
                        set.registers.pc += 4;
                        break;
                    case PRINTW:
                        set.printw(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case PRINTHEXW:
                        set.printhexw(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                        break;
                    case JMPW:
                        set.jmpw(program_data[set.registers.pc + 1], program_data[set.registers.pc + 2]);
                        set.registers.pc += 2;
                }
            }
        }

        System.out.println((char)27 + "[33mProgram did " + (char)27 + "[31mNOT " + (char)27 + "[33mexit cleanly." + (char)27 + "[0m");
    }

    public static void print_program(Path path_to_program) throws IOException
    {
        System.out.println("Program Data: ");
        byte[] program_data = Files.readAllBytes(path_to_program);
        for (byte mem: program_data)
        {
            System.out.print(String.format("0x%02x", mem) + " ");
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
}