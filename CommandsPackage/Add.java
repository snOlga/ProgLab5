package CommandsPackage;

import GetPackage.GetStringFromConsole;
import GetPackage.GettingOrganizations;
import OrganizationsPackage.*;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Command class ADD - adding element to collection with written ID, working with TreeMap
 * If ID is duplicated, it asks if user want to change organization {@link Change}
 */
public class Add extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - adding organization in collection with written ID." +
                "\n\tCommand format: ADD [ID]");
    }

    /**
     * Adding Organization from console, one field after one
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
            int writedID = Integer.parseInt(commandLine[keyIndex]);
            System.out.println("ID is duplicated. You can change the organization with this ID or try another number. Do you want to change it? Type y for 'yes' or n for 'no'");
            try
            {
                String changeOrNo = GetStringFromConsole.getNotNullString();
                if (changeOrNo.toLowerCase().contains("y"))
                {
                    Change change = new Change();
                    change.execute(writedID, executedMap);
                }
            }
            catch (IOException e)
            {
                System.out.println(e);
            }
        }
        else if (checkForDataAfterCommand(command, "ID"))
        {
            String[] commandLine = command.split(" ");
            int keyIndex = 1;
            int writedID = Integer.parseInt(commandLine[keyIndex]);
            GettingOrganizations getOrganization = new GettingOrganizations();

            Organization organization = getOrganization.getOrganizationFromConsole();
            if (organization != null)
            {
                organization.setID(writedID);
                executedMap.put(writedID, organization);
                History.commandsHistory.add(this);
            }
        }
    }

    /**
     * Adding Organization using {@link GettingOrganizations}
     * Command format: ADD [ID] [DATA]
     * @param command
     * @param executedMap
     */
    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        try
        {
            if (checkForDublicatedID(command, executedMap))
            {
                System.out.println("In command " + this.getClass().getSimpleName() + " catch duplicated ID. Replace element?" +
                        "Type y for 'yes' or n for 'no' \n ID:" + command.split(" ")[1]);
                try
                {
                    String changeOrNo = GetStringFromConsole.getNotNullString();
                    if (changeOrNo.toLowerCase().contains("y"))
                    {
                        Change change = new Change();
                        change.executeFromScript(command, executedMap);
                    }
                } catch (IOException e)
                {
                    System.out.println(e);
                }
            } else if (checkForDataAfterCommand(command, "ID"))
            {
                GettingOrganizations gettingOrganizations = new GettingOrganizations();

                String[] bufferData = command.split(" ");
                //String organizationData = bufferData[2];
                int newID = Integer.parseInt(bufferData[1]);

                try
                {
                    Organization organization = gettingOrganizations.getOrganizationFromScript(command);
                    organization.setID(newID);
                    executedMap.put(newID, organization);
                    History.commandsHistory.add(this);
                } catch (Exception e)
                {
                    System.out.println("There is a problem with " + this.getClass().getSimpleName() + " in script. Problem with data.");
                }
            } else
            {
                System.out.println("There is a problem with " + this.getClass().getSimpleName() + " in script. No ID found.");
            }
        } catch (NumberFormatException e)
        {
            System.out.println("There is a problem with " + this.getClass().getSimpleName() + " in script. No ID found.");
        }
    }
}
