
package hla.rti1516e.time.openrti;

import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

import hla.rti1516e.exceptions.CouldNotEncode;
import hla.rti1516e.exceptions.IllegalTimeArithmetic;
import hla.rti1516e.exceptions.InvalidLogicalTimeInterval;
import hla.rti1516e.time.HLAinteger64Interval;


public class LongTimeInterval implements HLAinteger64Interval
{
    private static final long serialVersionUID = -8359290083695957835L;
    
    private static final int LENGTH = 8;
    
    private long time;

    public LongTimeInterval(long time)
    {
        this.time = time;
    }

    @Override
    public String toString()
    {
        return Long.toString(time);
    }

    @Override
    public boolean isZero()
    {
        return this.time == 0;
    }

    @Override
    public boolean isEpsilon()
    {
        return false;
    }

    @Override
    public HLAinteger64Interval add(HLAinteger64Interval interval)
            throws IllegalTimeArithmetic, InvalidLogicalTimeInterval
    {
        return new LongTimeInterval(this.time + interval.getValue());
    }

    @Override
    public HLAinteger64Interval subtract(HLAinteger64Interval interval)
            throws IllegalTimeArithmetic, InvalidLogicalTimeInterval
    {
        return new LongTimeInterval(this.time - interval.getValue());
    }

    public int compareTo(HLAinteger64Interval other)
    {
        double otherTime = other.getValue();
        if (this.time == otherTime)
            return 0;
        else if (this.time > otherTime)
            return 1;
        else
            return -1;
    }

    public int encodedLength()
    {
        return LENGTH;
    }

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

    public long getValue()
    {
        return this.time;
    }
}
