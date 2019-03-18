package hla.rti1516e.openrti;

import java.net.URL;
import java.util.Set;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleFactory;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleSetFactory;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.AttributeHandleValueMapFactory;
import hla.rti1516e.AttributeSetRegionSetPairList;
import hla.rti1516e.AttributeSetRegionSetPairListFactory;
import hla.rti1516e.CallbackModel;
import hla.rti1516e.DimensionHandle;
import hla.rti1516e.DimensionHandleFactory;
import hla.rti1516e.DimensionHandleSet;
import hla.rti1516e.DimensionHandleSetFactory;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.FederateHandleFactory;
import hla.rti1516e.FederateHandleSet;
import hla.rti1516e.FederateHandleSetFactory;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.InteractionClassHandleFactory;
import hla.rti1516e.LogicalTime;
import hla.rti1516e.LogicalTimeFactory;
import hla.rti1516e.LogicalTimeInterval;
import hla.rti1516e.MessageRetractionHandle;
import hla.rti1516e.MessageRetractionReturn;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectClassHandleFactory;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.ObjectInstanceHandleFactory;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandle;
import hla.rti1516e.ParameterHandleFactory;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.ParameterHandleValueMapFactory;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.RangeBounds;
import hla.rti1516e.RegionHandle;
import hla.rti1516e.RegionHandleSet;
import hla.rti1516e.RegionHandleSetFactory;
import hla.rti1516e.ResignAction;
import hla.rti1516e.ServiceGroup;
import hla.rti1516e.TimeQueryReturn;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.TransportationTypeHandleFactory;
import hla.rti1516e.exceptions.AlreadyConnected;
import hla.rti1516e.exceptions.AsynchronousDeliveryAlreadyDisabled;
import hla.rti1516e.exceptions.AsynchronousDeliveryAlreadyEnabled;
import hla.rti1516e.exceptions.AttributeAcquisitionWasNotRequested;
import hla.rti1516e.exceptions.AttributeAlreadyBeingAcquired;
import hla.rti1516e.exceptions.AttributeAlreadyBeingChanged;
import hla.rti1516e.exceptions.AttributeAlreadyBeingDivested;
import hla.rti1516e.exceptions.AttributeAlreadyOwned;
import hla.rti1516e.exceptions.AttributeDivestitureWasNotRequested;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.AttributeNotOwned;
import hla.rti1516e.exceptions.AttributeNotPublished;
import hla.rti1516e.exceptions.AttributeRelevanceAdvisorySwitchIsOff;
import hla.rti1516e.exceptions.AttributeRelevanceAdvisorySwitchIsOn;
import hla.rti1516e.exceptions.AttributeScopeAdvisorySwitchIsOff;
import hla.rti1516e.exceptions.AttributeScopeAdvisorySwitchIsOn;
import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.ConnectionFailed;
import hla.rti1516e.exceptions.CouldNotCreateLogicalTimeFactory;
import hla.rti1516e.exceptions.CouldNotOpenFDD;
import hla.rti1516e.exceptions.CouldNotOpenMIM;
import hla.rti1516e.exceptions.DeletePrivilegeNotHeld;
import hla.rti1516e.exceptions.DesignatorIsHLAstandardMIM;
import hla.rti1516e.exceptions.ErrorReadingFDD;
import hla.rti1516e.exceptions.ErrorReadingMIM;
import hla.rti1516e.exceptions.FederateAlreadyExecutionMember;
import hla.rti1516e.exceptions.FederateHandleNotKnown;
import hla.rti1516e.exceptions.FederateHasNotBegunSave;
import hla.rti1516e.exceptions.FederateIsExecutionMember;
import hla.rti1516e.exceptions.FederateNameAlreadyInUse;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.FederateOwnsAttributes;
import hla.rti1516e.exceptions.FederateServiceInvocationsAreBeingReportedViaMOM;
import hla.rti1516e.exceptions.FederateUnableToUseTime;
import hla.rti1516e.exceptions.FederatesCurrentlyJoined;
import hla.rti1516e.exceptions.FederationExecutionAlreadyExists;
import hla.rti1516e.exceptions.FederationExecutionDoesNotExist;
import hla.rti1516e.exceptions.IllegalName;
import hla.rti1516e.exceptions.InTimeAdvancingState;
import hla.rti1516e.exceptions.InconsistentFDD;
import hla.rti1516e.exceptions.InteractionClassAlreadyBeingChanged;
import hla.rti1516e.exceptions.InteractionClassNotDefined;
import hla.rti1516e.exceptions.InteractionClassNotPublished;
import hla.rti1516e.exceptions.InteractionParameterNotDefined;
import hla.rti1516e.exceptions.InteractionRelevanceAdvisorySwitchIsOff;
import hla.rti1516e.exceptions.InteractionRelevanceAdvisorySwitchIsOn;
import hla.rti1516e.exceptions.InvalidAttributeHandle;
import hla.rti1516e.exceptions.InvalidDimensionHandle;
import hla.rti1516e.exceptions.InvalidFederateHandle;
import hla.rti1516e.exceptions.InvalidInteractionClassHandle;
import hla.rti1516e.exceptions.InvalidLocalSettingsDesignator;
import hla.rti1516e.exceptions.InvalidLogicalTime;
import hla.rti1516e.exceptions.InvalidLookahead;
import hla.rti1516e.exceptions.InvalidMessageRetractionHandle;
import hla.rti1516e.exceptions.InvalidObjectClassHandle;
import hla.rti1516e.exceptions.InvalidOrderName;
import hla.rti1516e.exceptions.InvalidOrderType;
import hla.rti1516e.exceptions.InvalidParameterHandle;
import hla.rti1516e.exceptions.InvalidRangeBound;
import hla.rti1516e.exceptions.InvalidRegion;
import hla.rti1516e.exceptions.InvalidRegionContext;
import hla.rti1516e.exceptions.InvalidResignAction;
import hla.rti1516e.exceptions.InvalidServiceGroup;
import hla.rti1516e.exceptions.InvalidTransportationName;
import hla.rti1516e.exceptions.InvalidTransportationType;
import hla.rti1516e.exceptions.InvalidUpdateRateDesignator;
import hla.rti1516e.exceptions.LogicalTimeAlreadyPassed;
import hla.rti1516e.exceptions.MessageCanNoLongerBeRetracted;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NameSetWasEmpty;
import hla.rti1516e.exceptions.NoAcquisitionPending;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectClassNotDefined;
import hla.rti1516e.exceptions.ObjectClassNotPublished;
import hla.rti1516e.exceptions.ObjectClassRelevanceAdvisorySwitchIsOff;
import hla.rti1516e.exceptions.ObjectClassRelevanceAdvisorySwitchIsOn;
import hla.rti1516e.exceptions.ObjectInstanceNameInUse;
import hla.rti1516e.exceptions.ObjectInstanceNameNotReserved;
import hla.rti1516e.exceptions.ObjectInstanceNotKnown;
import hla.rti1516e.exceptions.OwnershipAcquisitionPending;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RegionDoesNotContainSpecifiedDimension;
import hla.rti1516e.exceptions.RegionInUseForUpdateOrSubscription;
import hla.rti1516e.exceptions.RegionNotCreatedByThisFederate;
import hla.rti1516e.exceptions.RequestForTimeConstrainedPending;
import hla.rti1516e.exceptions.RequestForTimeRegulationPending;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.RestoreNotInProgress;
import hla.rti1516e.exceptions.RestoreNotRequested;
import hla.rti1516e.exceptions.SaveInProgress;
import hla.rti1516e.exceptions.SaveNotInProgress;
import hla.rti1516e.exceptions.SaveNotInitiated;
import hla.rti1516e.exceptions.SynchronizationPointLabelNotAnnounced;
import hla.rti1516e.exceptions.TimeConstrainedAlreadyEnabled;
import hla.rti1516e.exceptions.TimeConstrainedIsNotEnabled;
import hla.rti1516e.exceptions.TimeRegulationAlreadyEnabled;
import hla.rti1516e.exceptions.TimeRegulationIsNotEnabled;
import hla.rti1516e.exceptions.UnsupportedCallbackModel;
import hla.rti1516e.time.HLAfloat64Interval;
import hla.rti1516e.time.HLAfloat64Time;
import hla.rti1516e.time.HLAinteger64Interval;
import hla.rti1516e.time.HLAinteger64Time;

public class RTIambassadorImpl extends JniObject implements RTIambassador
{
    private static final String EMPTY_MESSAGE = "";
    
    private final AttributeHandleFactory ATTRIBUTE_HANDLE_FACTORY = new AttributeHandleFactoryImpl(this);

    private static final AttributeHandleSetFactory ATTRIBUTE_HANDLE_SET_FACTORY = new AttributeHandleSetFactoryImpl();

