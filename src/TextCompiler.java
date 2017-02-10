import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.*;
import java.io.IOException;
import java.util.Scanner;

public class TextCompiler
{
    public static String path = "Examples/rock.wor";
    public static File source = new File(path);

    public static void main(String [] args) throws IOException
    {
        String filename = path + ".out";
        Path path_to_program = Paths.get(filename);

        System.out.println((char)27 + "[32mCompiling " + (char)27 + "[1m" + (char)27 + "[34m" + source + (char)27 + "[0m" + (char)27 + "[32m into " + (char)27 + "[1m" + (char)27 + "[34m" + filename + (char)27 + "[0m");
        Files.write(path_to_program, new byte[]{});

        ByteBuffer instructions = ByteBuffer.allocate((int)Files.size(Paths.get(source.toURI())) / 2);

        Scanner sc = new Scanner(source);

        int length_of_program = 0;
        while (sc.hasNextLine())
        {
            byte[] line = parse(sc.nextLine());
            length_of_program += line.length;
            instructions.put(line);
        }

        byte[] instructions_shortened = new byte[length_of_program];

        System.arraycopy(instructions.array(), 0, instructions_shortened, 0, length_of_program);
        Files.write(path_to_program, instructions_shortened, StandardOpenOption.APPEND);

        sc.close();
        System.out.println((char)27 + "[32mCompilation successful." + (char)27 + "[0m");
        CPUMain.print_program(path_to_program);
    }

