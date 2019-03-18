package hla.rti1516e.openrti;

import hla.rti1516e.RegionHandleSet;
import hla.rti1516e.RegionHandleSetFactory;

public class RegionHandleSetFactoryImpl implements RegionHandleSetFactory
{

    @Override
    public RegionHandleSet create()
    {
        return new RegionHandleSetImpl();
    }

}
