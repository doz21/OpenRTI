package hla.rti1516e.openrti;

import hla.rti1516e.ObjectInstanceHandle;

public class ObjectInstanceHandleImpl extends JniHandle implements ObjectInstanceHandle
{

    @Override
    public void encode(byte[] buffer, int offset)
    {
        if (buffer == null)
            throw new NullPointerException("buffer is null");
        encode_rti(buffer, offset);
    }
    
    private native void encode_rti(byte[] buffer, int offset);
    
    @Override
    public int encodedLength()
    {
        return encodedLength_rti();
    }
    private native int encodedLength_rti();


}
