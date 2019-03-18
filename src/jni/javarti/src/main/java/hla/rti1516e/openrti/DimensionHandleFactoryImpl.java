package hla.rti1516e.openrti;

import hla.rti1516e.DimensionHandle;
import hla.rti1516e.DimensionHandleFactory;
import hla.rti1516e.exceptions.CouldNotDecode;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;

public class DimensionHandleFactoryImpl implements DimensionHandleFactory
{

    @Override
    public DimensionHandle decode(byte[] buffer, int offset)
            throws CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented yet");
    }

}
