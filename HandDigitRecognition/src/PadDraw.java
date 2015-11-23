import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.lang.Math.*;

class PadDraw extends JComponent{
  Image image;
  //this is gonna be your image that you draw on
  Graphics2D graphics2D;
  //this is what we'll be using to draw on
  int currentX, currentY, oldX, oldY;
  //these are gonna hold our mouse coordinates

  int maxX, maxY;
  
  
  //Now for the constructors
  public PadDraw(int mY, int mX){
    
    maxX = mX;
    maxY = mY;
    
    setDoubleBuffered(false);
    addMouseListener(new MouseAdapter(){
      public void mousePressed(MouseEvent e){
        oldX = e.getX();
        oldY = e.getY();
        
        if(e.getClickCount()>=2){
          clear();
        }
        else if(oldX<maxX & oldY<maxY){
          graphics2D.fillRect(20*(oldX/20), 20*(oldY/20), 20, 20);
          repaint();
        }
      }
    });
    //if the mouse is pressed it sets the oldX & oldY
    //coordinates as the mouses x & y coordinates
    addMouseMotionListener(new MouseMotionAdapter(){
      public void mouseDragged(MouseEvent e){
        currentX = e.getX();
        currentY = e.getY();
        if(graphics2D != null)
        //graphics2D.drawLine(oldX, oldY, currentX, currentY);

        if(currentX<maxX & currentY<maxY){
          graphics2D.fillRect(20*(oldX/20), 20*(oldY/20), 20, 20);
          repaint();
        }
        
        oldX = currentX;
        oldY = currentY;
      }

    });
    
    //while the mouse is dragged it sets currentX & currentY as the mouses x and y
    //then it draws a line at the coordinates
    //it repaints it and sets oldX and oldY as currentX and currentY

  }

  //this is the painting bit
  //if it has nothing on it then
  //it creates an image the size of the window
  //sets the value of Graphics as the image
  //sets the rendering
  //runs the clear() method
  //then it draws the image  
  public void paintComponent(Graphics g){
    if(image == null){
      image = createImage(getSize().width, getSize().height);
      graphics2D = (Graphics2D)image.getGraphics();
      graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      clear();

    }
    g.drawImage(image, 0, 0, null);
  }

  public void clear(){
    graphics2D.setPaint(Color.black);
    graphics2D.fillRect(0, 0, getSize().width, getSize().height);
    graphics2D.setPaint(Color.white);
    repaint();
  }

  public BufferedImage getImage(int H, int W){
    BufferedImage buffered = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
    buffered.getGraphics().drawImage(image, 0, 0 , W, H, null);
    return(buffered);
  }
}