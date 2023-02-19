package CommandsPackage;

import OrganizationsPackage.Organization;

import java.util.TreeMap;

/**
 * Command class HISTORY - writing information about collection, working with TreeMap
 */
public class Info extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - writing information about collection.");
    }

    @Override
    public void execute(String command, TreeMap<Integer, Organization> executedMap)
    {
        System.out.println("Collection: " + executedMap.getClass().getSimpleName() +
                "\nsize: " + executedMap.size());
        History.commandsHistory.add(this);
    }

    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        execute(command,executedMap);
    }
}
