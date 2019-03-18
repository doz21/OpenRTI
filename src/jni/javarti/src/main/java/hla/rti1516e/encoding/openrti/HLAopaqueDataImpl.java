package hla.rti1516e.encoding.openrti;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAopaqueData;

public class HLAopaqueDataImpl implements HLAopaqueData
{
    public byte[] value;

    public HLAopaqueDataImpl()
    {
        this.setValue(new byte[0]);
    }

    public HLAopaqueDataImpl(byte[] value)
    {
        this.setValue(value);
    }

    @Override
    public int size()
    {
        return this.value.length;
    }

    @Override
    public byte get(int index)
    {
        return this.value[index];
    }

    @Override
    public Iterator<Byte> iterator()
    {
        return new Iterator<Byte>()
        {
            private int index = 0;

            public boolean hasNext()
            {
                return index + 1 < value.length;
            }

            public Byte next()
            {
                if (hasNext() == false)
                    throw new NoSuchElementException("Past end of the iterator: index=" + index);

                index++;
                return value[index];
            }

            public void remove() throws UnsupportedOperationException
            {
                throw new UnsupportedOperationException("Removal not supported");
            }
        };
    }

    @Override
    public byte[] getValue()
    {
        return this.value;
    }

    @Override
    public void setValue(byte[] value)
    {
        if (value == null)
            this.value = new byte[0];
        else
            this.value = value;
    }

    @Override
    public final int getOctetBoundary()
    {
        return 4 + value.length;
    }

    @Override
    public final int getEncodedLength()
    {
        return 4 + value.length;
    }

    @Override
    public final void encode(ByteWrapper byteWrapper) throws EncoderException
    {
        if (byteWrapper.remaining() < getEncodedLength())
            throw new EncoderException("Insufficient space remaining in buffer to encode this value");

        byteWrapper.putInt(this.value.length);
        byteWrapper.put(this.value);
    }

    @Override
    public final byte[] toByteArray() throws EncoderException
    {
        // Encode into a byte wrapper
        ByteWrapper byteWrapper = new ByteWrapper(getEncodedLength());
        this.encode(byteWrapper);

        // Return underlying array
        return byteWrapper.array();
    }

    @Override
    public final void decode(ByteWrapper byteWrapper) throws DecoderException
    {
        try
        {
            int length = byteWrapper.getInt();
            this.value = new byte[length];
            byteWrapper.get(this.value);
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            throw new DecoderException(ex.getMessage(), ex);
        }
    }

    @Override
    public final void decode(byte[] bytes) throws DecoderException
    {
        ByteWrapper byteWrapper = new ByteWrapper(bytes);
        this.decode(byteWrapper);
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
