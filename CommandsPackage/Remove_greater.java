package CommandsPackage;

import GetPackage.GettingOrganizations;
import OrganizationsPackage.Organization;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Command class REMOVE_GREATER - removing organizations from collection that have ID greater than written ID, working with TreeMap
 */
public class Remove_greater extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - removing organizations from collection that have ID greater than written ID." +
                "\n\tCommand format: REMOVE_GREATER [ID]");
    }

    @Override
    public void execute(String command, TreeMap<Integer, Organization> executedMap)
    {
        if (checkForDublicatedID(command,executedMap))
        {
            TreeMap<Integer, Organization> bufferMap = new TreeMap<>();
            String[] commandLine = command.split(" ");
            int keyIndex = 1;
            int writedID = Integer.parseInt(commandLine[keyIndex]);

            Set<Map.Entry<Integer, Organization> > entrySet
                    = executedMap.entrySet();

            Map.Entry<Integer, Organization>[] entryArray
                    = entrySet.toArray(new Map.Entry[entrySet.size()]);

            for (int i = 0; i < executedMap.size(); i++)
            {
                if (entryArray[i].getKey() <= writedID)
                {
                    bufferMap.put(entryArray[i].getKey(), entryArray[i].getValue());
                }
            }
            GettingOrganizations.organizationMap = bufferMap;
            History.commandsHistory.add(this);
        }
    }

    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        execute(command,executedMap);
    }
}
