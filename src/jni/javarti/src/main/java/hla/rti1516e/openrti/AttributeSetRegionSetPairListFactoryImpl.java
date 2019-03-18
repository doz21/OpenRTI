package hla.rti1516e.openrti;

import hla.rti1516e.AttributeSetRegionSetPairList;
import hla.rti1516e.AttributeSetRegionSetPairListFactory;

public class AttributeSetRegionSetPairListFactoryImpl implements AttributeSetRegionSetPairListFactory
{

    @Override
    public AttributeSetRegionSetPairList create(int capacity)
    {
        return new AttributeSetRegionSetPairListImpl(capacity);
    }

}
