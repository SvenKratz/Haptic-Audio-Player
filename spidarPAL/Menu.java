import processing.core.*; 

public class Menu
{

  public static final int menuSize = 400; 
  public static final double intensityMax = 500; 
  public  int menuSectors = 0; 

  // sector size 
  public  final double cut = Math.PI / 6; 



  public int cx;
  public int cy;

  public static final int WHITE = 0xffffffff; 

  public PApplet ctx; 
  public double[][] intensities; 
  public PVector[][] forceDirections;

  // force sector
  double[] sectors; 
  
  // target sector (to validate selection) 
  double[] targetSectors; 
  
  private boolean isInitialized = false; 
  
  public String[] menuItems; 
  
  

  public Menu(int x, int y, String[] items, PApplet context)
  {
    cx = x; 
    cy = y; 
    ctx = context; 
    
    menuSectors = items.length; 
    menuItems = items; 

    intensities = new double[menuSize][menuSize]; 
    sectors = new double[menuSectors]; 
    targetSectors = new double[menuSectors];

    forceDirections = new PVector[menuSize][menuSize];

    for (int i = 0; i < menuSectors ; i++)
    {
      final double sect = i * 2 * Math.PI / menuSectors;  
      sectors[i] = sect; 
      
      final double targetSect = sect +   Math.PI / menuSectors ; 
      targetSectors[i] = targetSect; 
      
      System.out.println("Sector: " + sect);
    }

    //drawMenu();
  } 

  //public void draw()
 // {
 // }

  public void draw()
  {
    
    if (!isInitialized)
    {
        System.out.println("Menu drawing (not initialized) ");
      ctx.background(0);

    //ctx.noFill();
    // ctx.stroke(255);
    // ctx.ellipse(cx, cy,  menuSize, menuSize);  
    for (int i =-menuSize / 2; i < menuSize/2; i++)
    {
      for (int j = -menuSize /2 ; j < menuSize/2; j++)
      {

        final int ii = i + menuSize / 2;
        final int jj = j + menuSize / 2;


        int x = cx + i; 
        int y = cy + j;
        double angle = Math.atan2(j, i) + Math.PI ; 
        
        double dxx = cx - x;
        double dyy = cy - y;
        double dist = Math.sqrt(dxx * dxx + dyy * dyy); 
    
        for (int k = 0 ; k < menuSectors; k++)
        {

          double adist =   Math.abs(sectors[k] - angle);


          if ((adist < cut  || 2 * Math.PI - cut < adist) && dist < menuSize /2  )
          {
            // coloring 
            int col = ColorFromAdist(adist); 
            // force intensity
            final double intensity = IntensityFromAdist(adist);
            intensities[ii][jj] = intensity; 

            forceDirections[ii][jj] = getForceDirection(sectors[k], angle, intensity); 

            ctx.set(x, y, col);
          }
          else
          {
          }
          
        }
      }
    }
    
     // draw menu item labels 
    for (int i = 0; i < menuSectors; i++)
    {
      String label = menuItems[i];
      double angle = targetSectors[i];
      
      int y =cy +  (int) ( Math.cos(angle) * (menuSize/2 + 50));
      int x =cx + (int) ( Math.sin(angle) * (menuSize/2 + 50)); 
      
      ctx.textSize(32);
      ctx.fill(255, 102, 255, 255); 
      System.out.println("Text Coords " + x + " " + y); 
      ctx.text(label, x, y); 
    }
    
   
    
    isInitialized = true; 
     
  }
 }

  /****
   * 
   *    calculates the force intensity vector given a sector angle and an intensity 
   * 
   ****/
  public PVector getForceDirection(double sectorAngle, double angle, double intensity)
  {
    double adist =  sectorAngle - angle;
    
    
    if (  2 * Math.PI - cut  < adist)
    {
      //adist -=  (2 * Math.PI-cut);
      //cVal = (int) (Math.floor(  ( 255.0 * adist / cut)));
    }
    double forceAngle =  (adist > 0) ?  sectorAngle + Math.PI/2 : sectorAngle - Math.PI/2;
    
    double vy = Math.sin(forceAngle) * intensity; 
    double vx = Math.cos(forceAngle) * intensity; 
    
    if (  2 * Math.PI - cut  < angle)
    {
      vy *=-1;
      vx *=-1;
    }

    return new PVector((float) vx, (float) vy);
  }


  public double IntensityFromAdist(double adist)
  {
    double intens = 0; 
    if (  2 * Math.PI - cut  < adist)
    {
      adist -=  (2 * Math.PI-cut);
      intens = (int) (Math.floor(  ( intensityMax * adist / cut)));
    }
    else {
      intens = (int) (Math.floor( intensityMax - ( intensityMax * adist / cut)));
    }
    return intens;
  }

  public PVector getForce(int x, int y) 
  {
    if (isInitialized)
    {
      // get offset vs. boundaries 
      int dx =  - cx + x + menuSize/2 ;
      int dy =  - cy + y + menuSize/2 ; 

      System.out.println("Offset x " + dx + " y " + dy + " "); 

      if ( dx < menuSize && dy < menuSize && dx >=0 && dy >=0)
      { 
        PVector force =  forceDirections[dx][dy]; 
        if (force != null)
          System.out.println(" Force x " + force.x + " y " + force.y);
        return force;
      }
      else
      {
        System.out.println("Coords out of bounds!");
        return null;
      }
    }
    else
    {
      System.out.println("Not initialized!!");
      return null;
    }
  }
  
  /*** testif menu is selected or not ***/
  public String testSelection(int x, int y)
  {
    if (isInitialized)
    {
       // get offset vs. boundaries 
      double deltaX =cx -x;
      double deltaY =cy- y; 
     
      double angle = Math.atan2(deltaX, deltaY) + Math.PI ; 
      
       
          
       double targetDistance = Math.sqrt(deltaX*deltaX + deltaY*deltaY); 
       System.out.println("Dx " + deltaX + " dy " + deltaY +" Target Distance " + targetDistance); 

        for (int k = 0 ; k < menuSectors; k++)
        {

          double adist =   Math.abs(targetSectors[k] - angle);
          
          
         
        

          if ((adist < cut  || 2 * Math.PI - cut < adist) && (targetDistance >  menuSize/2))
          {
            final String result = menuItems[k];
            System.out.println("Got selection on Target "  + k +  " : " + result ); 
            return result;
          }
    }
    
    
    
  }
  
  return null; 
  
}
  

  public int ColorFromAdist(double adist)
  {
    int cVal = 0; 
    if (  2 * Math.PI - cut  < adist)
    {
      adist -=  (2 * Math.PI-cut);
      cVal = (int) (Math.floor(  ( 255.0 * adist / cut)));
    }
    else {
      cVal = (int) (Math.floor( 255.0 -  ( 255.0 * adist / cut)));
    }

    int col = 0xff000000  | cVal | cVal << 8 | cVal << 16; 
    return col;
  }
}

