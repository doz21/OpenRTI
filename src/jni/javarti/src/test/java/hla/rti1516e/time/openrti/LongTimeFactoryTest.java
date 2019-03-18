package hla.rti1516e.time.openrti;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import hla.rti1516e.LogicalTimeFactoryFactory;

public class LongTimeFactoryTest
{

    @Test
    public void testName()
    {
        assertNotNull(LogicalTimeFactoryFactory.getLogicalTimeFactory("HLAinteger64Time"));
    }
}
