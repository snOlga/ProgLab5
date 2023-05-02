package CommandsPackage;

import GetPackage.GettingOrganizations;
import OrganizationsPackage.Organization;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Command class EXECUTE_SCRIPT - executing script from written file. In file must be script in format one line - one command
 */
public class Execute_script extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - executing script from written file." +
                "\n\tCommand format: EXECUTE_SCRIPT [FILE_NAME.TXT]");
    }

    @Override
    public void execute(String commandLine, TreeMap<Integer, Organization> executedMap)
    {
        if (checkForDataAfterCommand(commandLine, "file name"))
        {
            String[] lineWithCommand = commandLine.split(" ");
            int fileIndex = 1;
            String fileName = lineWithCommand[fileIndex];

            ArrayList<String> commands = new ArrayList<>();

            try
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
                String line;

                while((line = bufferedReader.readLine()) != null)
                {
//                    if (line.toLowerCase().contains("execute_script"))
//                    {
//                        System.out.println("Cannot execute script from file, because it contains another script execution.");
//                        return;
//                    }
                    commands.add(line);
                }

                for (int i = 0; i < commands.size(); i++)
                {
                    for (Command command:
                            Command.createdCommands)
                    {
                        if (command.canDo(commands.get(i)))
                        {
                            command.executeFromScript(commands.get(i), GettingOrganizations.organizationMap);
                        }
                    }
                }
            } catch (FileNotFoundException e)
            {
                System.out.println("File not found. Try again.");
            } catch (IOException e)
            {
                System.out.println(e);
            }

        }
    }

    private static ArrayList<String> amountOfExecutions = new ArrayList<>();

    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        if (checkForDataAfterCommand(command, "file name"))
        {
            String fileName = command.split(" ")[1];
            if (amountOfExecutions.contains(fileName))
            {
                System.out.println("Program catch recursive. It will be skipped.");
            } else
            {
                amountOfExecutions.add(fileName);
                this.execute(command, executedMap);
                amountOfExecutions.remove(amountOfExecutions.size()-1);
            }
        }
        else
        {
            System.out.println("There is a problem with " + this.getClass().getSimpleName() + " in script. No file name found.");
        }
    }
}
