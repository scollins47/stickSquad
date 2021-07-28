import java.util.*;
public class Shotgun extends Gun
{

   public Shotgun(short c)
   {
      clipSize = c;//clip size = what you send it
      name = "Shotgun";
      for(int i =0;i<c;i++)//add the number of bullets to the magazine
         magazine.add(new Bullet("Shells"));
      Damage = magazine.get(0).getDamage();//damage equals the bullet damage
   }
   public Shotgun(short c,Point p)
   {
      clipSize = c;//clip size = what you send it
      name = "Shotgun";
      for(int i =0;i<c;i++)//add the number of bullets to the magazine
         magazine.add(new Bullet("Shells"));
      Damage = magazine.get(0).getDamage();//damage equals the bullet damage
      point = p;
   }
   public Shotgun()//no argument constructor
   {
      name = "Shotgun";
      clipSize = 5;//clip size automatically
      for(int i =0;i<5;i++)//fill the magazine with what type of bullet is being used
         magazine.add(new Bullet("Shells"));
      Damage = magazine.get(0).getDamage();//damage = the bullet damage
   }
   public Shotgun(Point p)//no argument constructor
   {
      name = "Shotgun";
      clipSize = 5;//clip size automatically
      for(int i =0;i<5;i++)//fill the magazine with what type of bullet is being used
         magazine.add(new Bullet("Shells"));
      Damage = magazine.get(0).getDamage();//damage = the bullet damage
      point =p;
   }

   
   
}