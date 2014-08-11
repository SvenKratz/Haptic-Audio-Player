package processing.SpidarMouse;

public class Spidar {
    /*
     * �l�C�e�B�u�E���\�b�h�̎����͋��L���C�u�����ɏ�����Ă���̂ŁA
     * ���[�h���Ȃ���΂Ȃ�Ȃ��B�l�C�e�B�u�E���\�b�h���ĂԑO�Ƀ��[�h
     * ���Ă����΂悢�̂ŁA�ʏ��static�R�[�h�ōs���B
     */
    static {
        System.load("C://SpidarMouseDLL//SpidarMouse.dll");
    }

    public Spidar() {
    }

    /**
     * SpidarMouse�̃l�C�e�B�u�E���\�b�h
     */
    public native int OpenSpidarMouse();
	public native void SetForce(float Force_XScale,float Force_YScale, int duration);
	public native int CloseSpidarMouse();
	public native void SetMinForceDuty(float MinForceDuty);
	public native void SetDutyOnCh(float duty1, float duty2, float duty3, float duty4, int duration);

    public static void main(String[] args) {
        Spidar hello = new Spidar();
        hello.OpenSpidarMouse();
        hello.SetForce(0.0f, 0.5f, 100);

    }
} // SpidarMouse
