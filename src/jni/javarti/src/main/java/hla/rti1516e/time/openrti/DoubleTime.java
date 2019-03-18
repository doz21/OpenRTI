
package hla.rti1516e.time.openrti;

import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

import hla.rti1516e.exceptions.CouldNotEncode;
import hla.rti1516e.exceptions.IllegalTimeArithmetic;
import hla.rti1516e.time.HLAfloat64Interval;
import hla.rti1516e.time.HLAfloat64Time;

public class DoubleTime implements HLAfloat64Time
{
    private static final long serialVersionUID = 3232335354963019971L;

    private static final int LENGTH = 8;

    private double time;

    public DoubleTime(double value)
    {
        this.time = value;
    }

    @Override
    public String toString()
    {
        return Double.toString(time);
    }

    @Override
    public boolean isInitial()
    {
        return this.time == 0.0;
    }

    @Override
    public boolean isFinal()
    {
        return this.time == Double.MAX_VALUE;
    }

    @Override
    public HLAfloat64Time add(HLAfloat64Interval interval) throws IllegalTimeArithmetic
    {
        return new DoubleTime(this.time + interval.getValue());
    }

    @Override
    public HLAfloat64Time subtract(HLAfloat64Interval interval) throws IllegalTimeArithmetic
    {
        return new DoubleTime(this.time - interval.getValue());
    }

    @Override
    public HLAfloat64Interval distance(HLAfloat64Time time)
    {
        return new DoubleTimeInterval(Math.abs(this.time - time.getValue()));
    }

    @Override
    public int compareTo(HLAfloat64Time other)
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
