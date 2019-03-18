package hla.rti1516e.openrti;

import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.ParameterHandleValueMapFactory;

public class ParameterHandleValueMapFactoryImpl implements ParameterHandleValueMapFactory
{

    @Override
    public ParameterHandleValueMap create(int capacity)
    {
        return new ParameterHandleValueMapImpl(capacity);
    }

}
