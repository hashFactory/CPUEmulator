import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Compiler
{
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
        byte[][] instructions = {{CPUMain.PUSH, 0x45}, {CPUMain.POP, 0x00}, {CPUMain.PLACE, 0x03, 0x35}, {CPUMain.DIV, 0x02, 0x03}, {CPUMain.PRINT, 0x02}};
        for (byte[] instruction: instructions)
        {
            // Write to file the desired bytes
            Files.write(path_to_program, instruction, StandardOpenOption.APPEND);
        }
        // Output completion of the process to the user
        System.out.println("Done writing program to " + filename);
    }
}
