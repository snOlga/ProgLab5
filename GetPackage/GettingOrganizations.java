package GetPackage;

import OrganizationsPackage.*;

import java.io.*;
import java.time.*;
import java.util.*;

/**
 * class GettingCommand - getting organization from any ways
 */
public class GettingOrganizations
{
    public static TreeMap<Integer, Organization> organizationMap = new TreeMap<Integer, Organization>();

    /**
     * Getting organization from csv file
     * <table border="1">
     *     <caption>Structure of line</caption>
     *   <tr>
     *     <td>ID;</td><td>name</td><td>xCoordinates,yCoordinates;</td><td>creationDate;</td><td>annualTurnover;</td><td>employeesCount;</td><td>type;</td><td>zipCode,xLocation yLocation townName</td>
     *   </tr>
     *   <tr>
     *     <td>int</td><td>String</td><td>int, double</td><td>ZonedDateTime</td><td>int</td><td>int</td><td>OrganizationType</td><td>String,int double String</td>
     *   </tr>
     * </table>
     */
    public void getFromFile (String fileName) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

        String firstLine = bufferedReader.readLine();

        firstLine = firstLine.toLowerCase();

        String[] columnNames = firstLine.split(";");

        int idColumn = 0;
        int nameColumn = 0;
        int coordinatesColumn = 0;
        int creationDateColumn = 0;
        int annualTurnoverColumn = 0;
        int employeesCountColumn = 0;
        int typeColumn = 0;
        int postalAddressColumn = 0;

        for(int i = 0; i < columnNames.length; i++)
        {
            switch (columnNames[i])
            {
                case "id":
                    idColumn = i;
                    break;
                case "name":
                    nameColumn = i;
                    break;
                case "coordinates":
                    coordinatesColumn = i;
                    break;
                case "creationdate":
                    creationDateColumn = i;
                    break;
                case "annualturnover":
                    annualTurnoverColumn = i;
                    break;
                case "employeescount":
                    employeesCountColumn = i;
                    break;
                case "type":
                    typeColumn = i;
                    break;
                case "postaladdress":
                    postalAddressColumn = i;
                    break;
            }
        }

        String line;

