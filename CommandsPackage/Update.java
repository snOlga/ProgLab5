package CommandsPackage;

import OrganizationsPackage.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Command class UPDATE - updating one field in organization with written ID
 */
public class Update extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - updating one field in organization with written ID." +
                "\n\tCommand format: UPDATE [ID]");
    }

    /**
     * Updating one field in organization that user wrote
     * @param command - line with command
     * @param executedMap - executed TreeMap
     */
    @Override
    public void execute(String command, TreeMap<Integer, Organization> executedMap)
    {
        if (checkForDublicatedID(command, executedMap))
        {
            Set<Map.Entry<Integer, Organization> > entrySet
                    = executedMap.entrySet();

            Map.Entry<Integer, Organization>[] entryArray
                    = entrySet.toArray(new Map.Entry[entrySet.size()]);

            int foundID = Integer.parseInt(command.split(" ")[1]);

            Organization executedOrganization = null;

            for (int i = 0; i < executedMap.size(); i++)
            {
                if (entryArray[i].getKey() == foundID)
                {
                    executedOrganization = entryArray[i].getValue();
                }
            }


            System.out.println("What data do you want to change?");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;

            try
            {
                line = reader.readLine();
                try
                {
                    switch (line.toLowerCase())
                    {
                        case "id":
                            System.out.println("Write new ID:");
                            line = reader.readLine();
                            int newID = Integer.parseInt(line);
                            boolean isUseID = false;
                            for (int i = 0; i < executedMap.size(); i++)
                            {
                                if (entryArray[i].getKey() == newID)
                                {
                                    System.out.println("Such ID is using.");
                                    isUseID = true;
                                    break;
                                }
                            }
                            if (!isUseID)
                            {
                                executedOrganization.setID(newID);
                            }
                            break;
                        case "name":
                            System.out.println("Write new name:");
                            line = reader.readLine();
                            executedOrganization.setName(line);
                            break;
                        case "coordinates":
                            System.out.println("Write new coordinates:");
                            line = reader.readLine();
                            String[] parts = line.split(" ");
                            long x = Long.parseLong(parts[0]);
                            Double y = Double.parseDouble(parts[1]);
                            executedOrganization.setCoordinates(new Coordinates(x, y));
                            break;
                        case "annual turnover", "annualturnover":
                            System.out.println("Write new annual turnover:");
                            line = reader.readLine();
                            long annualTurnover = Long.parseLong(line);
                            executedOrganization.setAnnualTurnover(annualTurnover);
                            break;
                        case "employees count", "employeescount":
                            System.out.println("Write new employees count:");
                            line = reader.readLine();
                            long employeesCount = Long.parseLong(line);
                            executedOrganization.setAnnualTurnover(employeesCount);
                            break;
                        case "type":
                            System.out.println("Write new type:");
                            line = reader.readLine();
                            executedOrganization.setType(OrganizationType.valueOf(line));
                            break;
                        case "postal address", "postaladdress":
                            System.out.println("\n zipcode:");
                            String zipCode = reader.readLine();
                            System.out.println("\n town:");
                            String townForLocation = reader.readLine();
                            System.out.println("\n x of town (in int format):");
                            int xForAddress = Integer.parseInt(reader.readLine());
                            System.out.println("\n y of town (in float format):");
                            float yForAddress = Float.parseFloat(reader.readLine());
                            Location location = new Location(xForAddress, yForAddress, townForLocation);
                            Address postalAddress = new Address(zipCode,location);
                            executedOrganization.setPostalAddress(postalAddress);
                            break;
                        default:
                            System.out.println("No data found.");
                    }
                    executedMap.replace(foundID,executedOrganization);
                } catch (Exception e)
                {
                    System.out.println("Not right format.");
                }

            } catch (IOException e)
            {
                System.out.println(e);
            }
            History.commandsHistory.add(this);
        }
        else
        {
            System.out.println("Cannot find organization with such ID");
        }
    }

    /**
     * Updating one field in organization that written after ID
     * Field form same as in csv file.
     * Command format: UPDATE [ID] [FIELD_NAME] [DATA]
     * @param command - line with command
     * @param executedMap - executed TreeMap
     */
    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        if (checkForDublicatedID(command, executedMap))
        {
            try
            {
                Set<Map.Entry<Integer, Organization>> entrySet
                        = executedMap.entrySet();

                Map.Entry<Integer, Organization>[] entryArray
                        = entrySet.toArray(new Map.Entry[entrySet.size()]);

                int foundID = Integer.parseInt(command.split(" ")[1]);

                Organization executedOrganization = null;

                for (int i = 0; i < executedMap.size(); i++)
                {
                    if (entryArray[i].getKey() == foundID)
                    {
                        executedOrganization = entryArray[i].getValue();
                    }
                }

                String[] parts = command.split(" ");
                int dataNameIndex = 2;
                int dataIndex = 3;
                String dataNameLine = parts[dataNameIndex];


                switch (dataNameLine.toLowerCase())
                {
                    case "id":
                        int id = Integer.parseInt(parts[dataIndex]);
                        executedOrganization.setID(id);
                        break;
                    case "name":
                        executedOrganization.setName(parts[dataIndex]);
                        break;
                    case "coordinates":
                        long x = Long.parseLong(parts[dataIndex]);
                        double y = Double.parseDouble(parts[dataIndex+1]);
                        executedOrganization.setCoordinates(new Coordinates(x, y));
                        break;
                    case "annualturnover":
                    case "employeescount":
                        long data = Long.parseLong(parts[dataIndex]);
                        executedOrganization.setAnnualTurnover(data);
                        break;
                    case "type":
                        executedOrganization.setType(OrganizationType.valueOf(parts[dataIndex]));
                        break;
                    case "postaladdress":
                        int zipCodeIndex = dataIndex;
                        int townIndex = dataIndex+1;
                        int xIndex = dataIndex+2;
                        int yIndex = dataIndex+3;
                        String zipCode = parts[zipCodeIndex];
                        String townForLocation = parts[townIndex];
                        int xForAddress = Integer.parseInt(parts[xIndex]);
                        float yForAddress = Float.parseFloat(parts[yIndex]);
                        Location location = new Location(xForAddress, yForAddress, townForLocation);
                        Address postalAddress = new Address(zipCode, location);
                        executedOrganization.setPostalAddress(postalAddress);
                        break;
                }
                executedMap.replace(foundID,executedOrganization);
            }catch (Exception e)
            {
                System.out.println("There is a problem with " + this.getClass().getSimpleName() + " in script. No data found.");
            }
        }else
        {
            System.out.println("There is a problem with " + this.getClass().getSimpleName() + " in script. No ID found.");
        }
    }
}
