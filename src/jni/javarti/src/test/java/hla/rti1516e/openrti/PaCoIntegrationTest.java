package hla.rti1516e.openrti;

import static org.hamcrest.MatcherAssert.assertThat;

import java.net.URL;

import org.hamcrest.core.Is;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.CallbackModel;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.ResignAction;
import hla.rti1516e.RtiFactory;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.HLAunicodeString;
import vwg.simlab.junit.IntegrationTest;

@Category(IntegrationTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaCoIntegrationTest
{
    private static RtiFactory factory;

    private static final URL MANAGER = getTestResource("manager.xml");

    private static URL getTestResource(String name)
    {
        return Thread.currentThread().getContextClassLoader().getResource(name);
    }

    @BeforeClass
    public static void beforeClass() throws Exception
    {
        factory = RtiFactoryFactory.getRtiFactory();
    }

    @Test
    // see info in manager window
    public void test_2_UpdateObject() throws Exception
    {
        URL[] foms =
        { MANAGER };
        RTIambassador amb = factory.getRtiAmbassador();
        TestAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "ParkCoordinator";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "rti://127.0.0.1:12345");
        amb.joinFederationExecution("testUpdateObject", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        AttributeHandleSet attrubutes = amb.getAttributeHandleSetFactory().create();
        attrubutes.add(attributeHandle);
        amb.publishObjectClassAttributes(objectClassHandle, attrubutes);
        amb.subscribeObjectClassAttributes(objectClassHandle, attrubutes);

        ObjectInstanceHandle ich = amb.registerObjectInstance(objectClassHandle);

        // update
        AttributeHandleValueMap map = amb.getAttributeHandleValueMapFactory().create(1);
        HLAunicodeString val = factory.getEncoderFactory().createHLAunicodeString("testUpdateObject");
        map.put(attributeHandle, val.toByteArray());
        amb.updateAttributeValues(ich, map, null);

        // discovery
        for (int i = 0; i < 5; i++)
        {
            amb.evokeMultipleCallbacks(0.1, 0.2);
        }
        assertThat(fed.objectInstances.size(), Is.is(1));
        assertThat(fed.objectName, Is.is("Manager"));

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.disconnect();

    }

    @Test
    // press s in manager window wenn running
    public void test_2_RecieveInteraction() throws Exception
    {
        URL[] foms =
        { MANAGER };
        RTIambassador amb = factory.getRtiAmbassador();
        TestAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "ParkCoordinator";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "rti://127.0.0.1:12345");
        amb.joinFederationExecution("testInteractionFederate", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        AttributeHandleSet attrubutes = amb.getAttributeHandleSetFactory().create();
        attrubutes.add(attributeHandle);
        amb.publishObjectClassAttributes(objectClassHandle, attrubutes);

        InteractionClassHandle interaction = amb.getInteractionClassHandle("Start");
        amb.subscribeInteractionClass(interaction);

        ObjectInstanceHandle ich = amb.registerObjectInstance(objectClassHandle);

        AttributeHandleValueMap map = amb.getAttributeHandleValueMapFactory().create(1);
        HLAunicodeString val = factory.getEncoderFactory().createHLAunicodeString("testInteraction");
        map.put(attributeHandle, val.toByteArray());
        amb.updateAttributeValues(ich, map, null);

        for (int i = 0; i < 50; i++)
        {
            amb.evokeMultipleCallbacks(0.1, 0.2);
        }

        assertThat(fed.interactionTarget, Is.is("testInteraction"));

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.disconnect();

    }

    @Test
    // press q in manager window wenn running
    public void test_9_RemoveObject() throws Exception
    {
        URL[] foms =
        { MANAGER };
        RTIambassador amb = factory.getRtiAmbassador();
        TestAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "ParkCoordinator";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "rti://127.0.0.1:12345");
        amb.joinFederationExecution("testRemoveObject", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        AttributeHandleSet attrubutes = amb.getAttributeHandleSetFactory().create();
        attrubutes.add(attributeHandle);
        amb.subscribeObjectClassAttributes(objectClassHandle, attrubutes);

        // discovery
        for (int i = 0; i < 5; i++)
        {
            amb.evokeMultipleCallbacks(0.1, 0.2);
        }
        assertThat(fed.objectInstances.size(), Is.is(1));

        // Remove
        for (int i = 0; i < 50; i++)
        {
            amb.evokeMultipleCallbacks(0.1, 0.2);
        }
        assertThat(fed.objectInstances.size(), Is.is(0));

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.disconnect();

    }

    @Test
    public void test_1_DiscoveryObject() throws Exception
    {
        URL[] foms =
        { MANAGER };
        RTIambassador amb = factory.getRtiAmbassador();
        TestAmbassador fed = new TestAmbassador(amb);
        String federationExecutionName = "ParkCoordinator";

        amb.connect(fed, CallbackModel.HLA_EVOKED, "rti://127.0.0.1:12345");
        amb.joinFederationExecution("testDiscoveryObject", "federateType", federationExecutionName, foms);

        ObjectClassHandle objectClassHandle = amb.getObjectClassHandle("SimLabFederate");
        AttributeHandle attributeHandle = amb.getAttributeHandle(objectClassHandle, "FederateName");
        AttributeHandleSet attrubutes = amb.getAttributeHandleSetFactory().create();
        attrubutes.add(attributeHandle);
        amb.subscribeObjectClassAttributes(objectClassHandle, attrubutes);

        // discovery
        for (int i = 0; i < 5; i++)
        {
            amb.evokeMultipleCallbacks(0.1, 0.2);
        }
        assertThat(fed.objectInstances.size(), Is.is(1));

        amb.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
        amb.disconnect();
    }

}
