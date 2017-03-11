public class Preprocessor
{
    public Preprocessor()
    {

    }

    public short pre_compile(String program_snippet_orig)
    {
        String[] program_snippet_lines = program_snippet_orig.split("\n");

        // Get rid of comments
        for (int i = 0; i < program_snippet_lines.length; i++)
        {
            int index = program_snippet_lines[i].indexOf(";");
            if (index != -1)
            {
                program_snippet_lines[i] = program_snippet_lines[i].substring(0, index);
            }
            program_snippet_lines[i].indexOf("/");
            if (index != -1)
            {
                program_snippet_lines[i] = program_snippet_lines[i].substring(0, index);
            }
        }

        return 2;
    }
}
