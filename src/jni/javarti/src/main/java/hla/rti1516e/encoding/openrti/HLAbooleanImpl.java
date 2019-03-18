package hla.rti1516e.encoding.openrti;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAboolean;

public class HLAbooleanImpl implements HLAboolean
{
    private static final int HLA_TRUE = 0x01;
    private static final int HLA_FALSE = 0x00;

    private HLAinteger32BEImpl value;

    public HLAbooleanImpl()
    {
        this.value = new HLAinteger32BEImpl(HLA_FALSE);
    }

    public HLAbooleanImpl(boolean value)
    {
        this();
        this.setValue(value);
    }

    @Override
    public boolean getValue()
    {
        return this.value.getValue() == HLA_TRUE;
    }

    @Override
    public void setValue(boolean value)
    {
        int valueAsInt = value ? HLA_TRUE : HLA_FALSE;
        this.value.setValue(valueAsInt);
    }

    @Override
    public int getOctetBoundary()
    {
        return this.value.getOctetBoundary();
    }

    @Override
    public void encode(ByteWrapper byteWrapper) throws EncoderException
    {
        this.value.encode(byteWrapper);
    }

    @Override
    public int getEncodedLength()
    {
        return this.value.getEncodedLength();
    }

    @Override
    public byte[] toByteArray() throws EncoderException
    {
        return this.value.toByteArray();
    }

    @Override
    public void decode(ByteWrapper byteWrapper) throws DecoderException
    {
        this.value.decode(byteWrapper);
    }

    @Override
    public void decode(byte[] bytes) throws DecoderException
    {
        try
        {
            ByteBuffer buf = ByteBuffer.wrap(bytes);
            int candidateValue = buf.getInt();
            this.value.setValue(candidateValue);
        }
        catch (BufferUnderflowException ex)
        {
            throw new DecoderException(ex.getMessage(), ex);
        }
    }

    @Override
    public int decodeFrom(ByteWrapper byteWrapper, int index) throws DecoderException
    {
        decode(new ByteWrapper(Arrays.copyOfRange(byteWrapper.array(), index, byteWrapper.array().length)));
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
