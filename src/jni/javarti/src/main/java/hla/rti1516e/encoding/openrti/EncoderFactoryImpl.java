package hla.rti1516e.encoding.openrti;

import hla.rti1516e.encoding.DataElement;
import hla.rti1516e.encoding.DataElementFactory;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAASCIIchar;
import hla.rti1516e.encoding.HLAASCIIstring;
import hla.rti1516e.encoding.HLAboolean;
import hla.rti1516e.encoding.HLAbyte;
import hla.rti1516e.encoding.HLAfixedArray;
import hla.rti1516e.encoding.HLAfixedRecord;
import hla.rti1516e.encoding.HLAfloat32BE;
import hla.rti1516e.encoding.HLAfloat32LE;
import hla.rti1516e.encoding.HLAfloat64BE;
import hla.rti1516e.encoding.HLAfloat64LE;
import hla.rti1516e.encoding.HLAinteger16BE;
import hla.rti1516e.encoding.HLAinteger16LE;
import hla.rti1516e.encoding.HLAinteger32BE;
import hla.rti1516e.encoding.HLAinteger32LE;
import hla.rti1516e.encoding.HLAinteger64BE;
import hla.rti1516e.encoding.HLAinteger64LE;
import hla.rti1516e.encoding.HLAoctet;
import hla.rti1516e.encoding.HLAoctetPairBE;
import hla.rti1516e.encoding.HLAoctetPairLE;
import hla.rti1516e.encoding.HLAopaqueData;
import hla.rti1516e.encoding.HLAunicodeChar;
import hla.rti1516e.encoding.HLAunicodeString;
import hla.rti1516e.encoding.HLAvariableArray;
import hla.rti1516e.encoding.HLAvariantRecord;

public class EncoderFactoryImpl implements EncoderFactory
{
    public HLAASCIIchar createHLAASCIIchar()
    {
        return new HLAASCIIcharImpl();
    }

    public HLAASCIIchar createHLAASCIIchar(byte b)
    {
        return new HLAASCIIcharImpl(b);
    }

    public HLAASCIIstring createHLAASCIIstring()
    {
        return new HLAASCIIstringImpl();
    }

    public HLAASCIIstring createHLAASCIIstring(String s)
    {
        return new HLAASCIIstringImpl(s);
    }

    public HLAboolean createHLAboolean()
    {
        return new HLAbooleanImpl();
    }

    public HLAboolean createHLAboolean(boolean b)
    {
        return new HLAbooleanImpl(b);
    }

    public HLAbyte createHLAbyte()
    {
        return new HLAbyteImpl();
    }

    public HLAbyte createHLAbyte(byte b)
    {
        return new HLAbyteImpl(b);
    }

    public <T extends DataElement> HLAvariantRecord<T> createHLAvariantRecord(T discriminant)
    {
        return new HLAvariantRecordImpl<>(discriminant);
    }

    public HLAfixedRecord createHLAfixedRecord()
    {
        return new HLAfixedRecordImpl();
    }

    public <T extends DataElement> HLAfixedArray<T> createHLAfixedArray(DataElementFactory<T> factory, int size)
    {
        return new HLAfixedArrayImpl<T>(factory, size);
    }

    public <T extends DataElement> HLAfixedArray<T> createHLAfixedArray(@SuppressWarnings("unchecked") T... elements)
    {
        return new HLAfixedArrayImpl<T>(elements);
    }

    public HLAfloat32BE createHLAfloat32BE()
    {
        return new HLAfloat32BEImpl();
    }

    public HLAfloat32BE createHLAfloat32BE(float f)
    {
        return new HLAfloat32BEImpl(f);
    }

    public HLAfloat32LE createHLAfloat32LE()
    {
        return new HLAfloat32LEImpl();
    }

    public HLAfloat32LE createHLAfloat32LE(float f)
    {
        return new HLAfloat32LEImpl(f);
    }

    public HLAfloat64BE createHLAfloat64BE()
    {
        return new HLAfloat64BEImpl();
    }

    public HLAfloat64BE createHLAfloat64BE(double d)
    {
        return new HLAfloat64BEImpl(d);
    }

    public HLAfloat64LE createHLAfloat64LE()
    {
        return new HLAfloat64LEImpl();
    }

    public HLAfloat64LE createHLAfloat64LE(double d)
    {
        return new HLAfloat64LEImpl(d);
    }

    public HLAinteger16BE createHLAinteger16BE()
    {
        return new HLAInteger16BEImpl();
    }

    public HLAinteger16BE createHLAinteger16BE(short s)
    {
        return new HLAInteger16BEImpl(s);
    }

    public HLAinteger16LE createHLAinteger16LE()
    {
        return new HLAinteger16LEImpl();
    }

    public HLAinteger16LE createHLAinteger16LE(short s)
    {
        return new HLAinteger16LEImpl(s);
    }

    public HLAinteger32BE createHLAinteger32BE()
    {
        return new HLAinteger32BEImpl();
    }

    public HLAinteger32BE createHLAinteger32BE(int i)
    {
        return new HLAinteger32BEImpl(i);
    }

    public HLAinteger32LE createHLAinteger32LE()
    {
        return new HLAinteger32LEImpl();
    }

    public HLAinteger32LE createHLAinteger32LE(int i)
    {
        return new HLAinteger32LEImpl(i);
    }

    public HLAinteger64BE createHLAinteger64BE()
    {
        return new HLAinteger64BEImpl();
    }

    public HLAinteger64BE createHLAinteger64BE(long l)
    {
        return new HLAinteger64BEImpl(l);
    }

    public HLAinteger64LE createHLAinteger64LE()
    {
        return new HLAinteger64LEImpl();
    }

    public HLAinteger64LE createHLAinteger64LE(long l)
    {
        return new HLAinteger64LEImpl(l);
    }

    public HLAoctet createHLAoctet()
    {
        return new HLAoctetImpl();
    }

    public HLAoctet createHLAoctet(byte b)
    {
        return new HLAoctetImpl(b);
    }

    public HLAoctetPairBE createHLAoctetPairBE()
    {
        return new HLAoctetPairBEImpl();
    }

    public HLAoctetPairBE createHLAoctetPairBE(short s)
    {
        return new HLAoctetPairBEImpl(s);
    }

    public HLAoctetPairLE createHLAoctetPairLE()
    {
        return new HLAoctetPairLEImpl();
    }

    public HLAoctetPairLE createHLAoctetPairLE(short s)
    {
        return new HLAoctetPairLEImpl(s);
    }

    public HLAopaqueData createHLAopaqueData()
    {
        return new HLAopaqueDataImpl();
    }

    public HLAopaqueData createHLAopaqueData(byte[] b)
    {
        return new HLAopaqueDataImpl(b);
    }

    public HLAunicodeChar createHLAunicodeChar()
    {
        return new HLAunicodeCharImpl();
    }

    public HLAunicodeChar createHLAunicodeChar(short c)
    {
        return new HLAunicodeCharImpl(c);
    }

    public HLAunicodeString createHLAunicodeString()
    {
        return new HLAunicodeStringImpl();
    }

    public HLAunicodeString createHLAunicodeString(String s)
    {
        return new HLAunicodeStringImpl(s);
    }

    public <T extends DataElement> HLAvariableArray<T> createHLAvariableArray(DataElementFactory<T> factory,
            @SuppressWarnings("unchecked") T... elements)
    {
        return new HLAvariableArrayImpl<T>(factory, elements);
    }

}
