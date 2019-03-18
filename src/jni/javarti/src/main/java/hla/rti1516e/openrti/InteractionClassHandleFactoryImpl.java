package hla.rti1516e.openrti;

import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.InteractionClassHandleFactory;
import hla.rti1516e.exceptions.CouldNotDecode;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;

public class InteractionClassHandleFactoryImpl implements InteractionClassHandleFactory
{

    @Override
    public InteractionClassHandle decode(byte[] buffer, int offset)
            throws CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented yet");
    }

}
