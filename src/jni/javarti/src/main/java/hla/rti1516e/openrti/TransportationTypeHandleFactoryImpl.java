package hla.rti1516e.openrti;

import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.TransportationTypeHandleFactory;
import hla.rti1516e.exceptions.CouldNotDecode;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;

public class TransportationTypeHandleFactoryImpl implements TransportationTypeHandleFactory
{

    @Override
    public TransportationTypeHandle decode(byte[] buffer, int offset)
            throws CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented yet");
    }

    @Override
    public TransportationTypeHandle getHLAdefaultReliable() throws RTIinternalError
    {
        throw new RTIinternalError("not implemented yet");
    }

    @Override
    public TransportationTypeHandle getHLAdefaultBestEffort() throws RTIinternalError
    {
        throw new RTIinternalError("not implemented yet");
    }

}
