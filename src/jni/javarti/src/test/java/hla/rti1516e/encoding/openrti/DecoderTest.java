package hla.rti1516e.encoding.openrti;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DataElement;
import hla.rti1516e.encoding.DataElementFactory;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.HLAfixedRecord;
import hla.rti1516e.encoding.HLAfloat64LE;
import hla.rti1516e.encoding.HLAvariableArray;

public class DecoderTest
{
    private static EncoderFactory factory;
    private static final byte[] byteArray2p5 =
    { 0, 0, 0, 0, 0, 0, 4, 64 };
    private static final byte[] byteArray2p5u4p7 =
    { 0, 0, 0, 0, 0, 0, 4, 64, -51, -52, -52, -52, -52, -52, 18, 64 };

    @BeforeClass
    public static void beforeClass() throws Exception
    {
        factory = RtiFactoryFactory.getRtiFactory().getEncoderFactory();
    }

    @Test
    public void toByteArrayZeroTest()
    {
        HLAfloat64LE el = factory.createHLAfloat64LE();
        el.setValue(0d);
        byte[] byteArrayZero =
        { 0, 0, 0, 0, 0, 0, 0, 0 };
        Assert.assertArrayEquals(byteArrayZero, el.toByteArray());
    }

    @Test
    public void toByteArrayNonZeroTest()
    {
        HLAfloat64LE el = factory.createHLAfloat64LE();
        el.setValue(2.5d);
        Assert.assertArrayEquals(byteArray2p5, el.toByteArray());
    }

    @Test
    public void decodeTest() throws DecoderException
    {
        HLAfloat64LE el = factory.createHLAfloat64LE(0d);
        el.decode(byteArray2p5);
        Assert.assertEquals(2.5, el.getValue(), 0);
    }

    @Test
    public void encodeTest() throws DecoderException
    {
        HLAfloat64LE el = factory.createHLAfloat64LE(2.5d);
        byte[] byteArray =
        { 0, 0, 0, 0, 0, 0, 4, 64 };
        ByteWrapper bw = new ByteWrapper(8);
        el.encode(bw);
        Assert.assertArrayEquals(byteArray, bw.array());
    }

    @Test
    public void recordDecodeTest() throws DecoderException
    {
        HLAfixedRecord record = factory.createHLAfixedRecord();
        record.add(factory.createHLAfloat64LE(0d));
        record.add(factory.createHLAfloat64LE(0d));
        record.decode(byteArray2p5u4p7);
        Iterator<DataElement> elements = record.iterator();
        Assert.assertEquals(2.5, ((HLAfloat64LEImpl) elements.next()).getValue(), 0);
        Assert.assertEquals(4.7, ((HLAfloat64LEImpl) elements.next()).getValue(), 0);
    }

    @Test(expected = DecoderException.class)
    public void willRecordThrowDecoderExceptionTest() throws DecoderException
    {
        HLAfixedRecord record = factory.createHLAfixedRecord();
        record.decode(byteArray2p5u4p7);
    }

    @Test
    public void getEncodedLengthAlignedTest()
    {
        HLAfloat64LE el = factory.createHLAfloat64LE();
        Assert.assertEquals(8, el.getEncodedLength());
        Assert.assertEquals(12, el.getEncodedLengthAligned(4));
    }

    @Test
    public void getOctetBoundaryArrayOfRecordTest()
    {
        HLAfixedRecord record = factory.createHLAfixedRecord();
        HLAfloat64LE el1 = factory.createHLAfloat64LE(0d);
        record.add(el1);
        record.add(factory.createHLAfloat64LE(0d));

        HLAvariableArray<HLAfixedRecord> array = factory.createHLAvariableArray(new DataElementFactory<HLAfixedRecord>()
        {
            @Override
            public HLAfixedRecord createElement(int index)
            {
                return factory.createHLAfixedRecord();
            }
        });

        array.addElement(record);

        Assert.assertEquals(8, el1.getOctetBoundary());
        Assert.assertEquals(1, record.getOctetBoundary());
        Assert.assertEquals(4, array.getOctetBoundary());
    }

    @Test
    public void getOctetBoundaryArrayOfDoubleTest()
    {
        HLAfloat64LE el1 = factory.createHLAfloat64LE(0d);

        HLAvariableArray<HLAfloat64LE> array = factory.createHLAvariableArray(new DataElementFactory<HLAfloat64LE>()
        {
            @Override
            public HLAfloat64LE createElement(int index)
            {
                return factory.createHLAfloat64LE();
            }
        });

        array.addElement(el1);

        Assert.assertEquals(el1.getOctetBoundary(), array.getOctetBoundary());
    }
}
