package OrganizationsPackage;
import java.time.*;
import java.util.*;
import java.util.Collection;

/**
 * class of organization
 */
public class Organization implements Comparable<Organization>
{
    private static int idHelp = 0;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long annualTurnover; //Значение поля должно быть больше 0
    private Long employeesCount; //Поле не может быть null, Значение поля должно быть больше 0
    private OrganizationType type; //Поле может быть null
    private Address postalAddress; //Поле может быть null

    public Organization(String name, Coordinates coordinates, long annualTurnover, Long employeesCount, OrganizationType type, Address postalAddress)
    {
        setID();
        this.name = name;
        this.coordinates = coordinates;
        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        this.creationDate = ZonedDateTime.now(zoneId);
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.postalAddress = postalAddress;
    }

    private void setID()
    {
        this.idHelp++;
        Collections.sort(canceledValuesOfID);
        for(int canceledNumber:canceledValuesOfID)
        {
            if (idHelp == canceledNumber)
            {
                idHelp++;
            }
        }
        this.id = idHelp;
    }

    public void setID(int ID)
    {
        canceledValuesOfID.add(ID);
        this.id = ID;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }

    public void setAnnualTurnover(long annualTurnover)
    {
        this.annualTurnover = annualTurnover;
    }

    public void setEmployeesCount(Long employeesCount)
    {
        this.employeesCount = employeesCount;
    }

    public void setType(OrganizationType type)
    {
        this.type = type;
    }

    public void setPostalAddress(Address postalAddress)
    {
        this.postalAddress = postalAddress;
    }

    public static ArrayList<Integer> canceledValuesOfID = new ArrayList<>();

    @Override
    public int hashCode()
    {
        return name.hashCode() + id;
    }

    @Override
    public int compareTo(Organization o)
    {
        return this.hashCode()-o.hashCode();
    }

    @Override
    public String toString()
    {
        if (this.type != null && this.postalAddress != null)
        {
            return "[" + this.getClass().getSimpleName() +
                    "\n id: " + this.id +
                    "\n name: " + this.name +
                    "\n coordinates: " + this.coordinates.toString() +
                    "\n creation date: " + this.creationDate.toString() +
                    "\n annual turnover: " + this.annualTurnover +
                    "\n employees count: " + this.employeesCount +
                    "\n type: " + this.type +
                    "\n postal address: " + this.postalAddress.toString() + "]";
        } else if (this.type != null)
        {
            return "[" + this.getClass().getSimpleName() +
                    "\n id: " + this.id +
                    "\n name: " + this.name +
                    "\n coordinates: " + this.coordinates.toString() +
                    "\n creation date: " + this.creationDate.toString() +
                    "\n annual turnover: " + this.annualTurnover +
                    "\n employees count: " + this.employeesCount +
                    "\n type: " + this.type + "]";
        } else if (this.postalAddress != null)
        {
            return "[" + this.getClass().getSimpleName() +
                    "\n id: " + this.id +
                    "\n name: " + this.name +
                    "\n coordinates: " + this.coordinates.toString() +
                    "\n creation date: " + this.creationDate.toString() +
                    "\n annual turnover: " + this.annualTurnover +
                    "\n employees count: " + this.employeesCount +
                    "\n postal address: " + this.postalAddress.toString() + "]";
        } else
        {
            return "[" + this.getClass().getSimpleName() +
                    "\n id: " + this.id +
                    "\n name: " + this.name +
                    "\n coordinates: " + this.coordinates.toString() +
                    "\n creation date: " + this.creationDate.toString() +
                    "\n annual turnover: " + this.annualTurnover +
                    "\n employees count: " + this.employeesCount + "]";
        }
    }

    public int getID()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public Coordinates getCoordinates()
    {
        return coordinates;
    }
    public ZonedDateTime getCreationDate()
    {
        return creationDate;
    }
    public long getAnnualTurnover()
    {
        return annualTurnover;
    }
    public Long getEmployeesCount()
    {
        return employeesCount;
    }
    public OrganizationType getType()
    {
        return type;
    }
    public Address getPostalAddress()
    {
        return postalAddress;
    }
}
