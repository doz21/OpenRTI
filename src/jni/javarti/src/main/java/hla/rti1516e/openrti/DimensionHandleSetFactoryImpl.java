package hla.rti1516e.openrti;

import hla.rti1516e.DimensionHandleSet;
import hla.rti1516e.DimensionHandleSetFactory;

public class DimensionHandleSetFactoryImpl implements DimensionHandleSetFactory
{

    @Override
    public DimensionHandleSet create()
    {
        return new DimensionHandleSetImpl();
    }

}
