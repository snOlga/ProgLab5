package CommandsPackage;

import OrganizationsPackage.Organization;

import java.util.TreeMap;

/**
 * Command class REMOVE_GREATER - removing organizations from collection that have written ID, working with TreeMap
 */
public class Remove_key extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - removing organizations from collection that have written ID." +
                "\n\tCommand format: REMOVE_KEY [ID]");
    }

    @Override
    public void execute(String command, TreeMap<Integer, Organization> executedMap)
    {
        if(checkForDublicatedID(command,executedMap))
        {
            String[] commandLine = command.split(" ");
            int keyIndex = 1;
            int writedID = Integer.parseInt(commandLine[keyIndex]);
            executedMap.remove(writedID);
            History.commandsHistory.add(this);
        }
        else
        {
            System.out.println("No organization has such ID. Try again.");
        }
    }

    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        if (checkForDublicatedID(command, executedMap))
        {
            execute(command, executedMap);
        }
        else
        {
            System.out.println("There is a problem with " + this.getClass().getSimpleName() + " in script.");
        }
    }
}
