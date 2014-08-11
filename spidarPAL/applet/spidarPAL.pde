import processing.SpidarMouse.*;

Spidar spider = new Spidar();
PImage initial;
PImage currentImage; 

final int w = 1366;
final int h = 768; 

final int menuSize = 250; 

PVector center;

Menu centerMenu; 



Settings.DisplayState dispState; 

void setup()
{
  colorMode(RGB, 255,255,255);
  size(w, h);
  initial = loadImage("initial.png"); 
  currentImage = initial; 
  spider.OpenSpidarMouse();
  dispState = Settings.DisplayState.INITIAL; 

  center = new PVector(w/2, h/2);
  
  centerMenu = new Menu(w/2, h/2, this); 
}


void draw()
{
  if (dispState == Settings.DisplayState.INITIAL) 
  {
    background(0); 
    image(currentImage, 0, 0);
  }
  else if (dispState == Settings.DisplayState.MENU)
  {  
    background(0); 
    centerMenu.draw(); 
  }
  else
  {
    background(0);   
  }
}

 

void mouseMoved()
{
    onMouse();
}

void mouseDragged()
{
  
  onMouse();
}

void onMouse()
{
  if (dispState == Settings.DisplayState.INITIAL)
  {
    spider.OpenSpidarMouse();
    int x = mouseX; 
    int y = mouseY; 
  
    int c = currentImage.get(x, y) & 0xff; 
  
    println("Mouse: " + mouseX + " " + mouseY + " color "+ Integer.toHexString(c));
  
    //spider.SetForce(0.0,0.0, c); 
  
    float dx = center.x - x; 
    float dy = center.y - y; 
  
    float d = sqrt((float)(dx*dx + dy*dy));
  
    float max_dist = sqrt(w/2 * w/2 + h/2 * h/2); 
  
    dx = 500 * dx / max_dist;
    dy = 500 * dy / max_dist;
  
    println("dx " + dx + " dy " + dy); 
    if (d >100)
    {
      spider.SetForce(dx, dy, 50);
    }
    else
    {
     dispState = Settings.DisplayState.MENU;  
    }
  }
}


void stop()
{
  spider.CloseSpidarMouse();
}

