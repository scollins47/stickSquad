import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler
{
   private BufferedImage view;
   private int[] pixels;
   
   public RenderHandler(int width,int height)
   {
      //Create a BufferedImage that will represent our view
      view = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
      
      //Create an array for pixels
      pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
      
      // for(int heightIndex = 0;heightIndex < height;heightIndex++)
//       {
//          int randomPixel = (int)(Math.random()*0xFFFFFF); //0xF highest pixel value we can have
//          
//          for(int widthIndex = 0;widthIndex < width; widthIndex++)
//          {
//             pixels[heightIndex*width + widthIndex] = randomPixel;
//          }
//       }
   }
   
   //Renders pixel array to screen
   public void render(Graphics graphics)
   {
      graphics.drawImage(view,0,0,view.getWidth(),view.getHeight(),null);
   }
   
   //Renders image to array of pixels
   public void renderImage(BufferedImage image, int xPos, int yPos)
   {
      int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData(); //array of pixels for the image
      
      for(int y=0;y<image.getHeight();y++)
      {
         for(int x = 0;x<image.getWidth();x++)
         {
            pixels[(x+xPos) + (y+yPos) * view.getWidth()] = imagePixels[x + y * image.getWidth()];
         }
      }
   }
}