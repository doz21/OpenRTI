package hla.rti1516e.openrti;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import hla.rti1516e.RTIambassador;
import hla.rti1516e.RtiFactory;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.exceptions.RTIexception;

public class RtiFactoryImplTest
{
    private static RtiFactory factory;
    
    @BeforeClass
    public static void beforeClass() throws Exception
    {
        factory = RtiFactoryFactory.getRtiFactory();
    }
    
    @Test
    public void testRtiName()
    {
        assertTrue("OpenRTI".equals(factory.rtiName()));
    }
    
    @Test
    public void testRtiVersion()
    {
        assertTrue("0.9.0".equals(factory.rtiVersion()));
    }

    @Test
    public void testGetAmbassador() throws RTIexception
    {
        RTIambassador amb = factory.getRtiAmbassador();
        assertNotNull(amb);
    }

}
