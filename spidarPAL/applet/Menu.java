import processing.core.PApplet; 
public class Menu
{
  
  public static final int menuSize = 250; 
  
  public static final int menuSectors = 4; 
  
  // sector size 
  public  final double cut = Math.PI / 8; 
  
  public int cx;
  public int cy;
  
  public static final int WHITE = 0xffffffff; 

 public PApplet ctx; 

 public double[][] distances; 
 
 double[] sectors; 
 
 public Menu(int x, int y, PApplet context)
  {
     cx = x; 
     cy = y; 
     ctx = context; 
     
     distances = new double[menuSize][menuSize]; 
     
     sectors = new double[menuSectors]; 
     
     for (int i = 0; i < menuSectors ; i++)
     {
        double sect = i * 2 * Math.PI / menuSectors;  
        sectors[i] = sect; 
        System.out.println("Sector: " + sect); 
     }
     
  } 
  
  public void draw()
  {
    ctx.background(0);
    
    //ctx.noFill();
   // ctx.stroke(255);
   // ctx.ellipse(cx, cy,  menuSize, menuSize);  
    for (int i =-menuSize / 2; i < menuSize/2; i++)
    {
       for (int j = -menuSize /2 ; j < menuSize/2; j++)
       {
        
          int x = cx + i; 
          int y = cy + j;
          double angle = Math.atan2(j,i) + Math.PI ; 
         
         
         for (int k = 0 ; k < menuSectors; k++)
          {
             
             double adist =   Math.abs(sectors[k] - angle);
          
          
            if (adist < cut  || 2 * Math.PI - cut < adist )
            {
                int col = ColorFromAdist(adist); 
                ctx.set(x,y, col);  
            }
          }
       } 
    }
  }
  
  public int ColorFromAdist(double adist)
  {
     if (  2 * Math.PI - cut < adist)
     {
        adist =  (2 * Math.PI-cut) - adist;
     }
     
     int cVal = (int) ( 255.0 -  ( 255.0 * adist / cut)); 
     
     
     
     int col = 0xff000000  | cVal | cVal << 8 | cVal << 16; 
     return col;
  }
}
  

