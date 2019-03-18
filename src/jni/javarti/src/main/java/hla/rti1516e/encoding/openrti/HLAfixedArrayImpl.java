package hla.rti1516e.encoding.openrti;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DataElement;
import hla.rti1516e.encoding.DataElementFactory;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAfixedArray;

public class HLAfixedArrayImpl<T extends DataElement> implements HLAfixedArray<T>
{
    private static final int SIZE_BYTE_COUNT = 4;

    protected List<T> elements;

    public HLAfixedArrayImpl(@SuppressWarnings("unchecked") T... provided)
    {
        this.elements = new ArrayList<T>(provided.length);
        for (T element : provided)
        {
            this.elements.add(element);
        }
    }

    /**
     * Create a new fixed array of the provided size and prepopulate it with the identified number of T instances
     * (using the factory)
     */
    public HLAfixedArrayImpl(DataElementFactory<T> factory, int size)
    {
        this.elements = new ArrayList<T>(size);
        for (int i = 0; i < size; i++)
        {
            elements.add(factory.createElement(i));
        }
    }

    @Override
    public int size()
    {
        return this.elements.size();
    }

    @Override
    public T get(int index)
    {
        return this.elements.get(index);
    }

    @Override
    public Iterator<T> iterator()
    {
        return this.elements.iterator();
    }

    @Override
    public int getOctetBoundary()
    {
        // Return the size of the largest element
        int maxSize = 1;

        if (this.size() > 0)
        {
            maxSize = Math.max(maxSize, this.elements.get(0).getOctetBoundary());
        }

        return maxSize;
    }

    @Override
    public void encode(ByteWrapper byteWrapper) throws EncoderException
    {
        int length = this.size();
        if (byteWrapper.remaining() < this.getEncodedLength())
        {
            throw new EncoderException("Insufficient space remaining in buffer to encode this value");
        }

        // Write the array length
        byteWrapper.putInt(length);

        // Write the array contents
        for (T element : elements)
        {
            element.encode(byteWrapper);
        }
    }

    @Override
    public int getEncodedLength()
    {
        return getEncodedLengthAligned(0);
    }

    @Override
    public byte[] toByteArray() throws EncoderException
    {
        // Encode the array and then use the ByteWrapper's underlying byte[]
        int length = this.getEncodedLength();
        ByteWrapper byteWrapper = new ByteWrapper(length);
        this.encode(byteWrapper);

        return byteWrapper.array();
    }

    @Override
    public void decode(ByteWrapper byteWrapper) throws DecoderException
    {
        decodeFrom(byteWrapper, 0);
    }

    @Override
    public void decode(byte[] bytes) throws DecoderException
    {
        // Decode via a ByteWrapper
        ByteWrapper byteWrapper = new ByteWrapper(bytes);
        this.decode(byteWrapper);
    }

    @Override
    public int decodeFrom(ByteWrapper byteWrapper, int index) throws DecoderException
    {
        byteWrapper.advance(index - byteWrapper.getPos());

        // Need at least 4 bytes to read the number of elements
        if (byteWrapper.remaining() < 4)
        {
            throw new DecoderException("Buffer underflow: Expected 4, found " + byteWrapper.remaining());
        }

        // Incoming size must match the size that the array was initialised with
        int size = byteWrapper.getInt();
        if (size != this.elements.size())
        {
            throw new DecoderException("Element count in decoded array differs. Expected [" + this.elements.size()
                    + "] Received [" + size + "]");
        }
        index = index + SIZE_BYTE_COUNT;

        // Decode the elements
        for (T element : elements)
        {
            index = element.decodeFrom(byteWrapper, index);
            break; // Only the first element is of interest with the current visualization of arrays in the FederationOverview
        }

        return index;
    }

    @Override
    public int getEncodedLengthAligned(int preLength)
    {
        int length = preLength + SIZE_BYTE_COUNT;
        for (T element : this.elements)
        {
            length += length % element.getOctetBoundary();
            length += element.getEncodedLengthAligned(length);
        }

        return length - preLength;
    }
}
