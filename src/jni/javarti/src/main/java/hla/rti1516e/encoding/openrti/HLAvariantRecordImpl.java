package hla.rti1516e.encoding.openrti;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DataElement;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAvariantRecord;

public class HLAvariantRecordImpl<T extends DataElement> implements HLAvariantRecord<T>
{
    private T discriminant = null;
    private Map<T, DataElement> variants = new HashMap<>();

    HLAvariantRecordImpl()
    {
    }

    HLAvariantRecordImpl(T discriminant)
    {
        this.discriminant = discriminant;
    }

    @Override
    public void setVariant(T discriminant, DataElement dataElement)
    {
        this.variants.put(discriminant, dataElement);
    }

    @Override
    public void setDiscriminant(T discriminant)
    {
        // should this be avoided if discriminant does not exist in the variants map?
        this.discriminant = discriminant;
    }

    @Override
    public T getDiscriminant()
    {
        return this.discriminant;
    }

    @Override
    public DataElement getValue()
    {
        if (!this.variants.containsKey(this.discriminant))
            return null;

        return this.variants.get(this.discriminant);
    }

    @Override
    public int getOctetBoundary()
    {
        if (this.discriminant == null)
            return 1;

        // the octet boundary of a HLAvariantRecord is the maximum of the octet boundary
        // of the discriminant and each of the possible alternatives. See section 4.13.9.2
        // of IEEE Std 1516-2010.2.
        int maxOctetBoundary = 1;
        for (DataElement variant : this.variants.values())
        {
            maxOctetBoundary = Math.max(maxOctetBoundary, variant.getOctetBoundary());
        }

        return maxOctetBoundary;
    }

    @Override
    public void encode(ByteWrapper byteWrapper) throws EncoderException
    {
        if (this.discriminant == null)
            throw new EncoderException("No discriminant set in HLAvariantRecord");

        if (this.getEncodedLength() > byteWrapper.remaining())
            throw new EncoderException("Not enought space in ByteWrapper to encode HLAvariantRecord");

        this.discriminant.encode(byteWrapper);
        // ignoring padding after the discriminant for now

        if (this.variants.containsKey(this.discriminant))
        {
            // encode the variant if the discriminant exists ...
            DataElement variant = this.variants.get(this.discriminant);
            if (variant != null)
            {
                // ... and has a value assigned.
                this.variants.get(this.discriminant).encode(byteWrapper);
            }
        }
        else
        {
            throw new EncoderException("Discriminant is unknown to this HLAvariantRecord");
        }
    }

    @Override
    public int getEncodedLength()
    {
        if (this.discriminant == null)
            return 0;

        if (!this.variants.containsKey(this.discriminant))
            return 0;

        // the encoded length is the encoded length of the discriminant ...
        int encodedLength = this.discriminant.getEncodedLength();

        // ... plus any padding that comes after the discriminant (ignore for now) ...
        if (this.variants.containsKey(this.discriminant))
        {
            DataElement variant = this.variants.get(this.discriminant);
            if (variant != null)
            {
                // ... plus the encoded length of the variant if the discriminant
                // exists and has an assigned variant.
                encodedLength += this.variants.get(this.discriminant).getEncodedLength();
            }
        }

        return encodedLength;
    }

    @Override
    public byte[] toByteArray() throws EncoderException
    {
        ByteWrapper byteWrapper = new ByteWrapper(this.getEncodedLength());
        this.encode(byteWrapper);

        return byteWrapper.array();
    }

    @Override
    public void decode(ByteWrapper byteWrapper) throws DecoderException
    {
        if (this.discriminant == null)
            throw new DecoderException("No space to decode the discriminant");

        if (this.discriminant.getEncodedLength() > byteWrapper.remaining())
            throw new DecoderException("Not enough data in ByteWrapper to decode a discriminant");

        this.discriminant.decode(byteWrapper);

        // consume padding (ignored for now)

        if (this.variants.containsKey(this.discriminant))
        {
            // if the decoded discriminant is known ...
            DataElement variant = this.variants.get(this.discriminant);

            if (variant == null)
            {
                // we're done decoding, there is no variant assigned to this discriminant
                return;
            }

            if (variant.getEncodedLength() > byteWrapper.remaining())
                throw new DecoderException(
                        "Not enough data in ByteWrapper to decode variant associated with discriminant.");

            // variant not null and enough data left in the byte wrapper, decode the variant.
            variant.decode(byteWrapper);
        }
        else
        {
            throw new DecoderException("Decoded discriminant is unknown to this HLAvariantRecord");
        }
    }

    @Override
    public void decode(byte[] bytes) throws DecoderException
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
