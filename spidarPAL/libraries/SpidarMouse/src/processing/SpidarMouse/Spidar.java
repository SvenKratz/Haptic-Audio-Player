package processing.SpidarMouse;

public class Spidar {
    /*
     * ネイティブ・メソッドの実装は共有ライブラリに書かれているので、
     * ロードしなければならない。ネイティブ・メソッドを呼ぶ前にロード
     * しておけばよいので、通常はstaticコードで行う。
     */
    static {
        System.load("C://SpidarMouseDLL//SpidarMouse.dll");
    }

    public Spidar() {
    }

    /**
     * SpidarMouseのネイティブ・メソッド
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