    private static final AttributeHandleValueMapFactory ATTRIBUTE_HANDLE_VALUE_MAP_FACTORY = new AttributeHandleValueMapFactoryImpl();

    private static final AttributeSetRegionSetPairListFactory ATTRIBUTE_SET_REGION_SET_PAIR_LIST_FACTORY = new AttributeSetRegionSetPairListFactoryImpl();

    private static final DimensionHandleFactory DIMENSION_HANDLE_FACTORY = new DimensionHandleFactoryImpl();

    private static final DimensionHandleSetFactory DIMENSION_HANDLE_SET_FACTORY = new DimensionHandleSetFactoryImpl();

    private final FederateHandleFactory FEDERATE_HANDLE_FACTORY = new FederateHandleFactoryImpl(this);

    private static final FederateHandleSetFactory FEDERATE_HANDLE_SET_FACTORY = new FederateHandleSetFactoryImpl();

    private static final InteractionClassHandleFactory INTERACTION_CLASS_HANDLE_FACTORY = new InteractionClassHandleFactoryImpl();

    private final ObjectClassHandleFactory OBJECT_CLASS_HANDLE_FACTORY = new ObjectClassHandleFactoryImpl(this);

    private final ObjectInstanceHandleFactory OBJECT_INSTANCE_HANDLE_FACTORY = new ObjectInstanceHandleFactoryImpl(this);

    private final ParameterHandleFactory PARAMETER_HANDLE_FACTORY = new ParameterHandleFactoryImpl(this);

    private static final ParameterHandleValueMapFactory PARAMETER_HANDLE_VALUE_MAP_FACTORY = new ParameterHandleValueMapFactoryImpl();

    private static final RegionHandleSetFactory REGION_HANDLE_SET_FACTORY = new RegionHandleSetFactoryImpl();

    private static final TransportationTypeHandleFactory TRANSPORTATION_TYPE_HANDLE_FACTORY = new TransportationTypeHandleFactoryImpl();

    private static final String HLA_VERSION = "2010.1";

    private boolean connected;
    private String designator = "";
    private boolean federateExecutionMember;

    ObjectInstanceHandle decodeObjectInstanceHandle(byte[] buffer, int offset)
    {
        if (buffer == null)
            throw new NullPointerException("buffer is null");
        return decodeObjectInstanceHandle_rti(buffer, offset);
    }
    private native ObjectInstanceHandle decodeObjectInstanceHandle_rti(byte[] buffer, int offset);
    
    ObjectClassHandle decodeObjectClassHandle(byte[] buffer, int offset)
    {
        if (buffer == null)
            throw new NullPointerException("buffer is null");
        return decodeObjectClassHandle_rti(buffer, offset);
    }
    private native ObjectClassHandle decodeObjectClassHandle_rti(byte[] buffer, int offset);
    
    FederateHandle decodeFederateHandle(byte[] buffer, int offset)
    {
        if (buffer == null)
            throw new NullPointerException("buffer is null");
        return decodeFederateHandle_rti(buffer, offset);
    }
    private native FederateHandle decodeFederateHandle_rti(byte[] buffer, int offset);
    
    AttributeHandle decodeAttributeHandle(byte[] buffer, int offset)
    {
        if (buffer == null)
            throw new NullPointerException("buffer is null");
        return decodeAttributeHandle_rti(buffer, offset);
    }
    private native AttributeHandle decodeAttributeHandle_rti(byte[] buffer, int offset);
    
    ParameterHandle decodeParameterHandle(byte[] buffer, int offset)
    {
        if (buffer == null)
            throw new NullPointerException("buffer is null");
        return decodeParameterHandle_rti(buffer, offset);
    }
    private native ParameterHandle decodeParameterHandle_rti(byte[] buffer, int offset);
    
    @Override
    public void connect(FederateAmbassador federateReference, CallbackModel callbackModel,
            String localSettingsDesignator) throws ConnectionFailed, InvalidLocalSettingsDesignator,
            UnsupportedCallbackModel, AlreadyConnected, CallNotAllowedFromWithinCallback, RTIinternalError
    {
        if (isConnected())
        {
            throw new AlreadyConnected(designator);
        }
        if (localSettingsDesignator == null)
        {
            throw new InvalidLocalSettingsDesignator("null");
        }
        connect_rti(federateReference, callbackModel, localSettingsDesignator);
        connected = true;
        designator = localSettingsDesignator;
    }

    @Override
    public void connect(FederateAmbassador federateReference, CallbackModel callbackModel)
            throws ConnectionFailed, InvalidLocalSettingsDesignator, UnsupportedCallbackModel, AlreadyConnected,
            CallNotAllowedFromWithinCallback, RTIinternalError
    {
        connect(federateReference, callbackModel, "");
    }

    @Override
    public void disconnect() throws FederateIsExecutionMember, CallNotAllowedFromWithinCallback, RTIinternalError
    {
        disconnect_rti();
        connected = false;
        designator = "";
    }

    @Override
    public void createFederationExecution(String federationExecutionName, URL[] fomModules, URL mimModule,
            String logicalTimeImplementationName) throws CouldNotCreateLogicalTimeFactory, InconsistentFDD,
            ErrorReadingFDD, CouldNotOpenFDD, ErrorReadingMIM, CouldNotOpenMIM, DesignatorIsHLAstandardMIM,
            FederationExecutionAlreadyExists, NotConnected, RTIinternalError
    {
        createFederationExecution_rti(federationExecutionName, fomModules, mimModule, logicalTimeImplementationName);
    }

    @Override
    public void createFederationExecution(String federationExecutionName, URL[] fomModules,
            String logicalTimeImplementationName) throws CouldNotCreateLogicalTimeFactory, InconsistentFDD,
            ErrorReadingFDD, CouldNotOpenFDD, FederationExecutionAlreadyExists, NotConnected, RTIinternalError
    {
        createFederationExecution_rti(federationExecutionName, fomModules, logicalTimeImplementationName);
    }

    @Override
    public void createFederationExecution(String federationExecutionName, URL[] fomModules, URL mimModule)
            throws InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, ErrorReadingMIM, CouldNotOpenMIM,
            DesignatorIsHLAstandardMIM, FederationExecutionAlreadyExists, NotConnected, RTIinternalError
    {
        createFederationExecution_rti(federationExecutionName, fomModules, mimModule);
    }

    @Override
    public void createFederationExecution(String federationExecutionName, URL[] fomModules) throws InconsistentFDD,
            ErrorReadingFDD, CouldNotOpenFDD, FederationExecutionAlreadyExists, NotConnected, RTIinternalError
    {
        createFederationExecution_rti(federationExecutionName, fomModules);
    }

    @Override
    public void createFederationExecution(String federationExecutionName, URL fomModule) throws InconsistentFDD,
            ErrorReadingFDD, CouldNotOpenFDD, FederationExecutionAlreadyExists, NotConnected, RTIinternalError
    {
        createFederationExecution_rti(federationExecutionName, fomModule);
    }

    @Override
    public void destroyFederationExecution(String federationExecutionName)
            throws FederatesCurrentlyJoined, FederationExecutionDoesNotExist, NotConnected, RTIinternalError
    {
        destroyFederationExecution_rti(federationExecutionName);
    }

    @Override
    public void listFederationExecutions() throws NotConnected, RTIinternalError
    {
        listFederationExecutions_rti();
    }

    @Override
    public FederateHandle joinFederationExecution(String federateName, String federateType,
            String federationExecutionName, URL[] additionalFomModules)
            throws CouldNotCreateLogicalTimeFactory, FederateNameAlreadyInUse, FederationExecutionDoesNotExist,
            InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress, RestoreInProgress,
            FederateAlreadyExecutionMember, NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError
    {
        FederateHandle result = joinFederationExecution_rti(federateName, federateType, federationExecutionName, additionalFomModules);
        federateExecutionMember = true;
        return result;
    }

    @Override
    public FederateHandle joinFederationExecution(String federateType, String federationExecutionName,
            URL[] additionalFomModules) throws CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist,
            InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress, RestoreInProgress,
            FederateAlreadyExecutionMember, NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public FederateHandle joinFederationExecution(String federateName, String federateType,
            String federationExecutionName) throws CouldNotCreateLogicalTimeFactory, FederateNameAlreadyInUse,
            FederationExecutionDoesNotExist, SaveInProgress, RestoreInProgress, FederateAlreadyExecutionMember,
            NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public FederateHandle joinFederationExecution(String federateType, String federationExecutionName)
            throws CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist, SaveInProgress, RestoreInProgress,
            FederateAlreadyExecutionMember, NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void resignFederationExecution(ResignAction resignAction)
            throws InvalidResignAction, OwnershipAcquisitionPending, FederateOwnsAttributes, FederateNotExecutionMember,
            NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError
    {
        resignFederationExecution_rti(resignAction);
        federateExecutionMember = false;
    }

