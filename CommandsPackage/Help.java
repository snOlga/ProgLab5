package CommandsPackage;
import OrganizationsPackage.Organization;
import com.sun.tools.javac.Main;

import java.util.TreeMap;

/**
 * Command class HELP - writing in console information about all commands
 */
public class Help extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - writing in console information about all commands.");
    }

    @Override
    public void execute(String command, TreeMap<Integer, Organization> executedMap)
    {
        for (Command command1 : Command.createdCommands)
        {
            command1.getHelp();
        }
        History.commandsHistory.add(this);
    }

    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        execute(command,executedMap);
    }
}
