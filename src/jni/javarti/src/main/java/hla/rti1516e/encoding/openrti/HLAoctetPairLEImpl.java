package hla.rti1516e.encoding.openrti;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAoctetPairLE;

public class HLAoctetPairLEImpl implements HLAoctetPairLE
{
    private short value;

    public HLAoctetPairLEImpl()
    {
        this.value = Short.MIN_VALUE;
    }

    public HLAoctetPairLEImpl(short value)
    {
        this.value = value;
    }

    @Override
    public short getValue()
    {
        return this.value;
    }

    @Override
    public void setValue(short value)
    {
        this.value = value;
    }

    @Override
    public final int getOctetBoundary()
    {
        return 2;
    }

    @Override
    public final int getEncodedLength()
    {
        return 2;
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
            ByteBuffer buf = ByteBuffer.allocate(getEncodedLength()).order(ByteOrder.LITTLE_ENDIAN);
            buf.putShort(value);
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
            ByteBuffer buf = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
            this.value = buf.getShort();
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
