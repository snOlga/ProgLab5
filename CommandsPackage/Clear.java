package CommandsPackage;

import OrganizationsPackage.Organization;

import java.util.TreeMap;

/**
 * Command class CLEAR - clearing collection, working with TreeMap
 */
public class Clear extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - clearing collection.");
    }

    @Override
    public void execute(String command, TreeMap<Integer, Organization> executedMap)
    {
        executedMap.clear();
        History.commandsHistory.add(this);
    }

    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        execute(command,executedMap);
    }
}
