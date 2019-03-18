package hla.rti1516e.openrti;

import hla.rti1516e.ParameterHandle;
import hla.rti1516e.ParameterHandleFactory;
import hla.rti1516e.exceptions.CouldNotDecode;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;

public class ParameterHandleFactoryImpl extends HandleFactory implements ParameterHandleFactory
{
    ParameterHandleFactoryImpl(RTIambassadorImpl rti)
    {
        super(rti);
    }

    @Override
    public ParameterHandle decode(byte[] buffer, int offset)
            throws CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getRti().decodeParameterHandle(buffer, offset);
    }
}
