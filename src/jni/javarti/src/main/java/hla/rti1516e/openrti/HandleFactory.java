package hla.rti1516e.openrti;

public class HandleFactory
{
    private final RTIambassadorImpl rti;

    public HandleFactory(RTIambassadorImpl rti)
    {
        this.rti = rti;
    }

    public RTIambassadorImpl getRti()
    {
        return rti;
    }

}
