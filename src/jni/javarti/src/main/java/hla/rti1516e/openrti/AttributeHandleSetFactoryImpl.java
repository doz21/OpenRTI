package hla.rti1516e.openrti;

import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleSetFactory;

public class AttributeHandleSetFactoryImpl implements AttributeHandleSetFactory
{

    @Override
    public AttributeHandleSet create()
    {
        return new AttributeHandleSetImpl();
    }

}
