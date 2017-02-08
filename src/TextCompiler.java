import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.*;
import java.io.IOException;
import java.util.Scanner;

public class TextCompiler
{
    public static void main(String [] args) throws IOException
    {
        File source = new File("test.wor");
        String filename = "test.wor.out";
        Path path_to_program = Paths.get(filename);

        Files.write(path_to_program, new byte[]{});

        //ByteBuffer instructions = ByteBuffer.allocate((int)Files.size(Paths.get(source.toURI())) / 2);
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
        System.out.println("Done writing program to " + filename);
        CPUMain.print_program(path_to_program);
    }

    private static byte[] parse(String line)
    {
        String[] split = line.split(" ");
        for (int i = 0; i < split.length; i++)
        {
            split[i] = split[i].toLowerCase().replaceAll("0x", "");
            split[i] = split[i].toLowerCase().replaceAll("1x", "");
        }
        ByteBuffer bytes = ByteBuffer.allocate(split.length);

        switch (split[0].toUpperCase())
        {
            case "MOV":
                bytes.put(CPUMain.MOV).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                break;
            case "PUSH":
                bytes.put(CPUMain.PUSH).put(DatatypeConverter.parseHexBinary(split[1]));
                break;
            case "POP":
                bytes.put(CPUMain.POP).put(DatatypeConverter.parseHexBinary(split[1]));
                break;
            case "ADD":
                bytes.put(CPUMain.ADD).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                break;
            case "SUB":
                bytes.put(CPUMain.SUB).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                break;
            case "PLACE":
                bytes.put(CPUMain.PLACE).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                break;
            case "PRINT":
                bytes.put(CPUMain.PRINT).put(DatatypeConverter.parseHexBinary(split[1]));
                break;
            case "MUL":
                bytes.put(CPUMain.MUL).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                break;
            case "DIV":
                bytes.put(CPUMain.DIV).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                break;
            case "AND":
                bytes.put(CPUMain.AND).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                break;
            case "OR":
                bytes.put(CPUMain.OR).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                break;
            case "NOT":
                bytes.put(CPUMain.AND).put(DatatypeConverter.parseHexBinary(split[1]));
                break;
            case "NAND":
                bytes.put(CPUMain.NAND).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                break;
            case "NOR":
                bytes.put(CPUMain.NOR).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
            break;
            case "XOR":
                bytes.put(CPUMain.XOR).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2]));
                break;
            case "PRINTHEX":
                bytes.put(CPUMain.PRINTHEX).put(DatatypeConverter.parseHexBinary(split[1]));
                break;
            case "JMP":
                bytes.put(CPUMain.JMP).put(DatatypeConverter.parseHexBinary(split[1]));
                break;
            case "CMP":
                bytes.put(CPUMain.CMP).put(DatatypeConverter.parseHexBinary(split[1])).put(DatatypeConverter.parseHexBinary(split[2])).put(DatatypeConverter.parseHexBinary(split[3])).put(DatatypeConverter.parseHexBinary(split[4]));
                break;
            case "HLT":
                bytes.put(CPUMain.HLT);
                break;
            case "RND":
                bytes.put(CPUMain.RND).put(DatatypeConverter.parseHexBinary(split[1]));
                break;
        }
        return bytes.array();
    }
}
