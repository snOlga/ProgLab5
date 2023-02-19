package CommandsPackage;

import OrganizationsPackage.Organization;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Command class SHOW -  - showing all elements in collection, working with TreeMap
 */
public class Show extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - showing all elements in collection.");
    }

    @Override
    public void execute(String command, TreeMap<Integer, Organization> executedMap)
    {
        //System.out.println(executedMap.toString());

        Set<Map.Entry<Integer, Organization> > entrySet
                = executedMap.entrySet();

        Map.Entry<Integer, Organization>[] entryArray
                = entrySet.toArray(new Map.Entry[entrySet.size()]);

        for(int i = 0; i < executedMap.size(); i++)
        {
            System.out.println(entryArray[i]);
        }

        History.commandsHistory.add(this);
    }

    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        execute(command,executedMap);
    }
}
