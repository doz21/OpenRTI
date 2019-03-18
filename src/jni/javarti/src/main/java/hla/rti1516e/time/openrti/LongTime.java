package hla.rti1516e.time.openrti;

import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

import hla.rti1516e.exceptions.CouldNotEncode;
import hla.rti1516e.exceptions.IllegalTimeArithmetic;
import hla.rti1516e.time.HLAinteger64Interval;
import hla.rti1516e.time.HLAinteger64Time;

public class LongTime implements HLAinteger64Time
{
    private static final long serialVersionUID = -1940150130809705496L;

    private static final int LENGTH = 8;

    private long time;

    public LongTime(long value)
    {
        this.time = value;
    }

    public String toString()
    {
        return Long.toString(time);
    }

    public boolean isInitial()
    {
        return this.time == 0;
    }

    public boolean isFinal()
    {
        return this.time == Long.MAX_VALUE;
    }

    @Override
    public HLAinteger64Time add(HLAinteger64Interval interval) throws IllegalTimeArithmetic
    {
        return new LongTime(this.time + interval.getValue());
    }

    @Override
    public HLAinteger64Time subtract(HLAinteger64Interval interval) throws IllegalTimeArithmetic
    {
        return new LongTime(this.time - interval.getValue());
    }

    @Override
    public HLAinteger64Interval distance(HLAinteger64Time other)
    {
        return new LongTimeInterval(Math.abs(this.time - other.getValue()));
    }

    @Override
    public int compareTo(HLAinteger64Time other)
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
            buf.putLong(offset, this.time);
        }
        catch (IndexOutOfBoundsException | ReadOnlyBufferException ex)
        {
            throw new CouldNotEncode(ex.getMessage(), ex);
        }
    }

    @Override
    public long getValue()
    {
        return this.time;
    }
}
