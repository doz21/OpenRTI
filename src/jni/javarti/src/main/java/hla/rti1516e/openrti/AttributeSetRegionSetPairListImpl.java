package hla.rti1516e.openrti;

import java.util.ArrayList;

import hla.rti1516e.AttributeRegionAssociation;
import hla.rti1516e.AttributeSetRegionSetPairList;

public class AttributeSetRegionSetPairListImpl extends ArrayList<AttributeRegionAssociation> implements AttributeSetRegionSetPairList
{

    public AttributeSetRegionSetPairListImpl(int capacity)
    {
        super(capacity);
    }
    
}
