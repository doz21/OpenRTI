package hla.rti1516e.encoding.openrti;

import java.util.Arrays;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAoctet;

public class HLAoctetImpl implements HLAoctet
{
    private byte value;

    public HLAoctetImpl()
    {
        this.value = Byte.MIN_VALUE;
    }

    public HLAoctetImpl(byte value)
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
        if (byteWrapper.remaining() < this.getEncodedLength())
            throw new EncoderException("Insufficient space remaining in buffer to encode this value");

        byteWrapper.put(this.value);
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

    /**
     * hashCode is required so that HLAoctetImpl can be used as a key in java.util.HashMap, which is used by
     * HLAvariantRecordImpl to store discriminant/variant pairs.
     */
    @Override
    public int hashCode()
    {
        return this.getValue();
    }

    /**
     * equals is required so that HLAoctetImpl can be used as a key in java.util.HashMap, which is used by
     * HLAvariantRecordImpl to store discriminant/variant pairs.
     */
    @Override
    public boolean equals(Object other)
    {
        return (this == other) || ((other != null) && (other instanceof HLAoctetImpl)
                && (this.getValue() == ((HLAoctetImpl) other).getValue()));
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

    //----------------------------------------------------------
    //                     STATIC METHODS
    //----------------------------------------------------------
}
