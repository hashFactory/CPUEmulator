import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Compiler
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

    // Register 0 is sacred, indicates placement in program
    // Register 1 is sacred, indicates pointer to position in stack
    public static void main(String [] args) throws IOException
    {
        // TODO: Let compilation occur on source file
        // Lets the user choose his outputing file
        String filename = "testing.out";
        // Initializes the path to said file
        Path path_to_program = Paths.get(filename);
        // Clears the file by writing nothing
        Files.write(path_to_program, new byte[]{});
        // Actual program in op-codes
        byte[][] instructions = {{PUSH, 0x45}, {POP, 0x00}, {PLACE, 0x03, 0x35}, {DIV, 0x02, 0x03}, {PRINT, 0x02}};
        for (byte[] instruction: instructions)
        {
            // Write to file the desired bytes
            Files.write(path_to_program, instruction, StandardOpenOption.APPEND);
        }
        System.out.println("Done writing program to " + filename);
    }

    private static void compile(String source, String output)
    {

    }
}
