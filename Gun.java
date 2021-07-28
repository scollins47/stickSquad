import java.util.*;
abstract class Gun extends Weapon
{
   protected Point point;
   protected short clipSize;//every gun has a clip size
   protected short Damage;
   protected int frameRemoved;
   protected ArrayList<Bullet>magazine = new ArrayList<Bullet>();//the clip of the gun filled with the bullets

   public Gun()
   {
      isMelee = false;//guns are not melee weapons
   }
   public short getClipSize()//returns the clipSize
   {
      return clipSize;
   }

   public short getDamage()//returns the damage
   {
      return Damage;
   }
   public Bullet Shoot()
   {
      
      if(magazine.size()==0)//if there are no bullets in the magazine return null
         return null;
   
      return magazine.remove(0);//else return and remove the bullet at the first possible index
   }
   public int getMagazine()
   {
      return magazine.size();
   }
   public Point getPosition()
   {
      return point;
   }
   public void setPoint(Point p)
   {
      point = new Point(p.getX(),p.getY());
   }
   
   //abstract Bullet Shoot();//every gun should be able to shoot... duh
   
}