    private static byte[] parse(String line)
    {
        String[] split = line.split(" ");

        // Preprocessor
        for (int i = 0; i < split.length; i++)
        {
            try {
                if (split[i].substring(0, 3).equalsIgnoreCase("RBx") || split[i].substring(0, 3).equalsIgnoreCase("RWx"))
                {
                    split[i] = parse_static_value(split[i]);
                }
            }
            catch (StringIndexOutOfBoundsException ex)
            {
            }

            try
            {
                if (split[i].charAt(0) == 0x27 && split[i].charAt(2) == 0x27)
                {
                    split[i] = String.format("0x%02x", (int)split[i].charAt(1));
                }
            }
            catch (StringIndexOutOfBoundsException ex) {
            }
        }

        for (int i = 0; i < split.length; i++)
        {
            split[i] = split[i].toLowerCase().replaceAll("0x", "");
            split[i] = split[i].toLowerCase().replaceAll("1x", ""); // Thanks victor...
        }
        ByteBuffer bytes = ByteBuffer.allocate(split.length * 2);

        int instruction_length = 0;
        switch (split[0].toUpperCase())
        {

            case "MOV":
                bytes.put(CPUMain.MOV).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
            case "PUSH":
                bytes.put(CPUMain.PUSH).put(DatatypeConverter.parseHexBinary(split[1]));
                instruction_length = 2;
                break;
            case "POP":
                bytes.put(CPUMain.POP).put(DatatypeConverter.parseHexBinary(split[1]));
                instruction_length = 2;
                break;
            case "ADD":
                bytes.put(CPUMain.ADD).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
            case "SUB":
                bytes.put(CPUMain.SUB).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
            case "PLACE":
                bytes.put(CPUMain.PLACE).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
            case "PRINT":
                bytes.put(CPUMain.PRINT).put(DatatypeConverter.parseHexBinary(split[1]));
                instruction_length = 2;
                break;
            case "MUL":
                bytes.put(CPUMain.MUL).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
            case "DIV":
                bytes.put(CPUMain.DIV).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
            case "AND":
                bytes.put(CPUMain.AND).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
            case "OR":
                bytes.put(CPUMain.OR).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
            case "NOT":
                bytes.put(CPUMain.AND).put(DatatypeConverter.parseHexBinary(split[1]));
                instruction_length = 2;
                break;
            case "NAND":
                bytes.put(CPUMain.NAND).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
            case "NOR":
                bytes.put(CPUMain.NOR).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
            case "XOR":
                bytes.put(CPUMain.XOR).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
            case "PRINTHEX":
                bytes.put(CPUMain.PRINTHEX).put(DatatypeConverter.parseHexBinary(split[1]));
                instruction_length = 2;
                break;
            case "JMP":
                bytes.put(CPUMain.JMP).put(DatatypeConverter.parseHexBinary(split[1]));
                instruction_length = 2;
                break;
            case "CMP":
                bytes.put(CPUMain.CMP).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2])).put(DatatypeConverter.parseHexBinary(split[3])).put(DatatypeConverter.parseHexBinary(split[4]));
                instruction_length = 5;
                break;
            case "HLT":
                bytes.put(CPUMain.HLT);
                instruction_length = 1;
                break;
            case "RND":
                bytes.put(CPUMain.RND).put(DatatypeConverter.parseHexBinary(split[1]));
                instruction_length = 2;
                break;
            case "PRINTCHAR":
                bytes.put(CPUMain.PRINTCHAR).put(DatatypeConverter.parseHexBinary(split[1]));
                instruction_length = 2;
                break;
            case "PLACEW":
                bytes.put(CPUMain.PLACEW).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[1])).order(ByteOrder.LITTLE_ENDIAN).getShort()).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[2])).order(ByteOrder.LITTLE_ENDIAN).getShort());
                instruction_length = 5;
                break;
            case "ADDW":
                bytes.put(CPUMain.ADDW).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[1])).order(ByteOrder.LITTLE_ENDIAN).getShort()).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[2])).order(ByteOrder.LITTLE_ENDIAN).getShort());
                instruction_length = 5;
                break;
            case "PRINTW":
                bytes.put(CPUMain.PRINTW).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[1])).order(ByteOrder.LITTLE_ENDIAN).getShort());
                instruction_length = 3;
                break;
            case "PRINTHEXW":
                bytes.put(CPUMain.PRINTHEXW).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[1])).order(ByteOrder.LITTLE_ENDIAN).getShort());
                instruction_length = 3;
                break;
            case "CMPW":
                bytes.put(CPUMain.CMPW).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[1])).order(ByteOrder.LITTLE_ENDIAN).getShort()).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[2])).order(ByteOrder.LITTLE_ENDIAN).getShort()).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[3])).order(ByteOrder.LITTLE_ENDIAN).getShort()).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[4])).order(ByteOrder.LITTLE_ENDIAN).getShort());
                instruction_length = 9;
                break;
            case "GTW":
                bytes.put(CPUMain.GTW).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[1])).order(ByteOrder.LITTLE_ENDIAN).getShort()).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[2])).order(ByteOrder.LITTLE_ENDIAN).getShort()).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[3])).order(ByteOrder.LITTLE_ENDIAN).getShort()).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[4])).order(ByteOrder.LITTLE_ENDIAN).getShort());
                instruction_length = 9;
                break;
            case "LTW":
                bytes.put(CPUMain.LTW).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[1])).order(ByteOrder.LITTLE_ENDIAN).getShort()).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[2])).order(ByteOrder.LITTLE_ENDIAN).getShort()).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[3])).order(ByteOrder.LITTLE_ENDIAN).getShort()).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[4])).order(ByteOrder.LITTLE_ENDIAN).getShort());
                instruction_length = 9;
                break;
            case "JMPW":
                bytes.put(CPUMain.JMPW).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[1])).order(ByteOrder.LITTLE_ENDIAN).getShort());
                instruction_length = 3;
                break;
            case "INPUT":
                bytes.put(CPUMain.INPUT).put(DatatypeConverter.parseHexBinary(split[1]));
                instruction_length = 2;
                break;
            case "RNDW":
                bytes.put(CPUMain.RNDW).putShort(ByteBuffer.wrap(DatatypeConverter.parseHexBinary(split[1])).order(ByteOrder.LITTLE_ENDIAN).getShort());
                instruction_length = 3;
                break;
            case "CP":
                bytes.put(CPUMain.CP).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
            case "PRINTREG":
                bytes.put(CPUMain.PRINTREG).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                instruction_length = 3;
                break;
        }

        byte[] condensed_instruction = new byte[instruction_length];
        System.arraycopy(bytes.array(), 0, condensed_instruction, 0, instruction_length);

        if (bytes.position() == 0)
        {
            return new byte[0];
        }
        else
        {
            return condensed_instruction;
        }
    }

    private static String parse_static_value(String value)
    {
        //byte[] returning_value_temp = new byte[(value.length() - 1) / 2];
        //int length = 0;
        if (value.substring(0, 3).equalsIgnoreCase("RBx"))
        {
            value = value.replaceAll("RB", "0");
            value = value.replaceAll("rb", "0");
            //length = 1;
        }
        else if (value.substring(0, 3).equalsIgnoreCase("RWx"))
        {
            value = value.replaceAll("RW", "0");
            value = value.replaceAll("rw", "0");
        }

        //byte[] returning_value = new byte[length];
        //System.arraycopy(returning_value_temp, 0, returning_value, 0, length);
        return value;
    }
}
