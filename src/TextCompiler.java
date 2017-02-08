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
        File source = new File("test.tristan");
        String filename = "test.tristan.out";
        Path path_to_program = Paths.get(filename);

        Files.write(path_to_program, new byte[]{});

        ByteBuffer instructions = ByteBuffer.allocate((int)Files.size(Paths.get(source.toURI())) / 2);

        Scanner sc = new Scanner(source);

        while (sc.hasNextLine())
        {
            instructions.put(parse(sc.nextLine()));
        }

        Files.write(path_to_program, instructions.array(), StandardOpenOption.APPEND);

        sc.close();
        System.out.println("Done writing program to " + filename);
    }

    private static byte[] parse(String line)
    {
        String[] split = line.split(" ");
        for (int i = 0; i < split.length; i++)
        {
            split[i] = split[i].toLowerCase().replaceAll("0x", "");
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

        }
        return bytes.array();
    }
}
