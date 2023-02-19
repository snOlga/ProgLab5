package CommandsPackage;

import GetPackage.GettingOrganizations;
import OrganizationsPackage.Organization;

import java.util.TreeMap;

/**
 * Command class CHANGE - changing organization from collection with written ID, working with TreeMap
 */
public class Change extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - changing organization from collection with written ID." +
                "\n\tCommand format: CHANGE [ID]");
    }

    /**
     * Changing organization using {@link GettingOrganizations}
     * @param command
     * @param executedMap
     */
    @Override
    public void execute(String command, TreeMap<Integer, Organization> executedMap)
    {
        if (checkForDublicatedID(command,executedMap))
        {
            String[] commandLine = command.split(" ");
            int keyIndex = 1;
            int writtenID = Integer.parseInt(commandLine[keyIndex]);
            GettingOrganizations gettingOrganizations = new GettingOrganizations();
            Organization organization = gettingOrganizations.getOrganizationFromConsole();
            if (organization != null)
            {
                organization.setID(writtenID);
                executedMap.replace(writtenID,organization);
            }
            History.commandsHistory.add(this);
        }
        else
        {
            System.out.println("No organization has such ID. Try again.");
        }
    }

    /**
     * Changing organization using {@link GettingOrganizations}
     * Line of data format is same as in csv file
     * Command format: CHANGE [ID] [DATA]
     * @param command
     * @param executedMap
     */
    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        if (checkForDublicatedID(command, executedMap))
        {
            GettingOrganizations gettingOrganizations = new GettingOrganizations();

            String[] bufferData = command.split(" ");
            String organizationData = bufferData[2];
            int newID = Integer.parseInt(bufferData[1]);

            try
            {
                Organization organization = gettingOrganizations.getOrganizationFromScript(command);
                organization.setID(newID);
                executedMap.replace(newID, organization);
                History.commandsHistory.add(this);
            } catch (Exception e)
            {
                System.out.println("There is a problem with " + this.getClass().getSimpleName() + " in script. Problem with data.");
            }
        }
        else
        {
            System.out.println("There is a problem with " + this.getClass().getSimpleName() + " in script. No ID found or ID not duplicated.");
        }
    }

    public void execute (int ID, TreeMap<Integer, Organization> executedMap)
    {
        String command = "update " + ID;
        execute(command,executedMap);
    }
}
