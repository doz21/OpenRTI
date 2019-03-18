package hla.rti1516e.encoding.openrti;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DataElement;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAfixedRecord;

public class HLAfixedRecordImpl implements HLAfixedRecord
{
    private List<DataElement> elements;

    public HLAfixedRecordImpl()
    {
        this.elements = new ArrayList<DataElement>();
    }

    @Override
    public void add(DataElement dataElement)
    {
        if (dataElement != null)
        {
            this.elements.add(dataElement);
        }
    }

    @Override
    public int size()
    {
        return this.elements.size();
    }

    @Override
    public DataElement get(int index)
    {
        return this.elements.get(index);
    }

    @Override
    public Iterator<DataElement> iterator()
    {
        return this.elements.iterator();
    }

    @Override
    public int getOctetBoundary()
    {
        return 1; // Might be changed in the future, currently implemented in OpenRTI like this
    }

    @Override
    public void encode(ByteWrapper byteWrapper) throws EncoderException
    {
        if (this.elements.size() == 0)
            throw new EncoderException("Cannot encode an empty fixed record!");

        byteWrapper.align(getOctetBoundary());

        for (DataElement element : this.elements)
        {
            byteWrapper.align(element.getOctetBoundary());
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
        // Encode into a byte wrapper
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
        // Wrap in a byte wrapper and decode
        ByteWrapper byteWrapper = new ByteWrapper(bytes);
        this.decode(byteWrapper);
    }

    @Override
    public int decodeFrom(ByteWrapper byteWrapper, int index) throws DecoderException
    {
        if (this.elements.size() == 0)
            throw new DecoderException("Cannot decode into an empty fixed record!");

        for (DataElement element : this.elements)
        {
            byteWrapper.advance(index - byteWrapper.getPos());
            byteWrapper.align(element.getOctetBoundary());
            index = byteWrapper.getPos();
            index = element.decodeFrom(byteWrapper, index);
        }

        return index;
    }

    @Override
    public int getEncodedLengthAligned(int preLength)
    {
        int length = preLength;
        for (DataElement element : this.elements)
        {
            length += length % element.getOctetBoundary();
            length += element.getEncodedLengthAligned(length);
        }

        return length - preLength;
    }
}
