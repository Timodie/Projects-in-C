import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;  
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;

public class ChartPanelImg extends JPanel {
  private double[] values;

  private final BufferedImage[] img;

  private String title;

  public ChartPanelImg(double[] v, final BufferedImage[] i, String t) {
    img = i;
    values = v;
    title = t;
  }

  public void setValues(double[] v) {
    values = v;
    this.repaint();
  }  

  public void setTitle(String t) {
    title=t;
    this.repaint();
  }  
  
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (values == null || values.length == 0)
      return;
    double minValue = 0.0;
    double maxValue = 1.0;
    //for (int i = 0; i < values.length; i++) {
    //  if (minValue > values[i])
    //    minValue = values[i];
    //  if (maxValue < values[i])
    //    maxValue = values[i];
    //}

    Dimension d = getSize();
    int clientWidth = d.width;
    int clientHeight = d.height;
    int barWidth = clientWidth / values.length;

    Font titleFont = new Font("SansSerif", Font.BOLD, 20);
    FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
    Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
    FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

    int titleWidth = titleFontMetrics.stringWidth(title);
    int y = titleFontMetrics.getAscent();
    int x = (clientWidth - titleWidth) / 2;
    g.setFont(titleFont);
    g.drawString(title, x, y);

    int top = titleFontMetrics.getHeight();
    int bottom = 55;
    if (maxValue == minValue)
      return;
    double scale = (clientHeight - top - bottom) / (maxValue - minValue);
    y = clientHeight - 100;
    g.setFont(labelFont);

    for (int i = 0; i < values.length; i++) {
      int valueX = i * barWidth + 1;
      int valueY = top;
      int height = (int) (values[i] * scale);
      if (values[i] >= 0)
        valueY += (int) ((maxValue - values[i]) * scale);
      else {
        valueY += (int) (maxValue * scale);
        height = -height;
      }

      g.setColor(Color.blue);
      g.fillRect(valueX, valueY, barWidth - 2, height);
      g.setColor(Color.black);
      g.drawRect(valueX, valueY, barWidth - 2, height);
      
      int labelWidth = 12*4;
      x = i * barWidth + (barWidth - labelWidth) / 2;
      
      y=clientHeight -50;
      g.drawImage(img[i], x, y, null);
    }
  }

}