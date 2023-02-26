package GetPackage;

import CommandsPackage.Exit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface GetStringFromConsole
{
    public static String getNotNullString() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line = reader.readLine();

        if (line == null)
        {
            Exit exit = new Exit();
            exit.execute(null, GetPackage.GettingOrganizations.organizationMap);
        }
        return line;
    }
}
