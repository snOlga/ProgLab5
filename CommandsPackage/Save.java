package CommandsPackage;

import GetPackage.GettingOrganizations;
import OrganizationsPackage.Address;
import OrganizationsPackage.Coordinates;
import OrganizationsPackage.Location;
import OrganizationsPackage.Organization;

import java.io.*;
import java.util.*;

/**
 * Command class SAVE - saving collection to csv file, working with TreeMap, csv format: between columns is ; between columns in columns is , third columns are separated spaces
 */
public class Save extends Command
{
    @Override
    public void getHelp()
    {
        System.out.println(this.getClass().getSimpleName() + " - saving collection to csv file.");
    }

    @Override
    public void execute(String command, TreeMap<Integer, Organization> executedMap)
    {
        try
        {
            GettingOrganizations list = new GettingOrganizations();
            BufferedWriter writter = new BufferedWriter(new FileWriter("organizations2.csv"));

            String org = executedMap.toString();


            Set<Map.Entry<Integer, Organization> > entrySet
                    = executedMap.entrySet();

            Map.Entry<Integer, Organization>[] entryArray
                    = entrySet.toArray(new Map.Entry[entrySet.size()]);

            boolean checkForFirstString = true;

            for (int i = 0; i < executedMap.size(); i++)
            {
                if (checkForFirstString == true)
                {
                    //writter.write(entryArray[i].getValue().getClass().getFields().toString());
                    writter.write("id;name;coordinates;creationDate;annualTurnover;employeesCount;type;postalAddress");
                    checkForFirstString = false;
                }
                writter.write(saveOrganizationCSV(entryArray[i].getValue()));

//                System.out.println("Key at " + i + ":"
//                        + entryArray[i].getKey());
//
//                System.out.println("Value at " + i + ":"
//                        + entryArray[i].getValue());
            }

            writter.close();
            History.commandsHistory.add(this);
        } catch (IOException e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void executeFromScript(String command, TreeMap<Integer, Organization> executedMap)
    {
        execute(command,executedMap);
    }

    private String saveOrganizationCSV (Organization organization)
    {
        if (organization.getType() != null && organization.getPostalAddress() != null)
        {
            return "\n" +
                    organization.getID() + ";" +
                    organization.getName() + ";" +
                    this.getCoordinatesForSavingCSV(organization.getCoordinates()) + ";" +
                    organization.getCreationDate() + ";" +
                    organization.getAnnualTurnover() + ";" +
                    organization.getEmployeesCount() + ";" +
                    organization.getType() + ";" +
                    this.getPostalAddressForSavingCSV(organization.getPostalAddress());
        } else if (organization.getType() != null)
        {
            return "\n" +
                    organization.getID() + ";" +
                    organization.getName() + ";" +
                    this.getCoordinatesForSavingCSV(organization.getCoordinates()) + ";" +
                    organization.getCreationDate() + ";" +
                    organization.getAnnualTurnover() + ";" +
                    organization.getEmployeesCount() + ";" +
                    organization.getType() + ";";
        }else if (organization.getPostalAddress() != null)
        {
            return "\n" +
                    organization.getID() + ";" +
                    organization.getName() + ";" +
                    this.getCoordinatesForSavingCSV(organization.getCoordinates()) + ";" +
                    organization.getCreationDate() + ";" +
                    organization.getAnnualTurnover() + ";" +
                    organization.getEmployeesCount() + ";;" +
                    this.getPostalAddressForSavingCSV(organization.getPostalAddress());
        }
        else
        {
            return "\n" +
                    organization.getID() + ";" +
                    organization.getName() + ";" +
                    this.getCoordinatesForSavingCSV(organization.getCoordinates()) + ";" +
                    organization.getCreationDate() + ";" +
                    organization.getAnnualTurnover() + ";" +
                    organization.getEmployeesCount() + ";;";
        }
    }

    private String getCoordinatesForSavingCSV(Coordinates coordinates)
    {
        return coordinates.getX() + "," + coordinates.getY();
    }
    private String getPostalAddressForSavingCSV(Address address)
    {
        return address.getZipCode() + "," + this.getLocationForSavingCSV(address.getTown());
    }
    private String getLocationForSavingCSV(Location location)
    {
        return location.getX() + " " + location.getY() + " " + location.getName();
    }
}
