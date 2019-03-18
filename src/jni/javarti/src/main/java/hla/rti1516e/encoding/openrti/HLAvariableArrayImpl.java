package hla.rti1516e.encoding.openrti;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DataElement;
import hla.rti1516e.encoding.DataElementFactory;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAvariableArray;

public class HLAvariableArrayImpl<T extends DataElement> implements HLAvariableArray<T>
{
    private static final int SIZE_BYTE_COUNT = 4;

    private DataElementFactory<T> factory;
    private List<T> elements;

    public HLAvariableArrayImpl(DataElementFactory<T> factory, @SuppressWarnings("unchecked") T... provided)
    {
        this.factory = factory;
        this.elements = new ArrayList<T>(provided.length);

        for (T element : provided)
            this.elements.add(element);
    }

    @Override
    public void addElement(T dataElement)
    {
        this.elements.add(dataElement);
    }

    @Override
    public void resize(int newSize)
    {
        int existingSize = this.elements.size();
        if (newSize > existingSize)
        {
            // Up-sizing to a larger capacity, so make up the difference using elements created
            // from the provided factory
            int deltaSize = newSize - existingSize;
            for (int i = 0; i < deltaSize; ++i)
                this.elements.add(this.factory.createElement(existingSize + i));
        }
        else if (newSize < existingSize)
        {
            // Down-sizing to a smaller capacity, so cull items from the end of the list 
            while (this.elements.size() > newSize)
                this.elements.remove(this.elements.size() - 1);
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
        int maxSize = 4;
        if (this.size() > 0)
        {
            maxSize = Math.max(maxSize, this.elements.get(0).getOctetBoundary());
        }
        return maxSize;
    }

    @Override
    public void encode(ByteWrapper byteWrapper) throws EncoderException
    {
        int octetBoundary = getOctetBoundary();
        byteWrapper.align(octetBoundary);
        if (byteWrapper.remaining() < this.getEncodedLength())
            throw new EncoderException("Insufficient space remaining in buffer to encode this value");

        byteWrapper.putInt(this.elements.size());
        byteWrapper.align(octetBoundary);

        for (T element : this.elements)
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
        // Create a ByteWrapper to encode into
        int length = this.getEncodedLength();
        ByteWrapper byteWrapper = new ByteWrapper(length);
        this.encode(byteWrapper);

        // Return the underlying array
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
        // Wrap the byte array in a ByteWrapper to decode from
        ByteWrapper byteWrapper = new ByteWrapper(bytes);
        this.decode(byteWrapper);
    }

    @Override
    public int decodeFrom(ByteWrapper byteWrapper, int index) throws DecoderException
    {
        try
        {
            // Need at least 4 bytes to read the number of elements
            byteWrapper.advance(index - byteWrapper.getPos());
            if (byteWrapper.remaining() < 4)
            {
                throw new DecoderException("Buffer underflow: Expected 4, found " + byteWrapper.remaining());
            }
            int size = byteWrapper.getInt();
            byteWrapper.align(getOctetBoundary());
            index = byteWrapper.getPos();

            // Clear the underlying collection so that it's ready to receive the new values
            this.elements.clear();

            this.resize(size);
         // Decode the elements
            for (T element : elements)
            {
                index = element.decodeFrom(byteWrapper, index);
                break; // Only the first element is of interest with the current visualization of arrays in the FederationOverview
            }
        }
        catch (ArrayIndexOutOfBoundsException | UnsupportedOperationException ex)
        {
            throw new DecoderException(ex.getMessage(), ex);
        }

        return index;
    }

    @Override
    public int getEncodedLengthAligned(int preLength)
    {
        int octetBoundary = getOctetBoundary();
        int length = preLength;
        if (4 < octetBoundary)
        {
            length += SIZE_BYTE_COUNT;
        }
        else
        {
            length += octetBoundary;
        }

        for (T element : this.elements)
        {
            length += length % element.getOctetBoundary();
            length += element.getEncodedLengthAligned(length);
        }
        return length - preLength;
    }
}
