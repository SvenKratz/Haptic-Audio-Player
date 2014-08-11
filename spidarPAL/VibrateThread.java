import processing.core.*;
import processing.SpidarMouse.*;

public class VibrateThread extends Thread
{
  private Spidar mySpider; 
  public VibrateThread (Spidar sm)
  {
     super();
    mySpider = sm; 
   
  }
  
  public void run()
  {
     System.out.println("Starting Vibrate"); 
  
       mySpider.SetForce(100, -100, 10); 
       try {
       sleep(10);
       }
       catch (InterruptedException e)
       {
         
       }
       mySpider.SetForce(-100, 100, 10); 
       try {
       sleep(10);
       }
       catch (InterruptedException e)
       {
         
       }
  }
}
