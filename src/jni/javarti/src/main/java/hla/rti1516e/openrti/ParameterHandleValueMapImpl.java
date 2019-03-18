package hla.rti1516e.openrti;

import java.util.HashMap;

import hla.rti1516e.ParameterHandle;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.encoding.ByteWrapper;

public class ParameterHandleValueMapImpl extends HashMap<ParameterHandle, byte[]> implements ParameterHandleValueMap
{

    public ParameterHandleValueMapImpl(int capacity)
    {
        super(capacity);
    }

    @Override
    public ByteWrapper getValueReference(ParameterHandle key)
    {
        byte[] buffer = get(key);
        if (buffer != null)
        {
            return new ByteWrapper(buffer);
        }
        return null;
    }

    @Override
    public ByteWrapper getValueReference(ParameterHandle key, ByteWrapper byteWrapper)
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
