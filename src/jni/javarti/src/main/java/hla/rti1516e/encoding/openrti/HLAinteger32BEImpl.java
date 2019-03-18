package hla.rti1516e.encoding.openrti;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAinteger32BE;

public class HLAinteger32BEImpl implements HLAinteger32BE
{
    private int value;

    public HLAinteger32BEImpl()
    {
        this.value = Integer.MIN_VALUE;
    }

    public HLAinteger32BEImpl(int value)
    {
        this.value = value;
    }

    @Override
    public int getValue()
    {
        return this.value;
    }

    @Override
    public void setValue(int value)
    {
        this.value = value;
    }

    @Override
    public final int getOctetBoundary()
    {
        return 4;
    }

    @Override
    public final int getEncodedLength()
    {
        return 4;
    }

    @Override
    public final void encode(ByteWrapper byteWrapper) throws EncoderException
    {
        if (byteWrapper.remaining() < 4)
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
            buf.putInt(value);
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
            this.value = buf.getInt();
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
