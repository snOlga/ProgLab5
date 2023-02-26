import CommandsPackage.*;
import GetPackage.*;
import OrganizationsPackage.OrganizationType;

import java.io.*;

/**
 * @author Olga Safonova p3107
 * @version 1.0
 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        File bufferFile = new File(GettingOrganizations.bufferedFileName);

        GettingOrganizations reader = new GettingOrganizations();

        long length = bufferFile.length();

        if (length == 0)
        {
            String fileName = reader.getOrganizationFileName();
            reader.getFromFile(fileName);
        }
        else
        {
            System.out.println("There is some unsaved data. Do you want to start with it(type 'y' for yes, 'n' for no)?");
            String askAboutStartWithUnsaved = GetStringFromConsole.getNotNullString();
            if (askAboutStartWithUnsaved.toLowerCase().contains("y"))
            {
                reader.getFromFile(GettingOrganizations.bufferedFileName);
            }
            else
            {
                PrintWriter writer = new PrintWriter(GettingOrganizations.bufferedFileName);
                writer.print("");
                writer.close();
                String fileName = reader.getOrganizationFileName();
                reader.getFromFile(fileName);
            }
        }

        //System.out.println(reader.organizationMap.toString());

        GettingCommand readerForCommand = new GettingCommand();
        createListOfCommands();

        while (true)
        {
            String lineWithCommand = readerForCommand.getFromConsole();

            boolean checkForCommand = false;
            int commandCount = 0;

            for (Command command:
                 Command.createdCommands)
            {
                if (command.canDo(lineWithCommand))
                {
                    command.execute(lineWithCommand, reader.organizationMap);
                    checkForCommand = true;
                }
                commandCount++;
            }

            if (commandCount == Command.createdCommands.size() && checkForCommand == false)
            {
                System.out.println("Not a command");
            }
        }
    }



    static public void createListOfCommands()
    {
        Command.createdCommands.add(new Exit());
        Command.createdCommands.add(new Show());
        Command.createdCommands.add(new Save());
        Command.createdCommands.add(new Help());
        Command.createdCommands.add(new Add());
        Command.createdCommands.add(new Change());
        Command.createdCommands.add(new Clear());
        Command.createdCommands.add(new Remove_key());
        Command.createdCommands.add(new Count_less_than_annual_turnover());
        Command.createdCommands.add(new Remove_greater());
        Command.createdCommands.add(new History());
        Command.createdCommands.add(new Info());
        Command.createdCommands.add(new Update());
        Command.createdCommands.add(new Execute_script());
    }
}