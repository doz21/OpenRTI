package hla.rti1516e.encoding.openrti;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAASCIIchar;

public class HLAASCIIcharImpl implements HLAASCIIchar
{
    private byte value;

    public HLAASCIIcharImpl()
    {
        this.value = Byte.MIN_VALUE;
    }

    public HLAASCIIcharImpl(byte value)
    {
        this.value = value;
    }

    @Override
    public byte getValue()
    {
        return this.value;
    }

    @Override
    public void setValue(byte value)
    {
        this.value = value;
    }

    @Override
    public int getOctetBoundary()
    {
        return 1;
    }

    @Override
    public void encode(ByteWrapper byteWrapper) throws EncoderException
    {
        try
        {
            byteWrapper.put(this.value);
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            throw new EncoderException(ex.getMessage(), ex);
        }
    }

    @Override
    public int getEncodedLength()
    {
        return 1;
    }

    @Override
    public byte[] toByteArray() throws EncoderException
    {
        return new byte[]
        { this.value };
    }

    @Override
    public void decode(ByteWrapper byteWrapper) throws DecoderException
    {
        try
        {
            this.value = (byte) byteWrapper.get();
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            throw new DecoderException(ex.getMessage(), ex);
        }
    }

    @Override
    public void decode(byte[] bytes) throws DecoderException
    {
        if (bytes == null || bytes.length < 1)
        {
            throw new DecoderException("invalid bytes");
        }
        this.value = bytes[0];
    }

    @Override
    public int decodeFrom(ByteWrapper byteWrapper, int index) throws DecoderException
    {
        byteWrapper.advance(index - byteWrapper.getPos());
        decode(byteWrapper);
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
