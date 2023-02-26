package GetPackage;

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
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//        try
//        {
            String line;
            line = GetStringFromConsole.getNotNullString();

            return line;
//        } catch (NullPointerException exception)
//        {
//            System.out.println("Not");
//        }
    }
}
