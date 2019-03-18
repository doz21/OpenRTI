package hla.rti1516e.openrti;

import hla.rti1516e.FederateHandle;
import hla.rti1516e.FederateHandleFactory;
import hla.rti1516e.exceptions.CouldNotDecode;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.RTIinternalError;

public class FederateHandleFactoryImpl extends HandleFactory implements FederateHandleFactory
{

    FederateHandleFactoryImpl(RTIambassadorImpl rti)
    {
        super(rti);
    }

    @Override
    public FederateHandle decode(byte[] buffer, int offset)
            throws CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getRti().decodeFederateHandle(buffer, offset);
    }

}
