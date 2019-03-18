package hla.rti1516e.time.openrti;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import hla.rti1516e.LogicalTimeFactoryFactory;

public class DoubleTimeFactoryTest
{

    @Test
    public void test()
    {
        assertNotNull(LogicalTimeFactoryFactory.getLogicalTimeFactory("HLAfloat64Time"));
    }

}
