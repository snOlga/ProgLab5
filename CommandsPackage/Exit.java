package CommandsPackage;

import GetPackage.GetStringFromConsole;
import GetPackage.GettingOrganizations;
import OrganizationsPackage.Organization;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Command class EXIT - exiting from program
 */
public class Exit extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - exiting from program.");
    }

    @Override
    public void execute(String command, TreeMap<Integer, Organization> executedMap)
    {
        Save save = new Save();
        if (command == null)
        {

            save.execute("exit", executedMap);
            System.exit(0);

        }
        else if (History.commandsHistory.get(History.commandsHistory.size() - 1).getClass() == save.getClass())
        {
            System.exit(0);
        }
        else
        {
            System.out.println("Do you want to save your data? Write 'y' for yes and 'n' for no.");
            try
            {
                String changeOrNo = GetStringFromConsole.getNotNullString();
                if (changeOrNo.toLowerCase().contains("y"))
                {
                    save.execute(command, executedMap);
                }
                else
                {
                    System.exit(0);
                }
            } catch (IOException e)
            {
                System.out.println(e);
            }
        }
    }

    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        execute(command, executedMap);
    }
}
