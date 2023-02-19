package OrganizationsPackage;

/**
 * class of organization's coordinates
 */
public class Coordinates
{
    private long x;
    private Double y; //Поле не может быть null

    public Coordinates (long x, Double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + " x = " + this.x + " y = " + this.y;
    }

    public long getX()
    {
        return x;
    }

    public Double getY()
    {
        return y;
    }
}
