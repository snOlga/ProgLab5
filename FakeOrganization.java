package OrganizationsPackage;

import java.time.ZonedDateTime;

/**
 * class of fake organization
 */
public class FakeOrganization extends Organization
{
    public FakeOrganization(String name, Coordinates coordinates, ZonedDateTime creationDate, long annualTurnover, Long employeesCount, OrganizationType type, Address postalAddress)
    {
        super(name, coordinates, annualTurnover, employeesCount, type, postalAddress);
    }

    public void cannotBeOrganization()
    {
        System.out.println("Some data cannot be organization (one or more fields are null). It will be lost in process.");
    }
}
