package hla.rti1516e.time.openrti;

import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

import hla.rti1516e.exceptions.CouldNotEncode;
import hla.rti1516e.exceptions.IllegalTimeArithmetic;
import hla.rti1516e.exceptions.InvalidLogicalTimeInterval;
import hla.rti1516e.time.HLAfloat64Interval;

public class DoubleTimeInterval implements HLAfloat64Interval
{
    private static final long serialVersionUID = 2991199598089503271L;

    private static final int LENGTH = 8;

    private double time;

    public DoubleTimeInterval(double time)
    {
        this.time = time;
    }

    @Override
    public String toString()
    {
        return Double.toString(time);
    }

    @Override
    public boolean isZero()
    {
        return this.time == 0.0;
    }

    @Override
    public boolean isEpsilon()
    {
        return false;
    }

    @Override
    public HLAfloat64Interval add(HLAfloat64Interval interval) throws IllegalTimeArithmetic, InvalidLogicalTimeInterval
    {
        return new DoubleTimeInterval(this.time + interval.getValue());
    }

    @Override
    public HLAfloat64Interval subtract(HLAfloat64Interval interval)
            throws IllegalTimeArithmetic, InvalidLogicalTimeInterval
    {
        return new DoubleTimeInterval(this.time - interval.getValue());
    }

    @Override
    public int compareTo(HLAfloat64Interval other)
    {
        double otherTime = other.getValue();
        if (this.time == otherTime)
            return 0;
        else if (this.time > otherTime)
            return 1;
        else
            return -1;
    }

    @Override
    public int encodedLength()
    {
        return LENGTH;
    }

    @Override
    public void encode(byte[] buffer, int offset) throws CouldNotEncode
    {
        try
        {
            ByteBuffer buf = ByteBuffer.wrap(buffer);
            buf.putDouble(offset, this.time);
        }
        catch (IndexOutOfBoundsException | ReadOnlyBufferException ex)
        {
            throw new CouldNotEncode(ex.getMessage(), ex);
        }
    }

    @Override
    public double getValue()
    {
        return this.time;
    }
}
