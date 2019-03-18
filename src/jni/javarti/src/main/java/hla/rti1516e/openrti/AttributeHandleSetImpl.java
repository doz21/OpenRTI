package hla.rti1516e.openrti;

import java.util.HashSet;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;

public class AttributeHandleSetImpl extends HashSet<AttributeHandle> implements AttributeHandleSet
{

    @Override
    public AttributeHandleSet clone()
    {
        AttributeHandleSetImpl res = new AttributeHandleSetImpl();
        res.addAll(this);
        return res;
    }

}
