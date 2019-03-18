package hla.rti1516e.openrti;

import java.util.HashMap;

import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.encoding.ByteWrapper;

public class AttributeHandleValueMapImpl extends HashMap<AttributeHandle, byte[]> implements AttributeHandleValueMap
{

    public AttributeHandleValueMapImpl(int capacity)
    {
        super(capacity);
    }

    @Override
    public ByteWrapper getValueReference(AttributeHandle key)
    {
        byte[] buffer = get(key);
        if (buffer != null)
        {
            return new ByteWrapper(buffer);
        }
        return null;
    }

    @Override
    public ByteWrapper getValueReference(AttributeHandle key, ByteWrapper byteWrapper)
    {
        byte[] buffer = get(key);
        if (buffer != null)
        {
            byteWrapper.reassign(buffer, 0, buffer.length);
            return byteWrapper;
        }
        return null;
    }

}
