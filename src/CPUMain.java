import java.io.IOException;
import java.nio.file.*;

// 8-bit instructions with 8 bit data
// Go through program file, send instruction and data to CPUInstructions, depending on instruction, send pointer along with it

public class CPUMain
{
    // TODO: Create 16-bit arithmetic
    // TODO: Create specific instructions for fixed data
    // TODO: Get the program into memory to implement GOTO command
    // TODO: Implement if statement (make it basically be a GOTO)
    // TODO: Implement read and write from file (pointer to filename & length of data segment)
    // Getting all of that done will lead to turing completeness (almost)
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

    private static CPUInstructions set = new CPUInstructions(1024);

    public static void main(String [] args) throws IOException
    {
        Path path_to_program = Paths.get("test.tristan.out");

        print_program(path_to_program);
        run_program(path_to_program);
        System.exit(0);
    }

    public static void run_program(Path path_to_program) throws IOException
    {
        byte[] program_data = Files.readAllBytes(path_to_program);
        int program_data_length = program_data.length;

        boolean is_instruction = true;
        for (int i = 0; i < program_data_length; i++)
        {
            byte current_byte = program_data[i];
            if (is_instruction)
            {
                switch (current_byte)
                {
                    case MOV:
                        set.mov(program_data[i + 1], program_data[i + 2]);
                        i+=2;
                        break;
                    case PUSH:
                        set.push(program_data[i + 1]);
                        i++;
                        break;
                    case POP:
                        set.pop(program_data[i + 1]);
                        i++;
                        break;
                    case ADD:
                        set.add(program_data[i + 1], program_data[i + 2]);
                        i+=2;
                        break;
                    case SUB:
                        set.sub(program_data[i + 1], program_data[i + 2]);
                        i+=2;
                        break;
                    case PLACE:
                        set.place(program_data[i + 1], program_data[i + 2]);
                        i+=2;
                        break;
                    case PRINT:
                        set.print(program_data[i + 1]);
                        i++;
                        break;
                    case MUL:
                        set.mul(program_data[i + 1], program_data[i + 2]);
                        i+=2;
                        break;
                    case DIV:
                        set.div(program_data[i + 1], program_data[i + 2]);
                        i+=2;
                        break;
                    case AND:
                        set.and(program_data[i + 1], program_data[i + 2]);
                        i+=2;
                        break;
                    case OR:
                        set.or(program_data[i + 1], program_data[i + 2]);
                        i+=2;
                        break;
                    case NOT:
                        set.not(program_data[i + 1]);
                        i++;
                        break;
                    case NAND:
                        set.nand(program_data[i + 1], program_data[i + 2]);
                        i+=2;
                        break;
                    case NOR:
                        set.nor(program_data[i + 1], program_data[i + 2]);
                        i+=2;
                        break;
                    case XOR:
                        set.xor(program_data[i + 1], program_data[i + 2]);
                        i+=2;
                        break;
                    case PRINTHEX:
                        set.printhex(program_data[i + 1]);
                        i++;
                        break;
                }
            }
        }
    }

    private static void print_program(Path path_to_program) throws IOException
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
        // TODO: Implement
    }
}