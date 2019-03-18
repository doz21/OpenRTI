package hla.rti1516e.encoding.openrti;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAunicodeString;

public class HLAunicodeStringImpl implements HLAunicodeString
{
    private static final Charset CHARSET = StandardCharsets.UTF_16BE;

    private String value;
    private int length;

    public HLAunicodeStringImpl()
    {
        setValue("");
    }

    public HLAunicodeStringImpl(String value)
    {
        setValue(value);
    }

    @Override
    public String getValue()
    {
        return this.value;
    }

    @Override
    public void setValue(String value)
    {
        this.value = String.valueOf(value);
        this.length = this.value.length() * 2;
    }

    @Override
    public int getOctetBoundary()
    {
        return getEncodedLength();
    }

    @Override
    public int getEncodedLength()
    {
        // index + 2 bytes per unicode character
        return 4 + length;
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
        // 4 bytes index + 2 bytes per unicode character
        ByteBuffer buffer = ByteBuffer.allocate(getEncodedLength());
        buffer.putInt(value.length()).put(value.getBytes(CHARSET));
        return buffer.array();
    }

    @Override
    public void decode(ByteWrapper byteWrapper) throws DecoderException
    {
        try
        {
            int len = byteWrapper.getInt();
            byte[] buffer = new byte[len * 2];
            byteWrapper.get(buffer);
            value = new String(buffer, CHARSET);
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
