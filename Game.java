import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

import java.lang.Runnable;
import java.lang.Thread;

import javax.swing.JFrame;

import javax.imageio.ImageIO;

import java.io.IOException;

public class Game extends JFrame implements Runnable
{
   private Canvas canvas = new Canvas();
   private RenderHandler renderer;
   private BufferedImage testImage;
   
   public Game()
   {
      //Make program shutdown when we exit
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      //Sets position and size of frame
      setBounds(0,0,1000,800);
      
      //Put frame on center of the screen
      setLocationRelativeTo(null);
      
      //Add graphics
      add(canvas);
      
      //Make frame visible
      setVisible(true);
      
      //Creates object for buffer strategy
      canvas.createBufferStrategy(3);
      
      renderer = new RenderHandler(getWidth(),getHeight());
      
      BufferedImage testImage = loadImage("sword.png");
   }
   
   private BufferedImage loadImage(String path)
   {
      try
      {
         BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
         BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB); //formats image from ARGB -> RGB
         formattedImage.getGraphics().drawImage(loadedImage,0,0,null);
         
         return formattedImage;
      }
      catch(IOException exception)
      {
         exception.printStackTrace();
         return null;
      }
   }
   
   public void update()
   {
   
   }
   
  
   public void render()
   {
      BufferStrategy bufferStrategy = canvas.getBufferStrategy();
      Graphics graphics = bufferStrategy.getDrawGraphics();
      super.paint(graphics);
      
      renderer.renderImage(testImage,0,0);
      renderer.render(graphics);
      
      graphics.dispose();
      bufferStrategy.show();
   }
   
   public void run()
   {
      BufferStrategy bufferStrategy = canvas.getBufferStrategy();
      int i = 0;
      int x = 0;
      
      Long lastTime = System.nanoTime(); //Long 2^63
      double nanoSecondConversion = 1000000000.0/60;
      double changeInSeconds = 0;
      
      while(true)
      {
         Long now = System.nanoTime();
         
         changeInSeconds += (now - lastTime) / nanoSecondConversion;
         while(changeInSeconds >= 1)
         {
            update();
            changeInSeconds = 0;
         }
         
         render();
         lastTime = now;
      }
   }
   
   public static void main(String[] args)
   {
      Game game = new Game();
      Thread gameThread = new Thread(game);
      gameThread.start();
   }
}