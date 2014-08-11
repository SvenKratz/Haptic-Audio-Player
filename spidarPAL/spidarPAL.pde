import processing.SpidarMouse.*;

Spidar spider = new Spidar();
PImage initial;
PImage currentImage; 

final int w = 1366;
final int h = 768; 

final int menuSize = 250; 

PVector center;

Menu centerMenu; 
Menu currentMenu; 

VibrateThread vibrate; 

String commandName = null;
int commandDwell = 0; 

String lastItem;

boolean playing = false; 
boolean firstTime = true; 

Settings.DisplayState dispState; 

VLCControl vlc;

void setup()
{
  colorMode(RGB, 255, 255, 255);
  size(w, h);
  initial = loadImage("initial.png"); 
  currentImage = initial; 
  spider.OpenSpidarMouse();
  //vibrateThread = new VibrateThread(spider);
  dispState = Settings.DisplayState.INITIAL; 

  center = new PVector(w/2, h/2);

 vlc = new VLCControl();
 try { 
 vlc.connect();
 
 vlc.sendCommand("enqueue Kalimba.mp3");
  //
  vlc.sendCommand("enqueue hooked.mp3");
  vlc.sendCommand("enqueue turca.mp3"); 


 }
 catch (IOException ex)
 {
    println("Could not connect to VLC");  
 }
  // centerMenu = new Menu(w/2, h/2, this);
  

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
    //background(0); 
    currentMenu.draw();
  }
  else
  {
    background(0);
  }
}


public void selectMenuItem(String item)
{
   println("Selected item: " + item); 
   
   try {
   if (item.equals("PAUSE"))
   {
     if (playing)
     {
      vlc.sendCommand("pause"); 
      playing = false;  
     }
   }
   else if (item.equals("STOP"))
   {
     vlc.sendCommand("stop");  
   }
   else if (item.equals("PLAY"))
   {
     if (firstTime)
     {
        vlc.sendCommand("play");
      playing = true; 
        firstTime= false;
     }
     if (!playing)
     {
        vlc.sendCommand("pause");  
        playing = true;
     }
     
   }
   else if (item.equals("PREV"))
   {
    vlc.sendCommand("prev"); 
   }
   else if (item.equals("NEXT"))
   {
    vlc.sendCommand("next"); 
   }
   
   }
   catch (IOException ex)
   {
    println(ex); 
   }
   commandName = null;
   commandDwell = 0;  
}

/***
  callback from menus 
*/
 public void onMenuItemSelected(String item, Menu menu)
 {
   
    VibrateThread vt = new VibrateThread(spider); 
    vt.run();
    
    if (commandName == null)
    {
     commandName = item;
     commandDwell = 0;  
    }
    else if (commandName.equals(item))
    {
     commandDwell++; 
     if (commandDwell > 12 )
     {
       selectMenuItem(item);  
       lastItem = item; 
     }
    }
    else
    {
     commandName = item;
     commandDwell = 0 ;  
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
  final int x = mouseX; 
  final int y = mouseY; 
  if (dispState == Settings.DisplayState.INITIAL)
  {
    int c = currentImage.get(x, y) & 0xff; 
    println("Mouse: " + mouseX + " " + mouseY + " color "+ Integer.toHexString(c));

    //spider.SetForce(0.0,0.0, c); 

    float dx = center.x - x; 
    float dy = center.y - y; 

    float d = sqrt((float)(dx*dx + dy*dy));

    float max_dist = sqrt(w/2 * w/2 + h/2 * h/2); 

    dx = 500 * dx / max_dist;
    dy = 500 * dy / max_dist;

    println("dx " + dx + " dy " + dy + " " + d); 
    if (d >100)
    {
      spider.SetForce(dx, dy, 50);
    }
    else 
    {
      println("activating menu");
      dispState = Settings.DisplayState.MENU;
      //centerMenu.drawMenu();  
      println("activating menu 2");
      if ( currentMenu == null)
      {
        currentMenu = new Menu(w/2, h/2, new String[]{"PLAY", "PAUSE", "PREV", "NEXT",}, this);
           println("activating menu 3");
      }
      println("done activating menu");
      return;
    }
  }
  else if (dispState == Settings.DisplayState.MENU) //// MENUJ Mode
  {
    println("Here");
    PVector force = currentMenu.getForce(x, y);
    if ( force != null)
    {
          spider.SetForce(force.x, force.y, 50);
    }
    else
    {
        spider.SetForce(0,0, 10);
    }
    final String selection = currentMenu.testSelection(x,y); 
    println("Current selection is: " + selection); 
    if (selection != null)
    {
     onMenuItemSelected(selection, currentMenu); 
    }
  }
}





void stop()
{
  spider.CloseSpidarMouse();
  try {
  vlc.disconnect();}
  catch (IOException ex)
  {}
}

