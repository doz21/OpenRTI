package hla.rti1516e.openrti;

import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.AttributeHandleValueMapFactory;

public class AttributeHandleValueMapFactoryImpl implements AttributeHandleValueMapFactory
{

    @Override
    public AttributeHandleValueMap create(int capacity)
    {
        return new AttributeHandleValueMapImpl(capacity);
    }

}
