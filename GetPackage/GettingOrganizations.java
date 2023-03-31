package GetPackage;

import CommandsPackage.Exit;
import OrganizationsPackage.*;

import java.io.*;
import java.time.*;
import java.util.*;

/**
 * class GettingCommand - getting organization from any ways
 */
public class GettingOrganizations implements GetStringFromConsole
{
    public static TreeMap<Integer, Organization> organizationMap = new TreeMap<Integer, Organization>();

    public static String bufferedFileName = "organization3.csv";

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

            //String[] dataFromLine = line.split(";");
            ArrayList<String> dataFromLineList = new ArrayList<>();
            dataFromLineList.add(line.split(";")[0]);
            dataFromLineList.add(line.split("\"")[1]);
            dataFromLineList.addAll(List.of(line.split("\"")[2].split(";")));
            dataFromLineList.remove(2);
            String [] dataFromLine = new String[dataFromLineList.size()];
            for (int i = 0; i < dataFromLine.length; i++)
            {
                dataFromLine[i] = dataFromLineList.get(i);
            }

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
                        name = line.split("\"")[1];

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

                        if(addressLine.length > thirdNumber)
                        {
                            String[] addresLineTown = dataForLocation;
                            addresLineTown[firstNumber] = "";
                            addresLineTown[secondNumber] = "";
                            townForLocation = dataForLocation.toString();
                            char falseChar = ']';
                            char falseChar2 = ',';
                            for (char letter : townForLocation.toCharArray())
                            {
                                if (letter == falseChar || letter == falseChar2)
                                {
                                    townForLocation.replace(String.valueOf(letter), "");
                                }
                            }
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
        System.out.println("Write file name:");

        String fileName = GetStringFromConsole.getNotNullString();

            try
            {
                FileReader fileReader = new FileReader(fileName);
//            if (fileName == null)
//            {
//                return this.getOrganizationFileName();
//            } else
//            {
                return fileName;
//            }
            } catch (FileNotFoundException exception)
            {
                System.out.println("File was not found. Try again");
                return this.getOrganizationFileName();
            }
//        catch (NullPointerException exception1)
//        {
//            System.out.println("File was not found. Try again");
//            return this.getOrganizationFileName();
//        }

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

//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            System.out.println("Please, write an organization: \n name:");
            name = getName();
//            try
//            {
                System.out.println("\n Coordinates:");
                xForCoordinates = getXForCoordinates();
                yForCoordinates = getYForCoordinates();
                coordinates = new Coordinates(xForCoordinates, yForCoordinates);
//                System.out.println("\n creation date(in format yyyy-mm-ddT00:00:00+00:00[Time/Zone]):");
//                creationDate = ZonedDateTime.parse(reader.readLine());
                System.out.println("\n annual turnover(in long format):");
                annualTurnover = getAnnualTurnover();
                System.out.println("\n employees count(in long format):");
                employeesCount = getEmployeesCount();
                System.out.println("\n does organization have type? (type 'y' for yes, 'n' for no)?");
                String setTypes = GetStringFromConsole.getNotNullString();
                if (setTypes.toLowerCase().contains("y"))
                {
                    System.out.println("\n organization type. Show all types (type 'y' for yes, 'n' for no)?");
                    String showAllTypes = GetStringFromConsole.getNotNullString();
                    if (showAllTypes.toLowerCase().contains("y"))
                    {
                        for(OrganizationType organizationType : OrganizationType.values())
                        {
                            System.out.println(organizationType);
                        }
                    }
                    System.out.println("\n organization type:");
                    type = getOrganizationType();
//                    for (OrganizationType orgType : OrganizationType.values())
//                    {
//                        if (orgType.toString().equals(getType.toUpperCase()))
//                        {
//                            type = orgType;
//                        }
//                    }
                }
                System.out.println("\n does organization have postal address? (type 'y' for yes, 'n' for no)?");
                String setPostalAddress = GetStringFromConsole.getNotNullString();
                if (setPostalAddress.toLowerCase().contains("y"))
                {
                    postalAddress = getPostalAddress();
                }
                System.out.println("Finished getting organization.");
                return new Organization(name,coordinates,annualTurnover,employeesCount,type,postalAddress);
//            } catch (RuntimeException e)
//            {
//                System.out.println("Not right format.");
//                return null;
//            }
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

        String name = organizationDataLine.split("\"")[1];

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

    public static String getName()
    {
        try
        {
            return GetStringFromConsole.getNotNullString();
        }
        catch (IOException e)
        {
            System.out.println("\n Not right input. Try again");
            String line = getName();
            return line;
        }
    }

    public static Coordinates getCoordinates()
    {
        System.out.println("\n Coordinates:");
        long x = getXForCoordinates();
        Double y = getYForCoordinates();
        return new Coordinates(x,y);
    }

    public static long getXForCoordinates()
    {
        System.out.println("\n x(in long format):");
        try
        {
            return Long.parseLong(GetStringFromConsole.getNotNullString());
        }
        catch (Exception e)
        {
            System.out.println("\n Not right input. Try again");
            long line = getXForCoordinates();
            return line;
        }
    }

    public static Double getYForCoordinates()
    {
        System.out.println("\n y(in double format):");
        try
        {
            return Double.parseDouble(GetStringFromConsole.getNotNullString());
        }
        catch (Exception e)
        {
            System.out.println("\n Not right input. Try again");
            Double line = getYForCoordinates();
            return line;
        }
    }

    public static long getAnnualTurnover()
    {
        try
        {
            return Long.parseLong(GetStringFromConsole.getNotNullString());
        }
        catch (Exception e)
        {
            System.out.println("\n Not right input. Try again");
            long line = getAnnualTurnover();
            return line;
        }
    }

    public static Long getEmployeesCount()
    {
        try
        {
            return Long.parseLong(GetStringFromConsole.getNotNullString());
        }
        catch (Exception e)
        {
            System.out.println("\n Not right input. Try again");
            Long line = getEmployeesCount();
            return line;
        }
    }

    public static OrganizationType getOrganizationType()
    {
        try
        {
            return OrganizationType.valueOf(GetStringFromConsole.getNotNullString());
        }
        catch (IOException e)
        {
            System.out.println("\n Not right input. Try again");
            OrganizationType line = getOrganizationType();
            return line;
        }
    }

    public static Address getPostalAddress()
    {
        System.out.println("\n Postal address. Zipcode:");
        String zipCode = getName(); //postalAddress returns string
        return new Address(zipCode,getLocation());
    }

    public static Location getLocation()
    {

        Integer x = getXForLocation();
        float y = getYForLocation();
        System.out.println("\n town:");
        String name = getName();
        return new Location(x,y,name);
    }

    public static Integer getXForLocation()
    {
        System.out.println("\n y of town (in float format):");
        try
        {
            return Integer.parseInt(GetStringFromConsole.getNotNullString());
        }
        catch (Exception e)
        {
            System.out.println("\n Not right input. Try again");
            Integer line = getXForLocation();
            return line;
        }
    }

    public static float getYForLocation()
    {
        System.out.println("\n x of town (in int format):");
        try
        {
            return Float.parseFloat(GetStringFromConsole.getNotNullString());
        }
        catch (Exception e)
        {
            System.out.println("\n Not right input. Try again");
            float line = getYForLocation();
            return line;
        }
    }
}
