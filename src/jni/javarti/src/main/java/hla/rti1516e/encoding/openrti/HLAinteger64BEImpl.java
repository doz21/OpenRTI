package hla.rti1516e.encoding.openrti;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAinteger64BE;

public class HLAinteger64BEImpl implements HLAinteger64BE
{
    private long value;

    public HLAinteger64BEImpl()
    {
        this.value = Long.MIN_VALUE;
    }

    public HLAinteger64BEImpl(long value)
    {
        this.value = value;
    }

    @Override
    public long getValue()
    {
        return this.value;
    }

    @Override
    public void setValue(long value)
    {
        this.value = value;
    }

    @Override
    public final int getOctetBoundary()
    {
        return 8;
    }

    @Override
    public final int getEncodedLength()
    {
        return 8;
    }

    @Override
    public final void encode(ByteWrapper byteWrapper) throws EncoderException
    {
        if (byteWrapper.remaining() < 8)
            throw new EncoderException("Insufficient space remaining in buffer to encode this value");
        else
            byteWrapper.put(toByteArray());
    }

    @Override
    public final byte[] toByteArray() throws EncoderException
    {
        try
        {
            ByteBuffer buf = ByteBuffer.allocate(getEncodedLength());
            buf.putLong(value);
            return buf.array();
        }
        catch (BufferOverflowException | UnsupportedOperationException ex)
        {
            throw new EncoderException(ex.getMessage(), ex);
        }
    }

    @Override
    public final void decode(ByteWrapper byteWrapper) throws DecoderException
    {
        decode(byteWrapper.array());
    }

    @Override
    public final void decode(byte[] bytes) throws DecoderException
    {
        try
        {
            ByteBuffer buf = ByteBuffer.wrap(bytes);
            this.value = buf.getLong();
        }
        catch (BufferUnderflowException ex)
        {
            throw new DecoderException(ex.getMessage(), ex);
        }
    }

    @Override
    public int decodeFrom(ByteWrapper byteWrapper, int index) throws DecoderException
    {
        decode(Arrays.copyOfRange(byteWrapper.array(), index, byteWrapper.array().length));
        return index + getEncodedLength();
    }

    @Override
    public int getEncodedLengthAligned(int preLength)
    {
        int length = preLength % getOctetBoundary();
        length += getEncodedLength();
        return length;
    }
}
