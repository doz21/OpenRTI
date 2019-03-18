package hla.rti1516e.time.openrti;

import java.nio.ByteBuffer;

import hla.rti1516e.exceptions.CouldNotDecode;
import hla.rti1516e.time.HLAinteger64Interval;
import hla.rti1516e.time.HLAinteger64Time;
import hla.rti1516e.time.HLAinteger64TimeFactory;

public class LongTimeFactory implements HLAinteger64TimeFactory
{
    private static final long serialVersionUID = 5924573578880020308L;

    private static final String NAME = "HLAinteger64Time";

    @Override
    public HLAinteger64Time decodeTime(byte[] buffer, int offset) throws CouldNotDecode
    {
        try
        {
            ByteBuffer buf = ByteBuffer.wrap(buffer);
            return new LongTime(buf.getLong(offset));
        }
        catch (IndexOutOfBoundsException ex)
        {
            throw new CouldNotDecode(ex.getMessage(), ex);
        }
    }

    @Override
    public HLAinteger64Interval decodeInterval(byte[] buffer, int offset) throws CouldNotDecode
    {
        try
        {
            ByteBuffer buf = ByteBuffer.wrap(buffer);
            return new LongTimeInterval(buf.getLong(offset));
        }
        catch (IndexOutOfBoundsException ex)
        {
            throw new CouldNotDecode(ex.getMessage(), ex);
        }
    }

    @Override
    public HLAinteger64Time makeInitial()
    {
        return new LongTime(0L);
    }

    @Override
    public HLAinteger64Time makeFinal()
    {
        return new LongTime(Long.MAX_VALUE);
    }

    @Override
    public HLAinteger64Time makeTime(long value)
    {
        return new LongTime(value);
    }

    @Override
    public HLAinteger64Interval makeZero()
    {
        return new LongTimeInterval(0L);
    }

    @Override
    public HLAinteger64Interval makeEpsilon()
    {
        return new LongTimeInterval(1L);
    }

    @Override
    public HLAinteger64Interval makeInterval(long value)
    {
        return new LongTimeInterval(value);
    }

    @Override
    public String getName()
    {
        return NAME;
    }
}
