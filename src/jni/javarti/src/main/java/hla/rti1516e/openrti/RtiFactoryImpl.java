package hla.rti1516e.openrti;

import hla.rti1516e.RTIambassador;
import hla.rti1516e.RtiFactory;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.encoding.openrti.EncoderFactoryImpl;
import hla.rti1516e.exceptions.RTIinternalError;

public class RtiFactoryImpl implements RtiFactory
{
    private static final EncoderFactory ENCODER_FACTORY = new EncoderFactoryImpl();
    
    private static final String VERSION = "0.9.0";
    private static final String NAME = "OpenRTI";
    static
    {
        System.loadLibrary("javarti");
    }

    @Override
    public RTIambassador getRtiAmbassador() throws RTIinternalError
    {
        return getRtiAmbassador_rti();
    }

    @Override
    public EncoderFactory getEncoderFactory() throws RTIinternalError
    {
        return ENCODER_FACTORY;
    }

    @Override
    public String rtiName()
    {
        return NAME;
    }

    @Override
    public String rtiVersion()
    {
        return VERSION;
    }

    private native RTIambassador getRtiAmbassador_rti()  throws RTIinternalError;
}
