package hla.rti1516e.encoding.openrti;

import hla.rti1516e.encoding.HLAinteger16LE;

public class HLAinteger16LEImpl extends HLAoctetPairLEImpl implements HLAinteger16LE
{
    public HLAinteger16LEImpl()
    {
        super();
    }

    public HLAinteger16LEImpl(short value)
    {
        super(value);
    }
}
