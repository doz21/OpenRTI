package hla.rti1516e.openrti;

import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.ObjectInstanceHandleFactory;
import hla.rti1516e.exceptions.CouldNotDecode;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;

public class ObjectInstanceHandleFactoryImpl extends HandleFactory implements ObjectInstanceHandleFactory
{

    ObjectInstanceHandleFactoryImpl(RTIambassadorImpl rti)
    {
        super(rti);
    }

    @Override
    public ObjectInstanceHandle decode(byte[] buffer, int offset)
            throws CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getRti().decodeObjectInstanceHandle(buffer, offset);
    }
}
