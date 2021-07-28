public class HealthPack
{
   private short Health;
   private Point position;
   private int frameRemoved;
   public HealthPack()
   {
      Health = 25;
   }
   public HealthPack(Point p)
   {
      position = p;
      Health = 25;
   }
   public HealthPack(short h, Point p)
   {
      Health = h;
      position = p;
   }
   public void setFrameRemoved(int frame)
   {
      frameRemoved = frame;
   }
   public int getFrameRemoved()
   {
      return frameRemoved;
   }
   public Point getPosition()
   {
      return position;
   }
   public short getHealth(){
      return Health;
   }
   public void setPosition(Point p)
   {
      position = p;
   }
   public void setHealth(short h)
   {
      Health = h;
   }
}