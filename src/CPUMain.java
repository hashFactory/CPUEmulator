import java.io.IOException;
import java.nio.file.*;

// 8-bit instructions with 8 bit data
// Go through program file, send instruction and data to CPUInstructions, depending on instruction, send pointer along with it

public class CPUMain
{
    public static final byte MOV = 0x01;
    public static final byte PUSH = 0x02;
    public static final byte POP = 0x03;
    public static final byte ADD = 0x04;
    public static final byte SUB = 0x05;
    public static final byte PLACE = 0x06;
    public static final byte PRINT = 0x07;
    public static final byte MUL = 0x08;
    public static final byte DIV = 0x09;

    private static CPUInstructions set = new CPUInstructions(1024);

    public static void main(String [] args) throws IOException
    {
        Path path_to_program = Paths.get("testing.out");

        print_program(path_to_program);
        run_program(path_to_program);
        System.exit(0);
    }

    public static void run_program(Path path_to_program) throws IOException
    {
        byte[] program_data = Files.readAllBytes(path_to_program);
        int program_data_length = program_data.length;

        boolean is_instruction = true;
        for (int i = 0; i < program_data_length; i+=2)
        {
            byte current_byte = program_data[i];
            if (is_instruction)
            {
                switch (current_byte)
                {
                    case MOV:
                        set.mov(program_data[i + 1], program_data[i + 2]);
                        i++;
                        break;
                    case PUSH:
                        set.push(program_data[i + 1]);
                        break;
                    case POP:
                        set.pop(program_data[i + 1]);
                        break;
                    case ADD:
                        set.add(program_data[i + 1], program_data[i + 2]);
                        i++;
                        break;
                    case SUB:
                        set.sub(program_data[i + 1], program_data[i + 2]);
                        i++;
                        break;
                    case PLACE:
                        set.place(program_data[i + 1], program_data[i + 2]);
                        i++;
                        break;
                    case PRINT:
                        set.print(program_data[i + 1]);
                        break;
                    case MUL:
                        set.mul(program_data[i + 1], program_data[i + 2]);
                        i++;
                        break;
                    case DIV:
                        set.div(program_data[i + 1], program_data[i + 2]);
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
            System.out.print(mem + " ");
        }
    }

    private static void print_registers(CPURegisters reg)
    {

    }
}
