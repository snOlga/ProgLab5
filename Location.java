package OrganizationsPackage;

/**
 * class of organization's town location
 */
public class Location
{
    private Integer x; //Поле не может быть null
    private float y;
    private String name; //Поле не может быть null

    public Location(Integer x, float y, String name)
    {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + " x = " + this.x + " y = " + this.y + " town: " + this.name;
    }

    public Integer getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public String getName()
    {
        return name;
    }
}
