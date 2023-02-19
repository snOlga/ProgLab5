package CommandsPackage;

import OrganizationsPackage.Organization;

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
        System.exit(0);
        History.commandsHistory.add(this);
    }

    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        execute(command, executedMap);
    }
}