    @Override
    public void registerFederationSynchronizationPoint(String synchronizationPointLabel, byte[] userSuppliedTag)
            throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void registerFederationSynchronizationPoint(String synchronizationPointLabel, byte[] userSuppliedTag,
            FederateHandleSet synchronizationSet) throws InvalidFederateHandle, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void synchronizationPointAchieved(String synchronizationPointLabel)
            throws SynchronizationPointLabelNotAnnounced, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void synchronizationPointAchieved(String synchronizationPointLabel, boolean successIndicator)
            throws SynchronizationPointLabelNotAnnounced, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void requestFederationSave(String label)
            throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void requestFederationSave(String label, LogicalTime theTime)
            throws LogicalTimeAlreadyPassed, InvalidLogicalTime, FederateUnableToUseTime, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void federateSaveBegun()
            throws SaveNotInitiated, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void federateSaveComplete() throws FederateHasNotBegunSave, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void federateSaveNotComplete() throws FederateHasNotBegunSave, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void abortFederationSave()
            throws SaveNotInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void queryFederationSaveStatus()
            throws RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void requestFederationRestore(String label)
            throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void federateRestoreComplete()
            throws RestoreNotRequested, SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void federateRestoreNotComplete()
            throws RestoreNotRequested, SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void abortFederationRestore()
            throws RestoreNotInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void queryFederationRestoreStatus()
            throws SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void publishObjectClassAttributes(ObjectClassHandle theClass, AttributeHandleSet attributeList)
            throws AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        publishObjectClassAttributes_rti(theClass, attributeList.toArray());
    }

    @Override
    public void unpublishObjectClass(ObjectClassHandle theClass)
            throws OwnershipAcquisitionPending, ObjectClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void unpublishObjectClassAttributes(ObjectClassHandle theClass, AttributeHandleSet attributeList)
            throws OwnershipAcquisitionPending, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void publishInteractionClass(InteractionClassHandle theInteraction) throws InteractionClassNotDefined,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        publishInteractionClass_rti(theInteraction);
    }

    @Override
    public void unpublishInteractionClass(InteractionClassHandle theInteraction) throws InteractionClassNotDefined,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void subscribeObjectClassAttributes(ObjectClassHandle theClass, AttributeHandleSet attributeList)
            throws AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        subscribeObjectClassAttributes_rti(theClass, attributeList.toArray());
    }

    @Override
    public void subscribeObjectClassAttributes(ObjectClassHandle theClass, AttributeHandleSet attributeList,
            String updateRateDesignator) throws AttributeNotDefined, ObjectClassNotDefined, InvalidUpdateRateDesignator,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void subscribeObjectClassAttributesPassively(ObjectClassHandle theClass, AttributeHandleSet attributeList)
            throws AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void subscribeObjectClassAttributesPassively(ObjectClassHandle theClass, AttributeHandleSet attributeList,
            String updateRateDesignator) throws AttributeNotDefined, ObjectClassNotDefined, InvalidUpdateRateDesignator,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void unsubscribeObjectClass(ObjectClassHandle theClass) throws ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void unsubscribeObjectClassAttributes(ObjectClassHandle theClass, AttributeHandleSet attributeList)
            throws AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void subscribeInteractionClass(InteractionClassHandle theClass)
            throws FederateServiceInvocationsAreBeingReportedViaMOM, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        subscribeInteractionClass_rti(theClass);
    }

    @Override
    public void subscribeInteractionClassPassively(InteractionClassHandle theClass)
            throws FederateServiceInvocationsAreBeingReportedViaMOM, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void unsubscribeInteractionClass(InteractionClassHandle theClass) throws InteractionClassNotDefined,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void reserveObjectInstanceName(String theObjectName) throws IllegalName, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void releaseObjectInstanceName(String theObjectInstanceName) throws ObjectInstanceNameNotReserved,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void reserveMultipleObjectInstanceName(Set<String> theObjectNames) throws IllegalName, NameSetWasEmpty,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void releaseMultipleObjectInstanceName(Set<String> theObjectNames) throws ObjectInstanceNameNotReserved,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public ObjectInstanceHandle registerObjectInstance(ObjectClassHandle theClass)
            throws ObjectClassNotPublished, ObjectClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return registerObjectInstance_rti(theClass);
    }

    @Override
    public ObjectInstanceHandle registerObjectInstance(ObjectClassHandle theClass, String theObjectName)
            throws ObjectInstanceNameInUse, ObjectInstanceNameNotReserved, ObjectClassNotPublished,
            ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected,
            RTIinternalError
    {
        return registerObjectInstance_rti(theClass, theObjectName);
    }

    @Override
    public void updateAttributeValues(ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes,
            byte[] userSuppliedTag) throws AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        updateAttributeValues_rti(theObject, theAttributes.entrySet().toArray(), userSuppliedTag);
    }

    @Override
    public MessageRetractionReturn updateAttributeValues(ObjectInstanceHandle theObject,
            AttributeHandleValueMap theAttributes, byte[] userSuppliedTag, LogicalTime theTime)
            throws InvalidLogicalTime, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        if (theTime instanceof HLAinteger64Time)
        {
            return updateAttributeValuesInteger64_rti(theObject, theAttributes.entrySet().toArray(), userSuppliedTag,
                    ((HLAinteger64Time) theTime).getValue());
        }
        else if (theTime instanceof HLAfloat64Time)
        {
            return updateAttributeValuesFloat64_rti(theObject, theAttributes.entrySet().toArray(), userSuppliedTag,
                    ((HLAfloat64Time) theTime).getValue());
        }
        else
        {
            throw new InvalidLogicalTime(String.valueOf(theTime));
        }
    }

    @Override
    public void sendInteraction(InteractionClassHandle theInteraction, ParameterHandleValueMap theParameters,
            byte[] userSuppliedTag)
            throws InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        sendInteraction_rti(theInteraction, theParameters.entrySet().toArray(), userSuppliedTag);
    }

    @Override
    public MessageRetractionReturn sendInteraction(InteractionClassHandle theInteraction,
            ParameterHandleValueMap theParameters, byte[] userSuppliedTag, LogicalTime theTime)
            throws InvalidLogicalTime, InteractionClassNotPublished, InteractionParameterNotDefined,
            InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected,
            RTIinternalError
    {
        if (theTime instanceof HLAinteger64Time)
        {
            return sendInteractionInteger64_rti(theInteraction, theParameters.entrySet().toArray(), userSuppliedTag,
                    ((HLAinteger64Time) theTime).getValue());
        }
        else if (theTime instanceof HLAfloat64Time)
        {
            return sendInteractionFloat64_rti(theInteraction, theParameters.entrySet().toArray(), userSuppliedTag,
                    ((HLAfloat64Time) theTime).getValue());
        }
        else
        {
            throw new InvalidLogicalTime(String.valueOf(theTime));
        }
    }

    @Override
    public void deleteObjectInstance(ObjectInstanceHandle objectHandle, byte[] userSuppliedTag)
            throws DeletePrivilegeNotHeld, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public MessageRetractionReturn deleteObjectInstance(ObjectInstanceHandle objectHandle, byte[] userSuppliedTag,
            LogicalTime theTime) throws InvalidLogicalTime, DeletePrivilegeNotHeld, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void localDeleteObjectInstance(ObjectInstanceHandle objectHandle)
            throws OwnershipAcquisitionPending, FederateOwnsAttributes, ObjectInstanceNotKnown, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void requestAttributeValueUpdate(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes,
            byte[] userSuppliedTag) throws AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        requestAttributeValueUpdate_rti(theObject, theAttributes.toArray(), userSuppliedTag);
    }

    @Override
    public void requestAttributeValueUpdate(ObjectClassHandle theClass, AttributeHandleSet theAttributes,
            byte[] userSuppliedTag) throws AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        requestAttributeValueUpdate_rti(theClass, theAttributes.toArray(), userSuppliedTag);
    }

    @Override
    public void requestAttributeTransportationTypeChange(ObjectInstanceHandle theObject,
            AttributeHandleSet theAttributes, TransportationTypeHandle theType) throws AttributeAlreadyBeingChanged,
            AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, InvalidTransportationType, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void queryAttributeTransportationType(ObjectInstanceHandle theObject, AttributeHandle theAttribute)
            throws AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void requestInteractionTransportationTypeChange(InteractionClassHandle theClass,
            TransportationTypeHandle theType) throws InteractionClassAlreadyBeingChanged, InteractionClassNotPublished,
            InteractionClassNotDefined, InvalidTransportationType, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void queryInteractionTransportationType(FederateHandle theFederate, InteractionClassHandle theInteraction)
            throws InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void unconditionalAttributeOwnershipDivestiture(ObjectInstanceHandle theObject,
            AttributeHandleSet theAttributes) throws AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void negotiatedAttributeOwnershipDivestiture(ObjectInstanceHandle theObject,
            AttributeHandleSet theAttributes, byte[] userSuppliedTag)
            throws AttributeAlreadyBeingDivested, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void confirmDivestiture(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes,
            byte[] userSuppliedTag) throws NoAcquisitionPending, AttributeDivestitureWasNotRequested, AttributeNotOwned,
            AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void attributeOwnershipAcquisition(ObjectInstanceHandle theObject, AttributeHandleSet desiredAttributes,
            byte[] userSuppliedTag) throws AttributeNotPublished, ObjectClassNotPublished, FederateOwnsAttributes,
            AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void attributeOwnershipAcquisitionIfAvailable(ObjectInstanceHandle theObject,
            AttributeHandleSet desiredAttributes) throws AttributeAlreadyBeingAcquired, AttributeNotPublished,
            ObjectClassNotPublished, FederateOwnsAttributes, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void attributeOwnershipReleaseDenied(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
            throws AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public AttributeHandleSet attributeOwnershipDivestitureIfWanted(ObjectInstanceHandle theObject,
            AttributeHandleSet theAttributes) throws AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void cancelNegotiatedAttributeOwnershipDivestiture(ObjectInstanceHandle theObject,
            AttributeHandleSet theAttributes)
            throws AttributeDivestitureWasNotRequested, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void cancelAttributeOwnershipAcquisition(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
            throws AttributeAcquisitionWasNotRequested, AttributeAlreadyOwned, AttributeNotDefined,
            ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected,
            RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void queryAttributeOwnership(ObjectInstanceHandle theObject, AttributeHandle theAttribute)
            throws AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public boolean isAttributeOwnedByFederate(ObjectInstanceHandle theObject, AttributeHandle theAttribute)
            throws AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void enableTimeRegulation(LogicalTimeInterval theLookahead) throws InvalidLookahead, InTimeAdvancingState,
            RequestForTimeRegulationPending, TimeRegulationAlreadyEnabled, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        if (theLookahead instanceof HLAinteger64Interval)
        {
            enableTimeRegulationInteger64_rti(((HLAinteger64Interval) theLookahead).getValue());
        }
        else if (theLookahead instanceof HLAfloat64Interval)
        {
            enableTimeRegulationFloat64_rti(((HLAfloat64Interval) theLookahead).getValue());
        }
        else
        {
            throw new InvalidLookahead(String.valueOf(theLookahead));
        }

    }

    @Override
    public void disableTimeRegulation() throws TimeRegulationIsNotEnabled, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void enableTimeConstrained()
            throws InTimeAdvancingState, RequestForTimeConstrainedPending, TimeConstrainedAlreadyEnabled,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        enableTimeConstrained_rti();
    }

    @Override
    public void disableTimeConstrained() throws TimeConstrainedIsNotEnabled, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void timeAdvanceRequest(LogicalTime theTime) throws LogicalTimeAlreadyPassed, InvalidLogicalTime,
            InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        if (theTime instanceof HLAinteger64Time)
        {
            timeAdvanceRequestInteger64_rti(((HLAinteger64Time) theTime).getValue());
        }
        else if (theTime instanceof HLAfloat64Time)
        {
            timeAdvanceRequestFloat64_rti(((HLAfloat64Time) theTime).getValue());
        }
        else
        {
            throw new InvalidLogicalTime(String.valueOf(theTime));
        }
    }

    @Override
    public void timeAdvanceRequestAvailable(LogicalTime theTime) throws LogicalTimeAlreadyPassed, InvalidLogicalTime,
            InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void nextMessageRequest(LogicalTime theTime) throws LogicalTimeAlreadyPassed, InvalidLogicalTime,
            InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void nextMessageRequestAvailable(LogicalTime theTime) throws LogicalTimeAlreadyPassed, InvalidLogicalTime,
            InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void flushQueueRequest(LogicalTime theTime) throws LogicalTimeAlreadyPassed, InvalidLogicalTime,
            InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void enableAsynchronousDelivery() throws AsynchronousDeliveryAlreadyEnabled, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void disableAsynchronousDelivery() throws AsynchronousDeliveryAlreadyDisabled, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public TimeQueryReturn queryGALT()
            throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public LogicalTime queryLogicalTime()
            throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public TimeQueryReturn queryLITS()
            throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void modifyLookahead(LogicalTimeInterval theLookahead)
            throws InvalidLookahead, InTimeAdvancingState, TimeRegulationIsNotEnabled, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public LogicalTimeInterval queryLookahead() throws TimeRegulationIsNotEnabled, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void retract(MessageRetractionHandle theHandle)
            throws MessageCanNoLongerBeRetracted, InvalidMessageRetractionHandle, TimeRegulationIsNotEnabled,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void changeAttributeOrderType(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes,
            OrderType theType) throws AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void changeInteractionOrderType(InteractionClassHandle theClass, OrderType theType)
            throws InteractionClassNotPublished, InteractionClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public RegionHandle createRegion(DimensionHandleSet dimensions) throws InvalidDimensionHandle, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void commitRegionModifications(RegionHandleSet regions) throws RegionNotCreatedByThisFederate, InvalidRegion,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void deleteRegion(RegionHandle theRegion)
            throws RegionInUseForUpdateOrSubscription, RegionNotCreatedByThisFederate, InvalidRegion, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public ObjectInstanceHandle registerObjectInstanceWithRegions(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions)
            throws InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotPublished,
            ObjectClassNotPublished, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public ObjectInstanceHandle registerObjectInstanceWithRegions(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions, String theObject) throws ObjectInstanceNameInUse,
            ObjectInstanceNameNotReserved, InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion,
            AttributeNotPublished, ObjectClassNotPublished, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void associateRegionsForUpdates(ObjectInstanceHandle theObject,
            AttributeSetRegionSetPairList attributesAndRegions) throws InvalidRegionContext,
            RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void unassociateRegionsForUpdates(ObjectInstanceHandle theObject,
            AttributeSetRegionSetPairList attributesAndRegions)
            throws RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void subscribeObjectClassAttributesWithRegions(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions) throws InvalidRegionContext,
            RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void subscribeObjectClassAttributesWithRegions(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions, String updateRateDesignator)
            throws InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined,
            ObjectClassNotDefined, InvalidUpdateRateDesignator, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void subscribeObjectClassAttributesPassivelyWithRegions(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions) throws InvalidRegionContext,
            RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void subscribeObjectClassAttributesPassivelyWithRegions(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions, String updateRateDesignator)
            throws InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined,
            ObjectClassNotDefined, InvalidUpdateRateDesignator, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void unsubscribeObjectClassAttributesWithRegions(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions)
            throws RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void subscribeInteractionClassWithRegions(InteractionClassHandle theClass, RegionHandleSet regions)
            throws FederateServiceInvocationsAreBeingReportedViaMOM, InvalidRegionContext,
            RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void subscribeInteractionClassPassivelyWithRegions(InteractionClassHandle theClass, RegionHandleSet regions)
            throws FederateServiceInvocationsAreBeingReportedViaMOM, InvalidRegionContext,
            RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void unsubscribeInteractionClassWithRegions(InteractionClassHandle theClass, RegionHandleSet regions)
            throws RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void sendInteractionWithRegions(InteractionClassHandle theInteraction, ParameterHandleValueMap theParameters,
            RegionHandleSet regions, byte[] userSuppliedTag)
            throws InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotPublished,
            InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public MessageRetractionReturn sendInteractionWithRegions(InteractionClassHandle theInteraction,
            ParameterHandleValueMap theParameters, RegionHandleSet regions, byte[] userSuppliedTag, LogicalTime theTime)
            throws InvalidLogicalTime, InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion,
            InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void requestAttributeValueUpdateWithRegions(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions, byte[] userSuppliedTag) throws InvalidRegionContext,
            RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public ResignAction getAutomaticResignDirective() throws FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void setAutomaticResignDirective(ResignAction resignAction)
            throws InvalidResignAction, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public FederateHandle getFederateHandle(String theName)
            throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public String getFederateName(FederateHandle theHandle) throws InvalidFederateHandle, FederateHandleNotKnown,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public ObjectClassHandle getObjectClassHandle(String theName)
            throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getObjectClassHandle_rti(theName);
    }

    @Override
    public String getObjectClassName(ObjectClassHandle theHandle)
            throws InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getObjectClassName_rti(theHandle);
    }

    @Override
    public ObjectClassHandle getKnownObjectClassHandle(ObjectInstanceHandle theObject)
            throws ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public ObjectInstanceHandle getObjectInstanceHandle(String theName)
            throws ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getObjectInstanceHandle_rti(theName);
    }

    @Override
    public String getObjectInstanceName(ObjectInstanceHandle theHandle)
            throws ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getObjectInstanceName_rti(theHandle);
    }

    @Override
    public AttributeHandle getAttributeHandle(ObjectClassHandle whichClass, String theName)
            throws NameNotFound, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getAttributeHandle_rti(whichClass, theName);
    }

    @Override
    public String getAttributeName(ObjectClassHandle whichClass, AttributeHandle theHandle) throws AttributeNotDefined,
            InvalidAttributeHandle, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getAttributeName_rti(whichClass, theHandle);
    }

    @Override
    public double getUpdateRateValue(String updateRateDesignator)
            throws InvalidUpdateRateDesignator, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public double getUpdateRateValueForAttribute(ObjectInstanceHandle theObject, AttributeHandle theAttribute)
            throws ObjectInstanceNotKnown, AttributeNotDefined, FederateNotExecutionMember, NotConnected,
            RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public InteractionClassHandle getInteractionClassHandle(String theName)
            throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getInteractionClassHandle_rti(theName);
    }

    @Override
    public String getInteractionClassName(InteractionClassHandle theHandle)
            throws InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getInteractionClassName_rti(theHandle);
    }

    @Override
    public ParameterHandle getParameterHandle(InteractionClassHandle whichClass, String theName) throws NameNotFound,
            InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        return getParameterHandle_rti(whichClass, theName);
    }

    @Override
    public String getParameterName(InteractionClassHandle whichClass, ParameterHandle theHandle)
            throws InteractionParameterNotDefined, InvalidParameterHandle, InvalidInteractionClassHandle,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public OrderType getOrderType(String theName)
            throws InvalidOrderName, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public String getOrderName(OrderType theType)
            throws InvalidOrderType, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public TransportationTypeHandle getTransportationTypeHandle(String theName)
            throws InvalidTransportationName, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public String getTransportationTypeName(TransportationTypeHandle theHandle)
            throws InvalidTransportationType, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public DimensionHandleSet getAvailableDimensionsForClassAttribute(ObjectClassHandle whichClass,
            AttributeHandle theHandle) throws AttributeNotDefined, InvalidAttributeHandle, InvalidObjectClassHandle,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public DimensionHandleSet getAvailableDimensionsForInteractionClass(InteractionClassHandle theHandle)
            throws InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public DimensionHandle getDimensionHandle(String theName)
            throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public String getDimensionName(DimensionHandle theHandle)
            throws InvalidDimensionHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public long getDimensionUpperBound(DimensionHandle theHandle)
            throws InvalidDimensionHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public DimensionHandleSet getDimensionHandleSet(RegionHandle region) throws InvalidRegion, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public RangeBounds getRangeBounds(RegionHandle region, DimensionHandle dimension)
            throws RegionDoesNotContainSpecifiedDimension, InvalidRegion, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void setRangeBounds(RegionHandle region, DimensionHandle dimension, RangeBounds bounds)
            throws InvalidRangeBound, RegionDoesNotContainSpecifiedDimension, RegionNotCreatedByThisFederate,
            InvalidRegion, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public long normalizeFederateHandle(FederateHandle federateHandle)
            throws InvalidFederateHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public long normalizeServiceGroup(ServiceGroup group)
            throws InvalidServiceGroup, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void enableObjectClassRelevanceAdvisorySwitch() throws ObjectClassRelevanceAdvisorySwitchIsOn,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void disableObjectClassRelevanceAdvisorySwitch() throws ObjectClassRelevanceAdvisorySwitchIsOff,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void enableAttributeRelevanceAdvisorySwitch() throws AttributeRelevanceAdvisorySwitchIsOn, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void disableAttributeRelevanceAdvisorySwitch() throws AttributeRelevanceAdvisorySwitchIsOff, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void enableAttributeScopeAdvisorySwitch() throws AttributeScopeAdvisorySwitchIsOn, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void disableAttributeScopeAdvisorySwitch() throws AttributeScopeAdvisorySwitchIsOff, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void enableInteractionRelevanceAdvisorySwitch() throws InteractionRelevanceAdvisorySwitchIsOn,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void disableInteractionRelevanceAdvisorySwitch() throws InteractionRelevanceAdvisorySwitchIsOff,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public boolean evokeCallback(double approximateMinimumTimeInSeconds)
            throws CallNotAllowedFromWithinCallback, RTIinternalError
    {
        return evokeCallback_rti(approximateMinimumTimeInSeconds);
    }

    @Override
    public boolean evokeMultipleCallbacks(double approximateMinimumTimeInSeconds,
            double approximateMaximumTimeInSeconds) throws CallNotAllowedFromWithinCallback, RTIinternalError
    {
        return evokeMultipleCallbacks_rti(approximateMinimumTimeInSeconds, approximateMaximumTimeInSeconds);
    }

    @Override
    public void enableCallbacks() throws SaveInProgress, RestoreInProgress, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public void disableCallbacks() throws SaveInProgress, RestoreInProgress, RTIinternalError
    {
        throw new RTIinternalError("not implemented");
    }

    @Override
    public AttributeHandleFactory getAttributeHandleFactory() throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return ATTRIBUTE_HANDLE_FACTORY;
    }

    @Override
    public AttributeHandleSetFactory getAttributeHandleSetFactory() throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return ATTRIBUTE_HANDLE_SET_FACTORY;
    }

    @Override
    public AttributeHandleValueMapFactory getAttributeHandleValueMapFactory()
            throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return ATTRIBUTE_HANDLE_VALUE_MAP_FACTORY;
    }

    @Override
    public AttributeSetRegionSetPairListFactory getAttributeSetRegionSetPairListFactory()
            throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return ATTRIBUTE_SET_REGION_SET_PAIR_LIST_FACTORY;
    }

    @Override
    public DimensionHandleFactory getDimensionHandleFactory() throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return DIMENSION_HANDLE_FACTORY;
    }

    @Override
    public DimensionHandleSetFactory getDimensionHandleSetFactory() throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return DIMENSION_HANDLE_SET_FACTORY;
    }

    @Override
    public FederateHandleFactory getFederateHandleFactory() throws FederateNotExecutionMember, NotConnected
    {
        return FEDERATE_HANDLE_FACTORY;
    }

    @Override
    public FederateHandleSetFactory getFederateHandleSetFactory() throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
       return FEDERATE_HANDLE_SET_FACTORY;
    }

    @Override
    public InteractionClassHandleFactory getInteractionClassHandleFactory()
            throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return INTERACTION_CLASS_HANDLE_FACTORY;
    }

    @Override
    public ObjectClassHandleFactory getObjectClassHandleFactory() throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return OBJECT_CLASS_HANDLE_FACTORY;
    }

    @Override
    public ObjectInstanceHandleFactory getObjectInstanceHandleFactory() throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return OBJECT_INSTANCE_HANDLE_FACTORY;
    }

    @Override
    public ParameterHandleFactory getParameterHandleFactory() throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return PARAMETER_HANDLE_FACTORY;
    }

    @Override
    public ParameterHandleValueMapFactory getParameterHandleValueMapFactory()
            throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return PARAMETER_HANDLE_VALUE_MAP_FACTORY;
    }

    @Override
    public RegionHandleSetFactory getRegionHandleSetFactory() throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return REGION_HANDLE_SET_FACTORY;
    }

    @Override
    public TransportationTypeHandleFactory getTransportationTypeHandleFactory()
            throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return TRANSPORTATION_TYPE_HANDLE_FACTORY;
    }

    @Override
    public String getHLAversion()
    {
        return HLA_VERSION;
    }

    @Override
    public LogicalTimeFactory getTimeFactory() throws FederateNotExecutionMember, NotConnected
    {
        checkConnected();
        checkFederateExecutionMember();
        return null; //TODO
    }

    // NATIVE
    ////////////////////////////////////
    //Federation Management Services //
    ////////////////////////////////////

    // 4.2
    private native void connect_rti(FederateAmbassador federateReference, CallbackModel callbackModel,
            String localSettingsDesignator) throws ConnectionFailed, InvalidLocalSettingsDesignator,
            UnsupportedCallbackModel, AlreadyConnected, CallNotAllowedFromWithinCallback, RTIinternalError;

    // 4.3
    private native void disconnect_rti()
            throws FederateIsExecutionMember, CallNotAllowedFromWithinCallback, RTIinternalError;

    //4.5
    private native void createFederationExecution_rti(String federationExecutionName, URL[] fomModules, URL mimModule,
            String logicalTimeImplementationName) throws CouldNotCreateLogicalTimeFactory, InconsistentFDD,
            ErrorReadingFDD, CouldNotOpenFDD, ErrorReadingMIM, CouldNotOpenMIM, DesignatorIsHLAstandardMIM,
            FederationExecutionAlreadyExists, NotConnected, RTIinternalError;

    //4.5
    private native void createFederationExecution_rti(String federationExecutionName, URL[] fomModules,
            String logicalTimeImplementationName) throws CouldNotCreateLogicalTimeFactory, InconsistentFDD,
            ErrorReadingFDD, CouldNotOpenFDD, FederationExecutionAlreadyExists, NotConnected, RTIinternalError;

    //4.5
    private native void createFederationExecution_rti(String federationExecutionName, URL[] fomModules, URL mimModule)
            throws InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, ErrorReadingMIM, CouldNotOpenMIM,
            DesignatorIsHLAstandardMIM, FederationExecutionAlreadyExists, NotConnected, RTIinternalError;

    //4.5
    private native void createFederationExecution_rti(String federationExecutionName, URL[] fomModules)
            throws InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, FederationExecutionAlreadyExists, NotConnected,
            RTIinternalError;

    //4.5
    private native void createFederationExecution_rti(String federationExecutionName, URL fomModule)
            throws InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, FederationExecutionAlreadyExists, NotConnected,
            RTIinternalError;

    //4.6
    private native void destroyFederationExecution_rti(String federationExecutionName)
            throws FederatesCurrentlyJoined, FederationExecutionDoesNotExist, NotConnected, RTIinternalError;

    // 4.7
    private native void listFederationExecutions_rti() throws NotConnected, RTIinternalError;

    //4.9
    private native FederateHandle joinFederationExecution_rti(String federateName, String federateType,
            String federationExecutionName, URL[] additionalFomModules)
            throws CouldNotCreateLogicalTimeFactory, FederateNameAlreadyInUse, FederationExecutionDoesNotExist,
            InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress, RestoreInProgress,
            FederateAlreadyExecutionMember, NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError;

    //4.9
    private native FederateHandle joinFederationExecution_rti(String federateType, String federationExecutionName,
            URL[] additionalFomModules) throws CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist,
            InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress, RestoreInProgress,
            FederateAlreadyExecutionMember, NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError;

    //4.9
    private native FederateHandle joinFederationExecution_rti(String federateName, String federateType,
            String federationExecutionName) throws CouldNotCreateLogicalTimeFactory, FederateNameAlreadyInUse,
            FederationExecutionDoesNotExist, SaveInProgress, RestoreInProgress, FederateAlreadyExecutionMember,
            NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError;

    //4.9
    private native FederateHandle joinFederationExecution_rti(String federateType, String federationExecutionName)
            throws CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist, SaveInProgress, RestoreInProgress,
            FederateAlreadyExecutionMember, NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError;

    //4.10
    private native void resignFederationExecution_rti(ResignAction resignAction)
            throws InvalidResignAction, OwnershipAcquisitionPending, FederateOwnsAttributes, FederateNotExecutionMember,
            NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError;

    //4.11
    private native void registerFederationSynchronizationPoint_rti(String synchronizationPointLabel,
            byte[] userSuppliedTag)
            throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    //4.11
    private native void registerFederationSynchronizationPoint_rti(String synchronizationPointLabel,
            byte[] userSuppliedTag, FederateHandleSet synchronizationSet) throws InvalidFederateHandle, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    //4.14
    private native void synchronizationPointAchieved_rti(String synchronizationPointLabel)
            throws SynchronizationPointLabelNotAnnounced, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError;

    //4.14
    private native void synchronizationPointAchieved_rti(String synchronizationPointLabel, boolean successIndicator)
            throws SynchronizationPointLabelNotAnnounced, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError;

    // 4.16
    private native void requestFederationSave_rti(String label)
            throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 4.16
    private native void requestFederationSave_rti(String label, LogicalTime theTime)
            throws LogicalTimeAlreadyPassed, InvalidLogicalTime, FederateUnableToUseTime, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 4.18
    private native void federateSaveBegun_rti()
            throws SaveNotInitiated, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 4.19
    private native void federateSaveComplete_rti() throws FederateHasNotBegunSave, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 4.19
    private native void federateSaveNotComplete_rti() throws FederateHasNotBegunSave, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 4.21
    private native void abortFederationSave_rti()
            throws SaveNotInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 4.22
    private native void queryFederationSaveStatus_rti()
            throws RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 4.24
    private native void requestFederationRestore_rti(String label)
            throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 4.28
    private native void federateRestoreComplete_rti()
            throws RestoreNotRequested, SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 4.28
    private native void federateRestoreNotComplete_rti()
            throws RestoreNotRequested, SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 4.30
    private native void abortFederationRestore_rti()
            throws RestoreNotInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 4.31
    private native void queryFederationRestoreStatus_rti()
            throws SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    /////////////////////////////////////
    //Declaration Management Services //
    /////////////////////////////////////

    // 5.2
    private native void publishObjectClassAttributes_rti(ObjectClassHandle theClass, Object[] objects)
            throws AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 5.3
    private native void unpublishObjectClass_rti(ObjectClassHandle theClass)
            throws OwnershipAcquisitionPending, ObjectClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 5.3
    private native void unpublishObjectClassAttributes_rti(ObjectClassHandle theClass, AttributeHandleSet attributeList)
            throws OwnershipAcquisitionPending, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 5.4
    private native void publishInteractionClass_rti(InteractionClassHandle theInteraction)
            throws InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError;

    // 5.5
    private native void unpublishInteractionClass_rti(InteractionClassHandle theInteraction)
            throws InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError;

    // 5.6
    private native void subscribeObjectClassAttributes_rti(ObjectClassHandle theClass, Object[] objects)
            throws AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 5.6
    private native void subscribeObjectClassAttributes_rti(ObjectClassHandle theClass, AttributeHandleSet attributeList,
            String updateRateDesignator) throws AttributeNotDefined, ObjectClassNotDefined, InvalidUpdateRateDesignator,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 5.6
    private native void subscribeObjectClassAttributesPassively_rti(ObjectClassHandle theClass,
            AttributeHandleSet attributeList) throws AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 5.6
    private native void subscribeObjectClassAttributesPassively_rti(ObjectClassHandle theClass,
            AttributeHandleSet attributeList, String updateRateDesignator)
            throws AttributeNotDefined, ObjectClassNotDefined, InvalidUpdateRateDesignator, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 5.7
    private native void unsubscribeObjectClass_rti(ObjectClassHandle theClass) throws ObjectClassNotDefined,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 5.7
    private native void unsubscribeObjectClassAttributes_rti(ObjectClassHandle theClass,
            AttributeHandleSet attributeList) throws AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 5.8
    private native void subscribeInteractionClass_rti(InteractionClassHandle theClass)
            throws FederateServiceInvocationsAreBeingReportedViaMOM, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 5.8
    private native void subscribeInteractionClassPassively_rti(InteractionClassHandle theClass)
            throws FederateServiceInvocationsAreBeingReportedViaMOM, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 5.9
    private native void unsubscribeInteractionClass_rti(InteractionClassHandle theClass)
            throws InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError;

    ////////////////////////////////
    //Object Management Services //
    ////////////////////////////////

    // 6.2
    private native void reserveObjectInstanceName_rti(String theObjectName) throws IllegalName, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.4
    private native void releaseObjectInstanceName_rti(String theObjectInstanceName)
            throws ObjectInstanceNameNotReserved, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError;

    // 6.5
    private native void reserveMultipleObjectInstanceName_rti(Set<String> theObjectNames)
            throws IllegalName, NameSetWasEmpty, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError;

    // 6.7
    private native void releaseMultipleObjectInstanceName_rti(Set<String> theObjectNames)
            throws ObjectInstanceNameNotReserved, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError;

    // 6.8
    private native ObjectInstanceHandle registerObjectInstance_rti(ObjectClassHandle theClass)
            throws ObjectClassNotPublished, ObjectClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.8
    private native ObjectInstanceHandle registerObjectInstance_rti(ObjectClassHandle theClass, String theObjectName)
            throws ObjectInstanceNameInUse, ObjectInstanceNameNotReserved, ObjectClassNotPublished,
            ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected,
            RTIinternalError;

    // 6.10
    private native void updateAttributeValues_rti(ObjectInstanceHandle theObject, Object[] theAttributes,
            byte[] userSuppliedTag) throws AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.10
    private native MessageRetractionReturn updateAttributeValuesFloat64_rti(ObjectInstanceHandle theObject,
            Object[] array, byte[] userSuppliedTag, double value);

    private native MessageRetractionReturn updateAttributeValuesInteger64_rti(ObjectInstanceHandle theObject,
            Object[] array, byte[] userSuppliedTag, long value);

    // 6.12
    private native void sendInteraction_rti(InteractionClassHandle theInteraction, Object[] theParameters,
            byte[] userSuppliedTag)
            throws InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.12
    private native MessageRetractionReturn sendInteractionInteger64_rti(InteractionClassHandle theInteraction,
            Object[] theParameters, byte[] userSuppliedTag, long theTime) throws InvalidLogicalTime,
            InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.12
    private native MessageRetractionReturn sendInteractionFloat64_rti(InteractionClassHandle theInteraction,
            Object[] theParameters, byte[] userSuppliedTag, double theTime) throws InvalidLogicalTime,
            InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.14
    private native void deleteObjectInstance_rti(ObjectInstanceHandle objectHandle, byte[] userSuppliedTag)
            throws DeletePrivilegeNotHeld, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.14
    private native MessageRetractionReturn deleteObjectInstance_rti(ObjectInstanceHandle objectHandle,
            byte[] userSuppliedTag, LogicalTime theTime)
            throws InvalidLogicalTime, DeletePrivilegeNotHeld, ObjectInstanceNotKnown, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.16
    private native void localDeleteObjectInstance_rti(ObjectInstanceHandle objectHandle)
            throws OwnershipAcquisitionPending, FederateOwnsAttributes, ObjectInstanceNotKnown, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.19
    private native void requestAttributeValueUpdate_rti(ObjectInstanceHandle theObject, Object[] attributes,
            byte[] userSuppliedTag) throws AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.19
    private native void requestAttributeValueUpdate_rti(ObjectClassHandle theClass, Object[] attributes,
            byte[] userSuppliedTag) throws AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.23
    private native void requestAttributeTransportationTypeChange_rti(ObjectInstanceHandle theObject,
            AttributeHandleSet theAttributes, TransportationTypeHandle theType) throws AttributeAlreadyBeingChanged,
            AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, InvalidTransportationType, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.25
    private native void queryAttributeTransportationType_rti(ObjectInstanceHandle theObject,
            AttributeHandle theAttribute) throws AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.27
    private native void requestInteractionTransportationTypeChange_rti(InteractionClassHandle theClass,
            TransportationTypeHandle theType) throws InteractionClassAlreadyBeingChanged, InteractionClassNotPublished,
            InteractionClassNotDefined, InvalidTransportationType, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 6.29
    private native void queryInteractionTransportationType_rti(FederateHandle theFederate,
            InteractionClassHandle theInteraction) throws InteractionClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    ///////////////////////////////////
    //Ownership Management Services //
    ///////////////////////////////////

    // 7.2
    private native void unconditionalAttributeOwnershipDivestiture_rti(ObjectInstanceHandle theObject,
            AttributeHandleSet theAttributes) throws AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 7.3
    private native void negotiatedAttributeOwnershipDivestiture_rti(ObjectInstanceHandle theObject,
            AttributeHandleSet theAttributes, byte[] userSuppliedTag)
            throws AttributeAlreadyBeingDivested, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 7.6
    private native void confirmDivestiture_rti(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes,
            byte[] userSuppliedTag) throws NoAcquisitionPending, AttributeDivestitureWasNotRequested, AttributeNotOwned,
            AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError;

    // 7.8
    private native void attributeOwnershipAcquisition_rti(ObjectInstanceHandle theObject,
            AttributeHandleSet desiredAttributes, byte[] userSuppliedTag) throws AttributeNotPublished,
            ObjectClassNotPublished, FederateOwnsAttributes, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 7.9
    private native void attributeOwnershipAcquisitionIfAvailable_rti(ObjectInstanceHandle theObject,
            AttributeHandleSet desiredAttributes) throws AttributeAlreadyBeingAcquired, AttributeNotPublished,
            ObjectClassNotPublished, FederateOwnsAttributes, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 7.12
    private native void attributeOwnershipReleaseDenied_rti(ObjectInstanceHandle theObject,
            AttributeHandleSet theAttributes) throws AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 7.13
    private native AttributeHandleSet attributeOwnershipDivestitureIfWanted_rti(ObjectInstanceHandle theObject,
            AttributeHandleSet theAttributes) throws AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 7.14
    private native void cancelNegotiatedAttributeOwnershipDivestiture_rti(ObjectInstanceHandle theObject,
            AttributeHandleSet theAttributes)
            throws AttributeDivestitureWasNotRequested, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 7.15
    private native void cancelAttributeOwnershipAcquisition_rti(ObjectInstanceHandle theObject,
            AttributeHandleSet theAttributes) throws AttributeAcquisitionWasNotRequested, AttributeAlreadyOwned,
            AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember,
            NotConnected, RTIinternalError;

    // 7.17
    private native void queryAttributeOwnership_rti(ObjectInstanceHandle theObject, AttributeHandle theAttribute)
            throws AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 7.19
    private native boolean isAttributeOwnedByFederate_rti(ObjectInstanceHandle theObject, AttributeHandle theAttribute)
            throws AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    //////////////////////////////
    //Time Management Services //
    //////////////////////////////

    // 8.2
    private native void enableTimeRegulationInteger64_rti(long theLookahead) throws InvalidLookahead,
            InTimeAdvancingState, RequestForTimeRegulationPending, TimeRegulationAlreadyEnabled, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    private native void enableTimeRegulationFloat64_rti(double theLookahead) throws InvalidLookahead,
            InTimeAdvancingState, RequestForTimeRegulationPending, TimeRegulationAlreadyEnabled, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.4
    private native void disableTimeRegulation_rti() throws TimeRegulationIsNotEnabled, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.5
    private native void enableTimeConstrained_rti()
            throws InTimeAdvancingState, RequestForTimeConstrainedPending, TimeConstrainedAlreadyEnabled,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.7
    private native void disableTimeConstrained_rti() throws TimeConstrainedIsNotEnabled, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.8
    private native void timeAdvanceRequestInteger64_rti(long theTime) throws LogicalTimeAlreadyPassed,
            InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    private native void timeAdvanceRequestFloat64_rti(double theTime) throws LogicalTimeAlreadyPassed,
            InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.9
    private native void timeAdvanceRequestAvailable_rti(LogicalTime theTime) throws LogicalTimeAlreadyPassed,
            InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.10
    private native void nextMessageRequest_rti(LogicalTime theTime) throws LogicalTimeAlreadyPassed, InvalidLogicalTime,
            InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.11
    private native void nextMessageRequestAvailable_rti(LogicalTime theTime) throws LogicalTimeAlreadyPassed,
            InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.12
    private native void flushQueueRequest_rti(LogicalTime theTime) throws LogicalTimeAlreadyPassed, InvalidLogicalTime,
            InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.14
    private native void enableAsynchronousDelivery_rti() throws AsynchronousDeliveryAlreadyEnabled, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.15
    private native void disableAsynchronousDelivery_rti() throws AsynchronousDeliveryAlreadyDisabled, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.16
    private native TimeQueryReturn queryGALT_rti()
            throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.17
    private native LogicalTime queryLogicalTime_rti()
            throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.18
    private native TimeQueryReturn queryLITS_rti()
            throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.19
    private native void modifyLookahead_rti(LogicalTimeInterval theLookahead)
            throws InvalidLookahead, InTimeAdvancingState, TimeRegulationIsNotEnabled, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.20
    private native LogicalTimeInterval queryLookahead_rti() throws TimeRegulationIsNotEnabled, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.21
    private native void retract_rti(MessageRetractionHandle theHandle)
            throws MessageCanNoLongerBeRetracted, InvalidMessageRetractionHandle, TimeRegulationIsNotEnabled,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.23
    private native void changeAttributeOrderType_rti(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes,
            OrderType theType) throws AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 8.24
    private native void changeInteractionOrderType_rti(InteractionClassHandle theClass, OrderType theType)
            throws InteractionClassNotPublished, InteractionClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    //////////////////////////////////
    //Data Distribution Management //
    //////////////////////////////////

    // 9.2
    private native RegionHandle createRegion_rti(DimensionHandleSet dimensions) throws InvalidDimensionHandle,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.3
    private native void commitRegionModifications_rti(RegionHandleSet regions)
            throws RegionNotCreatedByThisFederate, InvalidRegion, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.4
    private native void deleteRegion_rti(RegionHandle theRegion)
            throws RegionInUseForUpdateOrSubscription, RegionNotCreatedByThisFederate, InvalidRegion, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    //9.5
    private native ObjectInstanceHandle registerObjectInstanceWithRegions_rti(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions)
            throws InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotPublished,
            ObjectClassNotPublished, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    //9.5
    private native ObjectInstanceHandle registerObjectInstanceWithRegions_rti(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions, String theObject) throws ObjectInstanceNameInUse,
            ObjectInstanceNameNotReserved, InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion,
            AttributeNotPublished, ObjectClassNotPublished, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.6
    private native void associateRegionsForUpdates_rti(ObjectInstanceHandle theObject,
            AttributeSetRegionSetPairList attributesAndRegions) throws InvalidRegionContext,
            RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.7
    private native void unassociateRegionsForUpdates_rti(ObjectInstanceHandle theObject,
            AttributeSetRegionSetPairList attributesAndRegions)
            throws RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectInstanceNotKnown,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.8
    private native void subscribeObjectClassAttributesWithRegions_rti(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions) throws InvalidRegionContext,
            RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.8
    private native void subscribeObjectClassAttributesWithRegions_rti(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions, String updateRateDesignator)
            throws InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined,
            ObjectClassNotDefined, InvalidUpdateRateDesignator, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.8
    private native void subscribeObjectClassAttributesPassivelyWithRegions_rti(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions) throws InvalidRegionContext,
            RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.8
    private native void subscribeObjectClassAttributesPassivelyWithRegions_rti(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions, String updateRateDesignator)
            throws InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined,
            ObjectClassNotDefined, InvalidUpdateRateDesignator, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.9
    private native void unsubscribeObjectClassAttributesWithRegions_rti(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions)
            throws RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.10
    private native void subscribeInteractionClassWithRegions_rti(InteractionClassHandle theClass,
            RegionHandleSet regions) throws FederateServiceInvocationsAreBeingReportedViaMOM, InvalidRegionContext,
            RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.10
    private native void subscribeInteractionClassPassivelyWithRegions_rti(InteractionClassHandle theClass,
            RegionHandleSet regions) throws FederateServiceInvocationsAreBeingReportedViaMOM, InvalidRegionContext,
            RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.11
    private native void unsubscribeInteractionClassWithRegions_rti(InteractionClassHandle theClass,
            RegionHandleSet regions) throws RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotDefined,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    //9.12
    private native void sendInteractionWithRegions_rti(InteractionClassHandle theInteraction,
            ParameterHandleValueMap theParameters, RegionHandleSet regions, byte[] userSuppliedTag)
            throws InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotPublished,
            InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    //9.12
    private native MessageRetractionReturn sendInteractionWithRegions_rti(InteractionClassHandle theInteraction,
            ParameterHandleValueMap theParameters, RegionHandleSet regions, byte[] userSuppliedTag, LogicalTime theTime)
            throws InvalidLogicalTime, InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion,
            InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 9.13
    private native void requestAttributeValueUpdateWithRegions_rti(ObjectClassHandle theClass,
            AttributeSetRegionSetPairList attributesAndRegions, byte[] userSuppliedTag) throws InvalidRegionContext,
            RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress,
            RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    //////////////////////////
    //RTI Support Services //
    //////////////////////////

    // 10.2
    private native ResignAction getAutomaticResignDirective_rti()
            throws FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.3
    private native void setAutomaticResignDirective_rti(ResignAction resignAction)
            throws InvalidResignAction, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.4
    private native FederateHandle getFederateHandle_rti(String theName)
            throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.5
    private native String getFederateName_rti(FederateHandle theHandle_rti) throws InvalidFederateHandle,
            FederateHandleNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.6
    private native ObjectClassHandle getObjectClassHandle_rti(String theName)
            throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.7
    private native String getObjectClassName_rti(ObjectClassHandle theHandle)
            throws InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.8
    private native ObjectClassHandle getKnownObjectClassHandle_rti(ObjectInstanceHandle theObject)
            throws ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.9
    private native ObjectInstanceHandle getObjectInstanceHandle_rti(String theName)
            throws ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.10
    private native String getObjectInstanceName_rti(ObjectInstanceHandle theHandle)
            throws ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.11
    private native AttributeHandle getAttributeHandle_rti(ObjectClassHandle whichClass, String theName)
            throws NameNotFound, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.12
    private native String getAttributeName_rti(ObjectClassHandle whichClass, AttributeHandle theHandle)
            throws AttributeNotDefined, InvalidAttributeHandle, InvalidObjectClassHandle, FederateNotExecutionMember,
            NotConnected, RTIinternalError;

    // 10.13
    private native double getUpdateRateValue_rti(String updateRateDesignator)
            throws InvalidUpdateRateDesignator, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.14
    private native double getUpdateRateValueForAttribute_rti(ObjectInstanceHandle theObject,
            AttributeHandle theAttribute) throws ObjectInstanceNotKnown, AttributeNotDefined,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.15
    private native InteractionClassHandle getInteractionClassHandle_rti(String theName)
            throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.16
    private native String getInteractionClassName_rti(InteractionClassHandle theHandle)
            throws InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.17
    private native ParameterHandle getParameterHandle_rti(InteractionClassHandle whichClass, String theName)
            throws NameNotFound, InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected,
            RTIinternalError;

    // 10.18
    private native String getParameterName_rti(InteractionClassHandle whichClass, ParameterHandle theHandle)
            throws InteractionParameterNotDefined, InvalidParameterHandle, InvalidInteractionClassHandle,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.19
    private native OrderType getOrderType_rti(String theName)
            throws InvalidOrderName, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.20
    private native String getOrderName_rti(OrderType theType)
            throws InvalidOrderType, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.21
    private native TransportationTypeHandle getTransportationTypeHandle_rti(String theName)
            throws InvalidTransportationName, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.22
    private native String getTransportationTypeName_rti(TransportationTypeHandle theHandle)
            throws InvalidTransportationType, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.23
    private native DimensionHandleSet getAvailableDimensionsForClassAttribute_rti(ObjectClassHandle whichClass,
            AttributeHandle theHandle) throws AttributeNotDefined, InvalidAttributeHandle, InvalidObjectClassHandle,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.24
    private native DimensionHandleSet getAvailableDimensionsForInteractionClass_rti(InteractionClassHandle theHandle)
            throws InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.25
    private native DimensionHandle getDimensionHandle_rti(String theName)
            throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.26
    private native String getDimensionName_rti(DimensionHandle theHandle)
            throws InvalidDimensionHandle, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.27
    private native long getDimensionUpperBound_rti(DimensionHandle theHandle)
            throws InvalidDimensionHandle, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.28
    private native DimensionHandleSet getDimensionHandleSet_rti(RegionHandle region) throws InvalidRegion,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.29
    private native RangeBounds getRangeBounds_rti(RegionHandle region, DimensionHandle dimension)
            throws RegionDoesNotContainSpecifiedDimension, InvalidRegion, SaveInProgress, RestoreInProgress,
            FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.30
    private native void setRangeBounds_rti(RegionHandle region, DimensionHandle dimension, RangeBounds bounds)
            throws InvalidRangeBound, RegionDoesNotContainSpecifiedDimension, RegionNotCreatedByThisFederate,
            InvalidRegion, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected,
            RTIinternalError;

    // 10.31
    private native long normalizeFederateHandle_rti(FederateHandle federateHandle)
            throws InvalidFederateHandle, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.32
    private native long normalizeServiceGroup_rti(ServiceGroup group)
            throws InvalidServiceGroup, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.33
    private native void enableObjectClassRelevanceAdvisorySwitch_rti() throws ObjectClassRelevanceAdvisorySwitchIsOn,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.34
    private native void disableObjectClassRelevanceAdvisorySwitch_rti() throws ObjectClassRelevanceAdvisorySwitchIsOff,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.35
    private native void enableAttributeRelevanceAdvisorySwitch_rti() throws AttributeRelevanceAdvisorySwitchIsOn,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.36
    private native void disableAttributeRelevanceAdvisorySwitch_rti() throws AttributeRelevanceAdvisorySwitchIsOff,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.37
    private native void enableAttributeScopeAdvisorySwitch_rti() throws AttributeScopeAdvisorySwitchIsOn,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.38
    private native void disableAttributeScopeAdvisorySwitch_rti() throws AttributeScopeAdvisorySwitchIsOff,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.39
    private native void enableInteractionRelevanceAdvisorySwitch_rti() throws InteractionRelevanceAdvisorySwitchIsOn,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.40
    private native void disableInteractionRelevanceAdvisorySwitch_rti() throws InteractionRelevanceAdvisorySwitchIsOff,
            SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError;

    // 10.41
    private native boolean evokeCallback_rti(double approximateMinimumTimeInSeconds)
            throws CallNotAllowedFromWithinCallback, RTIinternalError;

    // 10.42
    private native boolean evokeMultipleCallbacks_rti(double approximateMinimumTimeInSeconds,
            double approximateMaximumTimeInSeconds) throws CallNotAllowedFromWithinCallback, RTIinternalError;

    // 10.43
    private native void enableCallbacks_rti() throws SaveInProgress, RestoreInProgress, RTIinternalError;

    // 10.44
    private native void disableCallbacks_rti() throws SaveInProgress, RestoreInProgress, RTIinternalError;

    private boolean isFederateExecutionMember()
    {
        return federateExecutionMember;
    }

    private boolean isConnected()
    {
        return connected;
    }

    private void checkConnected() throws NotConnected
    {
        if (!isConnected())
        {
            throw new NotConnected(EMPTY_MESSAGE);
        }
    }
    private void checkFederateExecutionMember() throws FederateNotExecutionMember
    {
        if (!isFederateExecutionMember())
        {
            throw new FederateNotExecutionMember(EMPTY_MESSAGE);
        }
    }
}
