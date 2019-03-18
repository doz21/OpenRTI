package hla.rti1516e.encoding.openrti;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAASCIIstring;

public class HLAASCIIstringImpl implements HLAASCIIstring
{
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private byte[] value;

    public HLAASCIIstringImpl()
    {
        this.value = "".getBytes(CHARSET);
    }

    public HLAASCIIstringImpl(String value)
    {
        this.value = value.getBytes(CHARSET);
    }

    @Override
    public String getValue()
    {
        return new String(value, CHARSET);
    }

    @Override
    public void setValue(String value)
    {
        this.value = value.getBytes(CHARSET);
    }

    @Override
    public int getOctetBoundary()
    {
        return getEncodedLength();
    }

    @Override
    public int getEncodedLength()
    {
        return 4 + value.length;
    }

    @Override
    public void encode(ByteWrapper byteWrapper) throws EncoderException
    {
        try
        {
            byteWrapper.put(toByteArray());
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            throw new EncoderException(ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] toByteArray() throws EncoderException
    {
        ByteBuffer buffer = ByteBuffer.allocate(getEncodedLength());
        buffer.putInt(value.length).put(value);
        return buffer.array();
    }

    @Override
    public void decode(ByteWrapper byteWrapper) throws DecoderException
    {
        try
        {
            int len = byteWrapper.getInt();
            byte[] buffer = new byte[len];
            byteWrapper.get(buffer);
            value = buffer;
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            throw new DecoderException(ex.getMessage(), ex);
        }
    }

    @Override
    public void decode(byte[] bytes) throws DecoderException
    {
        this.decode(new ByteWrapper(bytes));
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
