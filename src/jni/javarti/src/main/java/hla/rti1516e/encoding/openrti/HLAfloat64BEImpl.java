package hla.rti1516e.encoding.openrti;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAfloat64BE;

public class HLAfloat64BEImpl implements HLAfloat64BE
{
    private double value;

    public HLAfloat64BEImpl()
    {
        this.value = Double.MIN_VALUE;
    }

    public HLAfloat64BEImpl(double value)
    {
        this.value = value;
    }

    @Override
    public double getValue()
    {
        return this.value;
    }

    @Override
    public void setValue(double value)
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
            buf.putDouble(value);
            return buf.array();
        }
        catch (IndexOutOfBoundsException | UnsupportedOperationException ex)
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
            this.value = buf.getDouble();
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
