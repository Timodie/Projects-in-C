import java.awt.*;  
import javax.swing.*;
import java.awt.image.*;
import java.awt.image.ColorModel.*;
import java.io.*;
import javax.imageio.*;

public class util{


  //Convert a 2D array of doubles to a buffered image
  //Scale the array so it is between 0 and 1 before 
  //converting to an image
  public static final BufferedImage array2imgscale(double[][] X){
    int H = X.length;
    int W = X[0].length;
    final BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = (Graphics2D)img.getGraphics();
    
    double max = X[0][0];
    for(int i = 0; i < W; i++) {
      for(int j = 0; j < H; j++) {
        if(X[i][j]>max){
          max = X[i][j];
        }
      }  
    }
    
    for(int i = 0; i < W; i++) {
      for(int j = 0; j < H; j++) {
        float c = (float) (X[i][j]/max);
        g.setColor(new Color(c, c, c));
        g.fillRect(j, i, 1, 1);
      }
    }  
    return(img);
  } 

  //Convert a 2D array of doubles to a buffered image
  //Scale the array so it is between 0 and 1 before 
  //converting to an image
  public static final BufferedImage array2imgscale(int[][] X){
    int H = X.length;
    int W = X[0].length;
    final BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = (Graphics2D)img.getGraphics();
    
    double max = X[0][0];
    for(int i = 0; i < W; i++) {
      for(int j = 0; j < H; j++) {
        if(X[i][j]>max){
          max = X[i][j];
        }
      }  
    }
    
    for(int i = 0; i < W; i++) {
      for(int j = 0; j < H; j++) {
        float c = (float) (X[i][j]/max);
        g.setColor(new Color(c, c, c));
        g.fillRect(j, i, 1, 1);
      }
    }  
    return(img);
  } 
  
  
  
  public static void dumpImgArray(double[][] X){
    int H = X.length;
    int W = X[0].length;
    
    for(int i = 0; i <H; i++) {
      for(int j = 0; j < W; j++) {
        System.out.print(X[i][j]+ " ");
      }
      System.out.print("\n");
    }    
    System.out.print("\n");
  }
  
  //Convert a buffered image to a binary array
  //Any non-black pixel becomes white
  public static int[][]  img2intarray(BufferedImage img){
    int H = img.getHeight();
    int W = img.getWidth();
    int[][] X = new int[H][W];
    
    Graphics2D g = (Graphics2D)img.getGraphics();    
    
    for(int i = 0; i < H; i++) {
      for(int j = 0; j < W; j++) {
      
        int rgb  = img.getRGB(j,i);
        double pixel = (rgb >> 16) & 0x000000FF;
      
        if(pixel>0){
          X[i][j]=1;
        }  
        else{
          X[i][j]=0;
        }
      }
    }  
    return(X);
  } 
  

  //Convert a buffered image to a binary array
  //Any non-black pixel becomes white
  public static double[][]  img2array(BufferedImage img){
    int H = img.getHeight();
    int W = img.getWidth();
    double[][] X = new double[H][W];
    
    Graphics2D g = (Graphics2D)img.getGraphics();    
    
    for(int i = 0; i < H; i++) {
      for(int j = 0; j < W; j++) {
      
        int rgb  = img.getRGB(j,i);
        double pixel = (rgb >> 16) & 0x000000FF;
      
        if(pixel>0){
          X[i][j]=1;
        }  
        else{
          X[i][j]=0;
        }
      }
    }  
    return(X);
  } 
  
  
  
  //Scale a buffered image to make it larger
  public static BufferedImage scaleImage(BufferedImage img,float scale){
   int scaleX = (int) (img.getWidth() * scale);
   int scaleY = (int) (img.getHeight() * scale);
   Image image = img.getScaledInstance(scaleX, scaleY, Image.SCALE_SMOOTH);
   BufferedImage buffered = new BufferedImage(scaleX, scaleY, BufferedImage.TYPE_INT_ARGB);
   buffered.getGraphics().drawImage(image, 0, 0 , null);
   return(buffered);
  }
  
  public static void writeImage(BufferedImage img, String fname){
    try {
      File outputfile = new File(fname);
      ImageIO.write(img, "png", outputfile);
    } 
      catch (IOException e) {
      System.out.printf("Could not write image file: " + fname);
    }
  }
}
