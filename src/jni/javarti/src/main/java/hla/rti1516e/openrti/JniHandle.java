package hla.rti1516e.openrti;

public abstract class JniHandle extends JniObject
{
    private long hash;

    protected JniHandle()
    {
        super();
    }
   

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (hash ^ (hash >>> 32));
        return result;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JniHandle other = (JniHandle) obj;
        if (hash != other.hash)
            return false;
        return true;
    }


    public int encodedLength()
    {
        throw new RuntimeException("not implemented yet");
    }

    public void encode(byte[] buffer, int offset)
    {
        throw new RuntimeException("not implemented yet");
    }
}
