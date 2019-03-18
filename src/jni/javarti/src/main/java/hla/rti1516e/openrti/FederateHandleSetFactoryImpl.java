package hla.rti1516e.openrti;

import hla.rti1516e.FederateHandleSet;
import hla.rti1516e.FederateHandleSetFactory;

public class FederateHandleSetFactoryImpl implements FederateHandleSetFactory
{

    @Override
    public FederateHandleSet create()
    {
        return new FederateHandleSetImpl();
    }

}