        while ((line = bufferedReader.readLine()) != null)
        {
            Integer id = null;
            String name = null;
            Coordinates coordinates = null;
            long xForCoordinates = 0;
            Double yForCoordinates = null;
            ZonedDateTime creationDate = null;
            long annualTurnover = 0;
            Long employeesCount = null;
            OrganizationType type = null;
            Location town = null;
            Address postalAddress = null;
            Integer xForAddress = null;
            float yForAddress = 0;
            String townForLocation = null;

            String[] dataFromLine = line.split(";");

            for (int i = 0; i < dataFromLine.length; i++)
            {
                if (dataFromLine[i].equals(""))
                {

                } else
                {
                    if (i == idColumn)
                    {
                        id = Integer.parseInt(dataFromLine[i]);
                    } else if (i == nameColumn)
                    {
                        name = dataFromLine[i];
                    } else if (i == coordinatesColumn)
                    {
                        String[] coordinatesLine = dataFromLine[i].split(",");
                        int firstNumber = 0;
                        int secondNumber = 1;
                        if (coordinatesLine[firstNumber].contains("."))
                        {
                            xForCoordinates = Long.parseLong(coordinatesLine[secondNumber]);
                            yForCoordinates = Double.parseDouble(coordinatesLine[firstNumber]);
                        } else if (coordinatesLine[secondNumber].contains("."))
                        {
                            xForCoordinates = Long.parseLong(coordinatesLine[firstNumber]);
                            yForCoordinates = Double.parseDouble(coordinatesLine[secondNumber]);
                        }
                        coordinates = new Coordinates(xForCoordinates, yForCoordinates);
                    } else if (i == creationDateColumn)
                    {
                        creationDate = ZonedDateTime.parse(dataFromLine[i]);
                    } else if (i == annualTurnoverColumn)
                    {
                        annualTurnover = Long.parseLong(dataFromLine[i]);
                    } else if (i == employeesCountColumn)
                    {
                        employeesCount = Long.parseLong(dataFromLine[i]);
                    } else if (i == typeColumn)
                    {
                        for (OrganizationType orgType : OrganizationType.values())
                        {
                            if (orgType.toString().equals(dataFromLine[i].toUpperCase()))
                            {
                                type = orgType;
                            }
                        }
                    } else if (i == postalAddressColumn)
                    {
                        String[] addressLine = dataFromLine[i].split(",");
                        int indexOfPostalAddress = 0;
                        int indexOfLocation = 1;
                        String[] dataForLocation = addressLine[indexOfLocation].split(" ");

                        int firstNumber = 0;
                        int secondNumber = 1;
                        int thirdNumber = 2;
                        townForLocation = dataForLocation[thirdNumber];
                        if (dataForLocation[firstNumber].contains("."))
                        {
                            xForAddress = Integer.parseInt(dataForLocation[secondNumber]);
                            yForAddress = Float.parseFloat(dataForLocation[firstNumber]);
                        } else if (dataForLocation[secondNumber].contains("."))
                        {
                            xForAddress = Integer.parseInt(dataForLocation[firstNumber]);
                            yForAddress = Float.parseFloat(dataForLocation[secondNumber]);
                        }
                        Location location = new Location(xForAddress, yForAddress, townForLocation);
                        postalAddress = new Address(addressLine[indexOfPostalAddress], location);
                    }
                }
            }
            if (id != null && name != null && coordinates != null && creationDate != null && annualTurnover != 0 && employeesCount != null)
            {
                Organization org = new Organization(name, coordinates, annualTurnover, employeesCount, type, postalAddress);
                organizationMap.put(org.getID(), org);
            } else
            {
                FakeOrganization fakeOrganization = new FakeOrganization(name, coordinates, creationDate, annualTurnover, employeesCount, type, postalAddress);
                fakeOrganization.cannotBeOrganization();
            }
        }
    }

    public String getOrganizationFileName() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Write file name:");

        String fileName = reader.readLine();

        try
        {
            FileReader fileReader = new FileReader(fileName);
            return fileName;
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("File was not found. Try again");
            return this.getOrganizationFileName();
        }
    }

    /**
     * Getting organization from console
     * @return Organization
     */
    public Organization getOrganizationFromConsole()
    {
        String name = null;
        Coordinates coordinates = null;
        long xForCoordinates = 0;
        Double yForCoordinates = null;
        ZonedDateTime creationDate = null;
        long annualTurnover = 0;
        Long employeesCount = null;
        OrganizationType type = null;
        Location town = null;
        Address postalAddress = null;
        Integer xForAddress = null;
        float yForAddress = 0;
        String zipCode = null;
        String townForLocation= null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            System.out.println("Please, write an organization: \n name:");
            name = reader.readLine();
            try
            {
                System.out.println("\n Coordinates: \n x(in long format):");
                xForCoordinates = Long.parseLong(reader.readLine());
                System.out.println("\n y(in double format):");
                yForCoordinates = Double.parseDouble(reader.readLine());
                coordinates = new Coordinates(xForCoordinates, yForCoordinates);
//                System.out.println("\n creation date(in format yyyy-mm-ddT00:00:00+00:00[Time/Zone]):");
//                creationDate = ZonedDateTime.parse(reader.readLine());
                System.out.println("\n annual turnover(in long format):");
                annualTurnover = Long.parseLong(reader.readLine());
                System.out.println("\n employees count(in long format):");
                employeesCount = Long.parseLong(reader.readLine());
                System.out.println("\n does organization have type? (type 'y' for yes, 'n' for no)?");
                String setTypes = reader.readLine();
                if (setTypes.toLowerCase().contains("y"))
                {
                    System.out.println("\n organization type. Show all types (type 'y' for yes, 'n' for no)?");
                    String showAllTypes = reader.readLine();
                    if (showAllTypes.toLowerCase().contains("y"))
                    {
                        for(OrganizationType organizationType : OrganizationType.values())
                        {
                            System.out.println(organizationType);
                        }
                    }
                    System.out.println("\n organization type:");
                    String getType = reader.readLine();
                    for (OrganizationType orgType : OrganizationType.values())
                    {
                        if (orgType.toString().equals(getType.toUpperCase()))
                        {
                            type = orgType;
                        }
                    }
                }
                System.out.println("\n does organization have postal address? (type 'y' for yes, 'n' for no)?");
                String setPostalAddress = reader.readLine();
                if (setPostalAddress.toLowerCase().contains("y"))
                {
                    System.out.println("\n postal address. Zipcode:");
                    zipCode = reader.readLine();
                    System.out.println("\n town:");
                    townForLocation = reader.readLine();
                    System.out.println("\n x of town (in int format):");
                    xForAddress = Integer.parseInt(reader.readLine());
                    System.out.println("\n y of town (in float format):");
                    yForAddress = Float.parseFloat(reader.readLine());
                    Location location = new Location(xForAddress, yForAddress, townForLocation);
                    postalAddress = new Address(zipCode,location);
                }
                return new Organization(name,coordinates,annualTurnover,employeesCount,type,postalAddress);
            } catch (RuntimeException e)
            {
                System.out.println("Not right format.");
                return null;
            }
        } catch (IOException e)
        {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Getting organization from script line
     * <table border="1">
     *     <caption>Structure of line</caption>
     * <tr>
     * <td>ID;</td><td>name</td><td>xCoordinates,yCoordinates;</td><td>creationDate;</td><td>annualTurnover;</td><td>employeesCount;</td><td>type;</td><td>zipCode,xLocation yLocation townName</td>
     * </tr>
     * <tr>
     * <td>int</td><td>String</td><td>int,double</td><td>ZonedDateTime</td><td>int</td><td>int</td><td>OrganizationType</td><td>String,int double String</td>
     * </tr>
     * </table>
     * @param line - line with organization data inside
     * @return organization
     */
    public Organization getOrganizationFromScript (String line) throws IndexOutOfBoundsException
    {
        String[] parts = line.split(" ");
        int lengthWithData = 5;
        int organizationDataIndex = 2;
        if (parts.length == lengthWithData)
        {
            parts[organizationDataIndex] = parts[organizationDataIndex] + " " + parts[organizationDataIndex+1] + " " + parts[organizationDataIndex+2];
        }
        String organizationDataLine = parts[organizationDataIndex];

        String[] organizationData = organizationDataLine.split(";");

        String name = organizationData[0];

        String[] coordinatesLine = organizationData[1].split(",");

        long xForCoordinates = Long.parseLong(coordinatesLine[0]);
        Double yForCoordinates = Double.parseDouble(coordinatesLine[1]);
        Coordinates coordinates = new Coordinates(xForCoordinates, yForCoordinates);

        long annualTurnover = Long.parseLong(organizationData[2]);
        Long employeesCount = Long.parseLong(organizationData[3]);

        OrganizationType type = null;

        if (organizationData[4].equals(""))
        {

        }else
        {
            type = OrganizationType.valueOf(organizationData[4]);
        }

        Address postalAddress = null;

        try
        {
            String[] lineForAddres = organizationData[5].split(",");

            String zipCode = lineForAddres[0];

            String[] lineForLocation = lineForAddres[1].split(" ");

            Integer xForAddress = Integer.parseInt(lineForLocation[0]);
            float yForAddress = Float.parseFloat(lineForLocation[1]);
            String townForLocation = lineForLocation[2];
            Location town = new Location(xForAddress, yForAddress, townForLocation);
            postalAddress = new Address(zipCode, town);
        }catch (IndexOutOfBoundsException e)
        {
            postalAddress = null;
        }

        return new Organization(name, coordinates, annualTurnover, employeesCount, type, postalAddress);
    }
}
