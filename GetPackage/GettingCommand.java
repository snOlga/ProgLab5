package GetPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * class GettingCommand - getting command from any ways
 */
public class GettingCommand
{
    /**
     * Getting command from console
     * @return String with line of command. Do not check for existing command
     */
    public String getFromConsole() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line;
        line = reader.readLine();

        return line;
    }
}
