package hla.rti1516e.encoding.openrti;

import hla.rti1516e.encoding.HLAinteger16BE;

public class HLAInteger16BEImpl extends HLAoctetPairBEImpl implements HLAinteger16BE
{
    public HLAInteger16BEImpl()
    {
        super();
    }

    public HLAInteger16BEImpl(short value)
    {
        super(value);
    }
}
