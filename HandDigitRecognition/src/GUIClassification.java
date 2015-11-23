import java.awt.*;  
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.Arrays;

public class GUIClassification {

  public static void main(String args[]){
    final Model M = new Model();
    final BufferedImage img[] = new BufferedImage[10];
    
    //Create window
    JFrame frame = new JFrame();
    
    //Create drawing canvas
    final PadDraw PD = new PadDraw(240,240);
    PD.setPreferredSize(new Dimension(240, 240));
    frame.getContentPane().add(PD,BorderLayout.LINE_START);
    
    //Define Bar Graph
    double [] probs = {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
    for(int d=0;d<10;d++){
      img[d] = util.scaleImage(util.array2imgscale(M.getPXgD(d)),4);
    }            
    final ChartPanelImg CP = new ChartPanelImg(probs, img, "");
    CP.setPreferredSize(new Dimension(400, 240));
    frame.getContentPane().add(CP,BorderLayout.LINE_END);
  
    //Pack controls and finalize window
    frame.pack();
    frame.setSize(644, 240);
    frame.setResizable(false);
    frame.setVisible(true);

    //Window close listener 
    WindowListener wndCloser = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      };
    };
    frame.addWindowListener(wndCloser);

    //Classification lister 
    ActionListener dumpArray = new ActionListener(){   
        @Override
        public void actionPerformed(ActionEvent event){
          BufferedImage img = PD.getImage(12,12);
          int[][] X = util.img2intarray(img);         
          double[] probs = new double[10];
          for(int d=0;d<10;d++){
            probs[d] = Computations.conditionalProbabilityDgX(M,d,X);
          }  
          CP.setValues(probs);           
        }
    };

    //Set timer to run classifier 
    Timer timer = new Timer(500, dumpArray);
    timer.setInitialDelay(0);
    timer.start();
    
  }    
}
   