package hla.rti1516e.openrti;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleFactory;
import hla.rti1516e.exceptions.CouldNotDecode;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;

public class AttributeHandleFactoryImpl extends HandleFactory implements AttributeHandleFactory
{

    AttributeHandleFactoryImpl(RTIambassadorImpl rti)
    {
        super(rti);
    }

    @Override
    public AttributeHandle decode(byte[] buffer, int offset)
            throws CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getRti().decodeAttributeHandle(buffer, offset);
    }
}
