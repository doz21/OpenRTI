package hla.rti1516e.openrti;

import hla.rti1516e.FederateAmbassador.SupplementalRemoveInfo;
import hla.rti1516e.FederateHandle;

public class SupplementalRemoveInfoImpl implements SupplementalRemoveInfo
{
    private final FederateHandle producer;
    
    public SupplementalRemoveInfoImpl(FederateHandle producer)
    {
        this.producer = producer;
    }

    @Override
    public boolean hasProducingFederate()
    {
        return producer != null;
    }

    @Override
    public FederateHandle getProducingFederate()
    {
        return producer;
    }

}
