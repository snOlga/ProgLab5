package CommandsPackage;

import OrganizationsPackage.Organization;

import java.util.TreeMap;

/**
 * Interface "execute" for commands
 */
public interface Execute
{
    /**
     * Execute command from console
     * @param command
     * @param executedMap
     */
    public void execute(String command, TreeMap<Integer, Organization> executedMap);

    /**
     * Execute command from script
     * @param command
     * @param executedMap
     */
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap);
}
