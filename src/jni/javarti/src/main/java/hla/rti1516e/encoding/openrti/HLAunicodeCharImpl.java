package hla.rti1516e.encoding.openrti;

import hla.rti1516e.encoding.HLAunicodeChar;

public class HLAunicodeCharImpl extends HLAInteger16BEImpl implements HLAunicodeChar
{
    public HLAunicodeCharImpl()
    {
        super();
    }

    public HLAunicodeCharImpl(short value)
    {
        super(value);
    }
}
