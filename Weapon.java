public class Weapon
{
   protected short Damage;//every weapon should have damage
   protected boolean isMelee;//every weapon should be melee or ranged
   protected String name;//every weapon should have a range
   protected Point point;
   protected int frameRemoved;
   public String getName()//return the the name of the weapon
   {
      return name;
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
      return point;
   }
   public void setPoint(Point p)
   {
      point = new Point(p.getX(),p.getY());
   }
   public short getDamage()
   {
      return Damage;
   }
  //return the damage that the weapon does
   //abstract void Use();

   
}