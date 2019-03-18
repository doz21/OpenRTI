
package hla.rti1516e.time.openrti;

import java.nio.ByteBuffer;

import hla.rti1516e.exceptions.CouldNotDecode;
import hla.rti1516e.time.HLAfloat64Interval;
import hla.rti1516e.time.HLAfloat64Time;
import hla.rti1516e.time.HLAfloat64TimeFactory;

public class DoubleTimeFactory implements HLAfloat64TimeFactory
{
    private static final long serialVersionUID = 7090300589411827676L;

    private static final String NAME = "HLAfloat64Time";
    
    private static final Double EPSILON = Double.longBitsToDouble(971l << 52);

    @Override
    public HLAfloat64Time decodeTime(byte[] buffer, int offset) throws CouldNotDecode
    {
        try
        {
            ByteBuffer buf = ByteBuffer.wrap(buffer);
            return new DoubleTime(buf.getDouble(offset));
        }
        catch (IndexOutOfBoundsException ex)
        {
            throw new CouldNotDecode(ex.getMessage(), ex);
        }
    }

    @Override
    public HLAfloat64Interval decodeInterval(byte[] buffer, int offset) throws CouldNotDecode
    {
        try
        {
            ByteBuffer buf = ByteBuffer.wrap(buffer);
            return new DoubleTimeInterval(buf.getDouble(offset));
        }
        catch (IndexOutOfBoundsException ex)
        {
            throw new CouldNotDecode(ex.getMessage(), ex);
        }
    }

    @Override
    public HLAfloat64Time makeInitial()
    {
        return new DoubleTime(0.0);
    }

    @Override
    public HLAfloat64Time makeFinal()
    {
        return new DoubleTime(Double.MAX_VALUE);
    }

    @Override
    public HLAfloat64Time makeTime(double value)
    {
        return new DoubleTime(value);
    }

    @Override
    public HLAfloat64Interval makeZero()
    {
        return new DoubleTimeInterval(0.0);
    }

    @Override
    public HLAfloat64Interval makeEpsilon()
    {
        //Math.ulp(1.0)
        // Nach  IEEE 754 
        return new DoubleTimeInterval(EPSILON);
    }

    @Override
    public HLAfloat64Interval makeInterval(double value)
    {
        return new DoubleTimeInterval(value);
    }

    @Override
    public String getName()
    {
        return NAME;
    }
}
