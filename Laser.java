import java.util.*;
public class Laser extends Gun
{
  
   public Laser()//no argument constructor
   {
      clipSize = 1;//clip size automatically is 1
      magazine.add(new Bullet("Laser"));
      Damage = magazine.get(0).getDamage();//damage = the bullet damage
      name = "Laser";
   }
   public Laser(Point p)
   {
      clipSize = 1;
      magazine.add(new Bullet("Laser"));
      Damage = magazine.get(0).getDamage();
      name = "Laser";
      point = p;
   }  
     
}