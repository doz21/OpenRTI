package hla.rti1516e.encoding.openrti;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAfloat32BE;

public class HLAfloat32BEImpl implements HLAfloat32BE
{
    private float value;

    public HLAfloat32BEImpl()
    {
        this.value = Float.MIN_VALUE;
    }

    public HLAfloat32BEImpl(float value)
    {
        this.value = value;
    }

    @Override
    public float getValue()
    {
        return this.value;
    }

    @Override
    public void setValue(float value)
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
        try
        {
            byteWrapper.put(toByteArray());
        }
        catch (Exception e)
        {
            throw new EncoderException(e.getMessage(), e);
        }
    }

    @Override
    public final byte[] toByteArray() throws EncoderException
    {
        try
        {
            ByteBuffer buf = ByteBuffer.allocate(getEncodedLength());
            buf.putFloat(value);
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
            this.value = buf.getFloat();
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
