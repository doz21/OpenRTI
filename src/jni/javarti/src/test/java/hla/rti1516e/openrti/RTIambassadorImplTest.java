package hla.rti1516e.openrti;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;

import org.hamcrest.core.Is;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.CallbackModel;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.MessageRetractionReturn;
import hla.rti1516e.NullFederateAmbassador;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.ParameterHandle;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.ResignAction;
import hla.rti1516e.RtiFactory;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.ConnectionFailed;
import hla.rti1516e.exceptions.FederateIsExecutionMember;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectInstanceNameNotReserved;
import hla.rti1516e.exceptions.RTIexception;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RequestForTimeConstrainedPending;
import hla.rti1516e.exceptions.UnsupportedCallbackModel;
import hla.rti1516e.time.openrti.DoubleTime;
import hla.rti1516e.time.openrti.DoubleTimeInterval;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RTIambassadorImplTest
{
    private static RtiFactory factory;

    private static final URL FOM = getTestResource("manager.xml");
    private static final URL MIM = getTestResource("HLAstandardMIM.xml");

    private static URL getTestResource(String name)
    {
        return Thread.currentThread().getContextClassLoader().getResource(name);
    }

    @BeforeClass
    public static void beforeClass() throws Exception
    {
        factory = RtiFactoryFactory.getRtiFactory();
    }

    @Test(expected = ConnectionFailed.class)
    public void willFailedConnectFederateAmbassadorCallbackModelString() throws RTIexception
    {
        RTIambassador amb = factory.getRtiAmbassador();
        assertNotNull(amb);
        FederateAmbassador fed = new NullFederateAmbassador();
        amb.connect(fed, CallbackModel.HLA_EVOKED, "rti://127.0.0.1:123452");
    }

    @Test(expected = UnsupportedCallbackModel.class)
    public void willFailedUnsupportedCallbackModelFederateAmbassadorCallbackModelString() throws RTIexception
    {
        RTIambassador amb = factory.getRtiAmbassador();
        assertNotNull(amb);
        FederateAmbassador fed = new NullFederateAmbassador();
        amb.connect(fed, CallbackModel.HLA_IMMEDIATE, "thread://");
    }

    @Test
    public void willConnectFederateAmbassadorCallbackModelString() throws RTIexception
    {
        RTIambassador amb = factory.getRtiAmbassador();
        assertNotNull(amb);
        FederateAmbassador fed = new NullFederateAmbassador();
        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.disconnect();
    }

    @Test
    public void testConnectFederateAmbassadorCallbackModel()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testDisconnect() throws FederateIsExecutionMember, CallNotAllowedFromWithinCallback, RTIinternalError
    {
        RTIambassador amb = factory.getRtiAmbassador();
        amb.disconnect();
    }

    @Test
    public void testCreateFederationExecutionStringURLArrayURLString()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testCreateFederationExecutionStringURLArrayString()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testCreateFederationExecutionStringURLArrayURL()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testCreateFederationExecutionStringURLArray()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testCreateFederationExecutionStringURL()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testDestroyFederationExecution() throws MalformedURLException, RTIexception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testDestroyFederationExecution";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);
        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testListFederationExecutions()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testJoinFederationExecutionStringStringStringURLArray() throws RTIexception, MalformedURLException
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);

        String federationExecutionName = "testJoinFederationExecutionStringStringStringURLArray";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);
        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testJoinFederationExecutionStringStringURLArray()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testJoinFederationExecutionStringStringString()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testJoinFederationExecutionStringString()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testResignFederationExecution() throws MalformedURLException, RTIexception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution("testResignFederationExecution", foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", "testResignFederationExecution", foms);
        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.disconnect();
    }

    @Test
    public void testRegisterFederationSynchronizationPointStringByteArray()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testRegisterFederationSynchronizationPointStringByteArrayFederateHandleSet()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSynchronizationPointAchievedString()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSynchronizationPointAchievedStringBoolean()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testRequestFederationSaveString()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testRequestFederationSaveStringLogicalTime()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testFederateSaveBegun()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testFederateSaveComplete()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testFederateSaveNotComplete()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testAbortFederationSave()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testQueryFederationSaveStatus()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testRequestFederationRestore()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testFederateRestoreComplete()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testFederateRestoreNotComplete()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testAbortFederationRestore()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testQueryFederationRestoreStatus()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testPublishObjectClassAttributes() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testPublishObjectClassAttributes";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        AttributeHandleSet attrubutes = amb.getAttributeHandleSetFactory().create();
        attrubutes.add(attributeHandle);
        amb.publishObjectClassAttributes(objectClassHandle, attrubutes);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testUnpublishObjectClass()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testUnpublishObjectClassAttributes()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testPublishInteractionClass() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testPublishInteractionClass";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        InteractionClassHandle interaction = amb.getInteractionClassHandle("Start");
        amb.publishInteractionClass(interaction);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testUnpublishInteractionClass()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSubscribeObjectClassAttributesObjectClassHandleAttributeHandleSet() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        TestAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testSubscribeObjectClassAttributesObjectClassHandleAttributeHandleSet";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        AttributeHandleSet attrubutes = amb.getAttributeHandleSetFactory().create();
        attrubutes.add(attributeHandle);
        amb.subscribeObjectClassAttributes(objectClassHandle, attrubutes);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testSubscribeObjectClassAttributesObjectClassHandleAttributeHandleSetString()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSubscribeObjectClassAttributesPassivelyObjectClassHandleAttributeHandleSet()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSubscribeObjectClassAttributesPassivelyObjectClassHandleAttributeHandleSetString()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testUnsubscribeObjectClass()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testUnsubscribeObjectClassAttributes()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSubscribeInteractionClass() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testSubscribeInteractionClass";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        InteractionClassHandle handle = amb.getInteractionClassHandle("Start");
        amb.subscribeInteractionClass(handle);
        assertNotNull(handle);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testSubscribeInteractionClassPassively()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testUnsubscribeInteractionClass()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testReserveObjectInstanceName()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testReleaseObjectInstanceName()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testReserveMultipleObjectInstanceName()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testReleaseMultipleObjectInstanceName()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testRegisterObjectInstanceObjectClassHandle() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testRegisterObjectInstanceObjectClassHandle";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        AttributeHandleSet attrubutes = amb.getAttributeHandleSetFactory().create();
        attrubutes.add(attributeHandle);
        amb.publishObjectClassAttributes(objectClassHandle, attrubutes);

        ObjectInstanceHandle objectInstance = amb.registerObjectInstance(objectClassHandle);
        assertNotNull(objectInstance);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test(expected = ObjectInstanceNameNotReserved.class)
    public void testRegisterObjectInstanceObjectClassHandleString() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testRegisterObjectInstanceObjectClassHandleString";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        AttributeHandleSet attrubutes = amb.getAttributeHandleSetFactory().create();
        attrubutes.add(attributeHandle);
        amb.publishObjectClassAttributes(objectClassHandle, attrubutes);

        ObjectInstanceHandle objectInstance = amb.registerObjectInstance(objectClassHandle, "dummy");
        assertNotNull(objectInstance);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testUpdateAttributeValuesObjectInstanceHandleAttributeHandleValueMapByteArray() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testUpdateAttributeValuesObjectInstanceHandleAttributeHandleValueMapByteArray";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        AttributeHandleSet attrubutes = amb.getAttributeHandleSetFactory().create();
        attrubutes.add(attributeHandle);
        amb.publishObjectClassAttributes(objectClassHandle, attrubutes);

        ObjectInstanceHandle objectInstance = amb.registerObjectInstance(objectClassHandle);

        AttributeHandleValueMap map = amb.getAttributeHandleValueMapFactory().create(1);
        byte[] b =
        { 0 };
        map.put(attributeHandle, b);
        amb.updateAttributeValues(objectInstance, map, null);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testUpdateAttributeValuesObjectInstanceHandleAttributeHandleValueMapByteArrayLogicalTime()
            throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testUpdateAttributeValuesObjectInstanceHandleAttributeHandleValueMapByteArrayLogicalTime";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        AttributeHandleSet attrubutes = amb.getAttributeHandleSetFactory().create();
        attrubutes.add(attributeHandle);
        amb.publishObjectClassAttributes(objectClassHandle, attrubutes);

        ObjectInstanceHandle objectInstance = amb.registerObjectInstance(objectClassHandle);

        AttributeHandleValueMap map = amb.getAttributeHandleValueMapFactory().create(1);
        byte[] b =
        { 0 };
        map.put(attributeHandle, b);
        MessageRetractionReturn ret = amb.updateAttributeValues(objectInstance, map, null, new DoubleTime(0.03));
        // TODO
        assertNull(ret);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testSendInteractionInteractionClassHandleParameterHandleValueMapByteArray() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testSendInteractionInteractionClassHandleParameterHandleValueMapByteArray";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        InteractionClassHandle interaction = amb.getInteractionClassHandle("Start");
        ParameterHandle parameterHandle = amb.getParameterHandle(interaction, "Target");

        amb.publishInteractionClass(interaction);

        ParameterHandleValueMap map = amb.getParameterHandleValueMapFactory().create(1);
        byte[] b =
        { 0 };
        map.put(parameterHandle, b);
        amb.sendInteraction(interaction, map, null);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testSendInteractionInteractionClassHandleParameterHandleValueMapByteArrayLogicalTime() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testSendInteractionInteractionClassHandleParameterHandleValueMapByteArrayLogicalTime";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        InteractionClassHandle interaction = amb.getInteractionClassHandle("Start");
        ParameterHandle parameterHandle = amb.getParameterHandle(interaction, "Target");

        amb.publishInteractionClass(interaction);

        ParameterHandleValueMap map = amb.getParameterHandleValueMapFactory().create(1);
        byte[] b =
        { 0 };
        map.put(parameterHandle, b);
        amb.sendInteraction(interaction, map, null, new DoubleTime(0.03));

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testDeleteObjectInstanceObjectInstanceHandleByteArray()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteObjectInstanceObjectInstanceHandleByteArrayLogicalTime()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testLocalDeleteObjectInstance()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testRequestAttributeValueUpdateObjectInstanceHandleAttributeHandleSetByteArray() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testRequestAttributeValueUpdateObjectInstanceHandleAttributeHandleSetByteArray";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        AttributeHandleSet attrubutes = amb.getAttributeHandleSetFactory().create();
        attrubutes.add(attributeHandle);

        amb.publishObjectClassAttributes(objectClassHandle, attrubutes);

        ObjectInstanceHandle objectInstance = amb.registerObjectInstance(objectClassHandle);

        amb.requestAttributeValueUpdate(objectInstance, attrubutes, null);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testRequestAttributeValueUpdateObjectClassHandleAttributeHandleSetByteArray() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testRequestAttributeValueUpdateObjectClassHandleAttributeHandleSetByteArray";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        AttributeHandleSet attrubutes = amb.getAttributeHandleSetFactory().create();
        attrubutes.add(attributeHandle);

        amb.requestAttributeValueUpdate(objectClassHandle, attrubutes, null);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testRequestAttributeTransportationTypeChange()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testQueryAttributeTransportationType()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testRequestInteractionTransportationTypeChange()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testQueryInteractionTransportationType()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testUnconditionalAttributeOwnershipDivestiture()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testNegotiatedAttributeOwnershipDivestiture()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testConfirmDivestiture()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testAttributeOwnershipAcquisition()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testAttributeOwnershipAcquisitionIfAvailable()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testAttributeOwnershipReleaseDenied()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testAttributeOwnershipDivestitureIfWanted()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testCancelNegotiatedAttributeOwnershipDivestiture()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testCancelAttributeOwnershipAcquisition()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testQueryAttributeOwnership()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testIsAttributeOwnedByFederate()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEnableTimeRegulation() throws Exception
    {
        URL[] foms =
        { FOM.toURI().toURL() };
        RTIambassador amb = factory.getRtiAmbassador();
        TestAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testEnableTimeRegulation";

        assertFalse(fed.isTimeRegulationEnabled());
        assertNull(fed.timeRegulation);

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM.toURI().toURL(), "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);
        amb.enableTimeRegulation(new DoubleTimeInterval(0.1));
        amb.evokeMultipleCallbacks(0.1, 0.2);
        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();

        assertTrue(fed.isTimeRegulationEnabled());
        assertTrue(fed.timeRegulation.compareTo(new DoubleTime(0.0)) == 0);
    }

    @Test
    public void testDisableTimeRegulation() throws Exception
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEnableTimeConstrained() throws Exception
    {
        URL[] foms =
        { FOM.toURI().toURL() };
        RTIambassador amb = factory.getRtiAmbassador();
        TestAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testEnableTimeConstrained";

        assertFalse(fed.isTimeConstrainedEnabled());
        assertNull(fed.timeConstrained);

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM.toURI().toURL(), "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);
        amb.enableTimeConstrained();
        amb.evokeMultipleCallbacks(0.1, 0.2);
        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();

        assertTrue(fed.isTimeConstrainedEnabled());
        assertTrue(fed.timeConstrained.compareTo(new DoubleTime(0.0)) == 0);
    }

    @Test
    public void testDisableTimeConstrained()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testTimeAdvanceRequest() throws Exception
    {
        URL[] foms =
        { FOM.toURI().toURL() };
        RTIambassador amb = factory.getRtiAmbassador();
        TestAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testTimeAdvanceRequest";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM.toURI().toURL(), "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        amb.timeAdvanceRequest(fed.federateTime.add(new DoubleTimeInterval(0.1)));
        amb.evokeMultipleCallbacks(0.1, 0.2);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();

        assertTrue(fed.federateTime.compareTo(new DoubleTime(0.1)) == 0);
    }

    @Test(expected = RequestForTimeConstrainedPending.class)
    public void willThrowRequestForTimeConstrainedPending() throws Exception
    {
        URL[] foms =
        { FOM.toURI().toURL() };
        RTIambassador amb = factory.getRtiAmbassador();
        TestAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "willThrowRequestForTimeConstrainedPendingt";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM.toURI().toURL(), "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        amb.enableTimeConstrained();

        amb.timeAdvanceRequest(fed.federateTime.add(new DoubleTimeInterval(0.1)));
        amb.evokeMultipleCallbacks(0.1, 0.2);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();

        assertTrue(fed.federateTime.compareTo(new DoubleTime(0.1)) == 0);
    }

    @Test
    public void testTimeAdvanceRequestAvailable()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testNextMessageRequest()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testNextMessageRequestAvailable()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testFlushQueueRequest()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEnableAsynchronousDelivery()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testDisableAsynchronousDelivery()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testQueryGALT()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testQueryLogicalTime()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testQueryLITS()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testModifyLookahead()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testQueryLookahead()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testRetract()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testChangeAttributeOrderType()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testChangeInteractionOrderType()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testCreateRegion()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testCommitRegionModifications()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteRegion()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testRegisterObjectInstanceWithRegionsObjectClassHandleAttributeSetRegionSetPairList()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testRegisterObjectInstanceWithRegionsObjectClassHandleAttributeSetRegionSetPairListString()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testAssociateRegionsForUpdates()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testUnassociateRegionsForUpdates()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSubscribeObjectClassAttributesWithRegionsObjectClassHandleAttributeSetRegionSetPairList()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSubscribeObjectClassAttributesWithRegionsObjectClassHandleAttributeSetRegionSetPairListString()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSubscribeObjectClassAttributesPassivelyWithRegionsObjectClassHandleAttributeSetRegionSetPairList()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSubscribeObjectClassAttributesPassivelyWithRegionsObjectClassHandleAttributeSetRegionSetPairListString()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testUnsubscribeObjectClassAttributesWithRegions()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSubscribeInteractionClassWithRegions()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSubscribeInteractionClassPassivelyWithRegions()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testUnsubscribeInteractionClassWithRegions()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSendInteractionWithRegionsInteractionClassHandleParameterHandleValueMapRegionHandleSetByteArray()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSendInteractionWithRegionsInteractionClassHandleParameterHandleValueMapRegionHandleSetByteArrayLogicalTime()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testRequestAttributeValueUpdateWithRegions()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAutomaticResignDirective()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSetAutomaticResignDirective()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetFederateHandle()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetFederateName()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetObjectClassHandle() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testGetObjectClassHandle";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        ObjectClassHandle handle = amb.getObjectClassHandle("SimLabFederate");
        assertNotNull(handle);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetObjectClassName() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testGetObjectClassHandle";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        ObjectClassHandle handle = amb.getObjectClassHandle("SimLabFederate");
        String name = amb.getObjectClassName(handle);

        assertTrue("SimLabFederate".equals(name));

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetKnownObjectClassHandle()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetObjectInstanceHandle()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetObjectInstanceName() throws Exception
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAttributeHandle() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testGetAttributeHandle";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");

        assertNotNull(attributeHandle);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetAttributeName() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testGetAttributeName";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        String name = amb.getAttributeName(objectClassHandle, attributeHandle);

        assertTrue("FederateName".equals(name));

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetUpdateRateValue()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetUpdateRateValueForAttribute()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetInteractionClassHandle() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testGetInteractionClassHandle";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        InteractionClassHandle handle = amb.getInteractionClassHandle("Start");

        assertNotNull(handle);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetInteractionClassName() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testGetInteractionClassName";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        InteractionClassHandle handle = amb.getInteractionClassHandle("Start");
        String name = amb.getInteractionClassName(handle);

        assertTrue("Start".equals(name));

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetParameterHandle() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "testGetParameterHandle";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        InteractionClassHandle interaction = amb.getInteractionClassHandle("Start");
        ParameterHandle handle = amb.getParameterHandle(interaction, "Target");

        assertNotNull(handle);

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetParameterName()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetOrderType()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetOrderName()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetTransportationTypeHandle()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetTransportationTypeName()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAvailableDimensionsForClassAttribute()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAvailableDimensionsForInteractionClass()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetDimensionHandle()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetDimensionName()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetDimensionUpperBound()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetDimensionHandleSet()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetRangeBounds()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSetRangeBounds()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testNormalizeFederateHandle()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testNormalizeServiceGroup()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEnableObjectClassRelevanceAdvisorySwitch()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testDisableObjectClassRelevanceAdvisorySwitch()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEnableAttributeRelevanceAdvisorySwitch()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testDisableAttributeRelevanceAdvisorySwitch()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEnableAttributeScopeAdvisorySwitch()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testDisableAttributeScopeAdvisorySwitch()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEnableInteractionRelevanceAdvisorySwitch()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testDisableInteractionRelevanceAdvisorySwitch()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEvokeCallback()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEvokeMultipleCallbacks()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testEnableCallbacks()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testDisableCallbacks()
    {
        fail("Not yet implemented");
    }

    @Test(expected = NotConnected.class)
    public void testGetAttributeHandleFactoryDisconnected() throws Exception
    {
        RTIambassador amb = factory.getRtiAmbassador();

        assertNotNull(amb.getAttributeHandleFactory());
    }

    @Test(expected = FederateNotExecutionMember.class)
    public void testGetAttributeHandleFactoryNotJoined() throws Exception
    {
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new NullFederateAmbassador();

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");

        assertNotNull(amb.getAttributeHandleFactory());

        amb.disconnect();
    }

    @Test
    public void testGetAttributeHandleFactory() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new NullFederateAmbassador();
        String federationExecutionName = "testGetAttributeHandleFactory";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        assertNotNull(amb.getAttributeHandleFactory());

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetAttributeHandleSetFactory() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new NullFederateAmbassador();
        String federationExecutionName = "testGetAttributeHandleSetFactory";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        assertNotNull(amb.getAttributeHandleSetFactory());

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetAttributeHandleValueMapFactory() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new NullFederateAmbassador();
        String federationExecutionName = "testGetAttributeHandleValueMapFactory";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        assertNotNull(amb.getAttributeHandleValueMapFactory());

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetAttributeSetRegionSetPairListFactory() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new NullFederateAmbassador();
        String federationExecutionName = "testGetAttributeSetRegionSetPairListFactory";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        assertNotNull(amb.getAttributeSetRegionSetPairListFactory());

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetDimensionHandleFactory() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new NullFederateAmbassador();
        String federationExecutionName = "testGetDimensionHandleFactory";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        assertNotNull(amb.getDimensionHandleFactory());

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetDimensionHandleSetFactory() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new NullFederateAmbassador();
        String federationExecutionName = "testGetDimensionHandleSetFactory";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        assertNotNull(amb.getDimensionHandleSetFactory());

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetFederateHandleFactory()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetFederateHandleSetFactory()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetInteractionClassHandleFactory()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetObjectClassHandleFactory()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetObjectInstanceHandleFactory()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetParameterHandleFactory()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetParameterHandleValueMapFactory() throws Exception
    {
        URL[] foms =
        { FOM };
        RTIambassador amb = factory.getRtiAmbassador();
        FederateAmbassador fed = new NullFederateAmbassador();
        String federationExecutionName = "testGetParameterHandleValueMapFactory";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "thread://");
        amb.createFederationExecution(federationExecutionName, foms, MIM, "");
        amb.joinFederationExecution("federateName", "federateType", federationExecutionName, foms);

        assertNotNull(amb.getParameterHandleValueMapFactory());

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.destroyFederationExecution(federationExecutionName);
        amb.disconnect();
    }

    @Test
    public void testGetRegionHandleSetFactory()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetTransportationTypeHandleFactory()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testGetHLAversion() throws Exception
    {
        RTIambassador amb = factory.getRtiAmbassador();
        assertThat(amb.getHLAversion(), Is.is("2010.1"));
    }

    @Test
    public void testGetTimeFactory()
    {
        fail("Not yet implemented");
    }

}
