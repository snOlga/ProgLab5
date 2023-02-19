package CommandsPackage;

import OrganizationsPackage.Organization;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Abstract command class
 */
public abstract class Command implements Execute
{
    public abstract void getHelp();
    public boolean canDo(String line)
    {
        String classFromString = line.split(" ")[0].toLowerCase();
        var className = this.getClass().getSimpleName().toLowerCase();
        if (classFromString.equals(className))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static ArrayList<Command> createdCommands = new ArrayList<>();

    public static boolean checkForDublicatedID(String command, TreeMap<Integer, Organization> executedMap)
    {

        if (checkForDataAfterCommand(command, "ID") == false)
        {
            return false;
        }

        String[] commandLine = command.split(" ");
        int keyIndex = 1;
        int writedID = Integer.parseInt(commandLine[keyIndex]);
        boolean isDuplicatedID = false;

        Set<Map.Entry<Integer, Organization> > entrySet
                = executedMap.entrySet();

        Map.Entry<Integer, Organization>[] entryArray
                = entrySet.toArray(new Map.Entry[entrySet.size()]);

        for (int i = 0; i < executedMap.size(); i++)
        {
            if (writedID == entryArray[i].getKey())
            {
                isDuplicatedID = true;
                break;
            }
        }
        if (isDuplicatedID)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean  checkForDataAfterCommand (String command, String nameOfData)
    {
        String[] commandLine = command.split(" ");
        if (commandLine.length == 1)
        {
            System.out.println("No " + nameOfData + " found. Try again.");
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName();
    }
}
