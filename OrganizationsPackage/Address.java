package OrganizationsPackage;

/**
 * class of organization's town address
 */
public class Address
{
    private String zipCode; //Поле не может быть null
    private Location town; //Поле не может быть null

    public Address (String zipCode, Location town)
    {
        this.zipCode = zipCode;
        this.town = town;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + " zipcode: " + zipCode + " location: " + this.town.toString();
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public Location getTown()
    {
        return town;
    }
}
