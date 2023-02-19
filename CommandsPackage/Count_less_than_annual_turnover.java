package CommandsPackage;

import OrganizationsPackage.Organization;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Command class COUNT_LESS_THAN_ANNUAL_TURNOVER - counting organizations with annual turnover less than written number, working with TreeMap
 */
public class Count_less_than_annual_turnover extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - counting organizations with annual turnover less than written number." +
                "\n\tCommand format: COUNT_LESS_THAN_ANNUAL_TURNOVER [ANNUAL_TURNOVER]");
    }

    @Override
    public void execute(String command, TreeMap<Integer, Organization> executedMap)
    {
        if (checkForDataAfterCommand(command, "annual turnover"))
        {
            int countOfTurnover = 0;

            String[] commandLine = command.split(" ");
            int keyIndex = 1;
            int writedTurnover = Integer.parseInt(commandLine[keyIndex]);

            Set<Map.Entry<Integer, Organization> > entrySet
                    = executedMap.entrySet();

            Map.Entry<Integer, Organization>[] entryArray
                    = entrySet.toArray(new Map.Entry[entrySet.size()]);

            for (int i = 0; i < executedMap.size(); i++)
            {
                if(entryArray[i].getValue().getAnnualTurnover() < writedTurnover)
                {
                    countOfTurnover++;
                }
            }
            System.out.println(countOfTurnover);
            History.commandsHistory.add(this);
        }
    }

    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        execute(command, executedMap);
    }
}
