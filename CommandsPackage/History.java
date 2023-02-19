package CommandsPackage;

import OrganizationsPackage.Organization;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Command class HISTORY - writing in console some last commands, working with ArrayList
 */
public class History extends Command
{

    protected static ArrayList<Command> commandsHistory = new ArrayList<>();

    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - writing in console some last commands." +
                "\n\tCommand format: HISTORY [COMMANDS_NUMBER]");
    }

    @Override
    public void execute(String command, TreeMap<Integer, Organization> executedMap)
    {
        if (checkForDataAfterCommand(command, "amount of commands"))
        {
            String[] line = command.split(" ");
            int amountIndex = 1;
            int amount = Integer.parseInt(line[amountIndex]);

            for (int i = commandsHistory.size()-1; i > 0; i--)
            {
                if(amount > 0)
                {
                    System.out.println(commandsHistory.get(i).getClass().getSimpleName());
                    amount--;
                }
            }
        }
    }

    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        execute(command,executedMap);
    }
}
