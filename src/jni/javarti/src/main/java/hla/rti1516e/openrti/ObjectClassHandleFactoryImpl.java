package hla.rti1516e.openrti;

import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectClassHandleFactory;
import hla.rti1516e.exceptions.CouldNotDecode;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;

public class ObjectClassHandleFactoryImpl extends HandleFactory implements ObjectClassHandleFactory
{

    ObjectClassHandleFactoryImpl(RTIambassadorImpl rti)
    {
        super(rti);
    }

    @Override
    public ObjectClassHandle decode(byte[] buffer, int offset)
            throws CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getRti().decodeObjectClassHandle(buffer, offset);
    }

    private native ObjectClassHandle decode_rti(byte[] buffer, int offset);

}
