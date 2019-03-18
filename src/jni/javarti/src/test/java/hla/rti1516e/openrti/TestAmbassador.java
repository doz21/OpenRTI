package hla.rti1516e.openrti;

import java.util.HashSet;
import java.util.Set;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.LogicalTime;
import hla.rti1516e.LogicalTimeFactoryFactory;
import hla.rti1516e.MessageRetractionHandle;
import hla.rti1516e.NullFederateAmbassador;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandle;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.HLAunicodeString;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.InvalidInteractionClassHandle;
import hla.rti1516e.exceptions.InvalidObjectClassHandle;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectInstanceNotKnown;
import hla.rti1516e.exceptions.RTIexception;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;

public class TestAmbassador extends NullFederateAmbassador
{
    private RTIambassador rtiAmbassador;

    private boolean timeRegulationEnabled = false;
    private boolean timeConstrainedEnabled = false;
    private boolean timeAdvanceGrant = false;

    protected LogicalTime federateTime = LogicalTimeFactoryFactory.getLogicalTimeFactory("HLAfloat64Time")
            .makeInitial();

    protected LogicalTime timeRegulation;
    protected LogicalTime timeConstrained;

    protected Set<ObjectInstanceHandle> objectInstances;
    
    protected String objectName;
    protected String interactionTarget;

    public TestAmbassador(RTIambassador rtiAmbassador)
    {
        super();
        this.rtiAmbassador = rtiAmbassador;
        this.objectInstances = new HashSet<ObjectInstanceHandle>();
    }

    @Override
    public void timeRegulationEnabled(LogicalTime time) throws FederateInternalError
    {
        timeRegulationEnabled = true;
        timeRegulation = time;
    }

    @Override
    public void timeConstrainedEnabled(LogicalTime time) throws FederateInternalError
    {
        timeConstrainedEnabled = true;
        timeConstrained = time;
    }

    // TODO uebrige parameter
    @Override
    public void reflectAttributeValues(ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes,
            byte[] userSuppliedTag, OrderType sentOrdering, TransportationTypeHandle theTransport,
            SupplementalReflectInfo reflectInfo) throws FederateInternalError
    {
        try
        {
            ObjectClassHandle objectClassHandle = rtiAmbassador.getObjectClassHandle("SimLabFederate");
            AttributeHandle attributeHandle = rtiAmbassador.getAttributeHandle(objectClassHandle, "FederateName");

            HLAunicodeString decoder = RtiFactoryFactory.getRtiFactory().getEncoderFactory().createHLAunicodeString();
            decoder.decode(theAttributes.get(attributeHandle));
            objectName = decoder.getValue();
        }
        catch (RTIexception | DecoderException e)
        {
            throw new FederateInternalError(e.getMessage(), e);
        }
    }

    @Override
    public void discoverObjectInstance(ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass,
            String objectName) throws FederateInternalError
    {
        objectInstances.add(theObject);

        ObjectClassHandle objectClassHandle;
        try
        {
            objectClassHandle = rtiAmbassador.getObjectClassHandle("SimLabFederate");
            AttributeHandle attributeHandle = rtiAmbassador.getAttributeHandle(objectClassHandle, "FederateName");
            AttributeHandleSet attrubutes = rtiAmbassador.getAttributeHandleSetFactory().create();
            attrubutes.add(attributeHandle);
            rtiAmbassador.requestAttributeValueUpdate(theObject, attrubutes, null);
        }
        catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | InvalidObjectClassHandle
                | AttributeNotDefined | ObjectInstanceNotKnown | SaveInProgress | RestoreInProgress e)
        {
            throw new FederateInternalError(e.getMessage(), e);
        }
    }

    @Override
    public void discoverObjectInstance(ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass,
            String objectName, FederateHandle producingFederate) throws FederateInternalError
    {
        objectInstances.add(theObject);
    }

    @Override
    public void removeObjectInstance(ObjectInstanceHandle theObject, byte[] userSuppliedTag, OrderType sentOrdering,
            SupplementalRemoveInfo removeInfo) throws FederateInternalError
    {
        objectInstances.remove(theObject);
    }

    @Override
    public void removeObjectInstance(ObjectInstanceHandle theObject, byte[] userSuppliedTag, OrderType sentOrdering,
            LogicalTime theTime, OrderType receivedOrdering, SupplementalRemoveInfo removeInfo)
            throws FederateInternalError
    {
        objectInstances.remove(theObject);
    }

    @Override
    public void removeObjectInstance(ObjectInstanceHandle theObject, byte[] userSuppliedTag, OrderType sentOrdering,
            LogicalTime theTime, OrderType receivedOrdering, MessageRetractionHandle retractionHandle,
            SupplementalRemoveInfo removeInfo) throws FederateInternalError
    {
        objectInstances.remove(theObject);
    }

    @Override
    public void timeAdvanceGrant(LogicalTime theTime) throws FederateInternalError
    {
        timeAdvanceGrant = true;
        federateTime = theTime;
    }

    public boolean isTimeRegulationEnabled()
    {
        return timeRegulationEnabled;
    }

    public boolean isTimeConstrainedEnabled()
    {
        return timeConstrainedEnabled;
    }

    public boolean isTimeAdvanceGrant()
    {
        return timeAdvanceGrant;
    }
    
    private void receiveInteraction(InteractionClassHandle interactionClass, ParameterHandleValueMap theParameters) throws FederateInternalError
    {
        try
        {
            InteractionClassHandle interaction = rtiAmbassador.getInteractionClassHandle("Start");
            ParameterHandle handle = rtiAmbassador.getParameterHandle(interaction, "Target");
            if (interaction.equals(interactionClass))
            {
                HLAunicodeString decoder = RtiFactoryFactory.getRtiFactory().getEncoderFactory().createHLAunicodeString();
                decoder.decode(theParameters.get(handle));
                interactionTarget = decoder.getValue();
            }
        }
        catch (RTIexception | DecoderException e)
        {
            throw new FederateInternalError(e.getMessage(), e);
        }  
    }

    @Override
    public void receiveInteraction(InteractionClassHandle interactionClass, ParameterHandleValueMap theParameters,
            byte[] userSuppliedTag, OrderType sentOrdering, TransportationTypeHandle theTransport,
            SupplementalReceiveInfo receiveInfo) throws FederateInternalError
    {
        receiveInteraction(interactionClass, theParameters);
    }

    @Override
    public void receiveInteraction(InteractionClassHandle interactionClass, ParameterHandleValueMap theParameters,
            byte[] userSuppliedTag, OrderType sentOrdering, TransportationTypeHandle theTransport, LogicalTime theTime,
            OrderType receivedOrdering, SupplementalReceiveInfo receiveInfo) throws FederateInternalError
    {
        receiveInteraction(interactionClass, theParameters);
    }

    @Override
    public void receiveInteraction(InteractionClassHandle interactionClass, ParameterHandleValueMap theParameters,
            byte[] userSuppliedTag, OrderType sentOrdering, TransportationTypeHandle theTransport, LogicalTime theTime,
            OrderType receivedOrdering, MessageRetractionHandle retractionHandle, SupplementalReceiveInfo receiveInfo)
            throws FederateInternalError
    {
        receiveInteraction(interactionClass, theParameters);
    }

}
