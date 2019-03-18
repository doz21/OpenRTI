package hla.rti1516e.openrti;

import hla.rti1516e.exceptions.RTIinternalError;

public abstract class JniObject
{
    protected long ptr;

    protected JniObject()
    {
    }

    @Override
    protected void finalize() throws Throwable
    {
        try
        {
            dispose();
        }
        finally
        {
            super.finalize();
        }
    }

    final protected native void dispose() throws RTIinternalError;

    final protected long getPtr()
    {
        return ptr;
    }
}
