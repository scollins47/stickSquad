import java.util.*;
public class Pistol extends Gun
{
 
   public Pistol(short c)//send it the amount of bullets in the gun 
   {
      clipSize = c;//clip size = what you send it
      name = "Pistol";
      for(int i =0;i<c;i++)//add the number of bullets to the magazine
         magazine.add(new Bullet("PistolClip"));
      Damage = magazine.get(0).getDamage();//damage equals the bullet damage
   }
   public Pistol()//no argument constructor
   {
      name = "Pistol";
      clipSize = 7;//clip size automatically is 7
      for(int i =0;i<7;i++)//fill the magazine with what type of bullet is being used
         magazine.add(new Bullet("PistolClip"));
      Damage = magazine.get(0).getDamage();//damage = the bullet damage
   }
   public Pistol(Point p)
   {
      name = "Pistol";
      clipSize = 7;//clip size automatically is 7
      for(int i =0;i<7;i++)//fill the magazine with what type of bullet is being used
         magazine.add(new Bullet("PistolClip"));
      Damage = magazine.get(0).getDamage();//damage = the bullet damage
      point = p;
   }
   public Pistol(short c, Point p)//send it the amount of bullets in the gun 
   {
      clipSize = c;//clip size = what you send it
      name = "Pistol";
      for(int i =0;i<c;i++)//add the number of bullets to the magazine
         magazine.add(new Bullet("PistolClip"));
      Damage = magazine.get(0).getDamage();//damage equals the bullet damage
      point = p;
   }
 
   
   
}