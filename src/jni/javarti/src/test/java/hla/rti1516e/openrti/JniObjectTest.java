package hla.rti1516e.openrti;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.exceptions.RTIinternalError;

public class JniObjectTest
{
    private static final long NULL = 0L;
    
    
    @Test
    public void willDispose() throws Exception
    {
        JniObject obj = (JniObject) RtiFactoryFactory.getRtiFactory().getRtiAmbassador();
        assertThat(obj.getPtr(), IsNot.not(NULL));
        
        obj.dispose();
        assertThat(obj.getPtr(), Is.is(NULL));
    }
    
    @Test(expected = RTIinternalError.class)
    public void willThrowRTIinternalError() throws Exception
    {
        JniObject obj = new JniObject()
        {
        };
        obj.dispose();
    }

}
