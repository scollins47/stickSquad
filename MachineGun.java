import java.util.*;
public class MachineGun extends Gun
{
  
   public MachineGun(short c)//send it the amount of bullets in the gun 
   {
      clipSize = c;//clip size = what you send it
      name = "Machine Gun";
      for(int i =0;i<c;i++)//add the number of bullets to the magazine
         magazine.add(new Bullet("MachineGunClip"));
      Damage = magazine.get(0).getDamage();//damage equals the bullet damage
   }
   public MachineGun()//no argument constructor
   {
      clipSize = 10;//clip size automatically is 20
      for(int i =0;i<10;i++)//fill the magazine with what type of bullet is being used
         magazine.add(new Bullet("MachineGunClip"));
      Damage = magazine.get(0).getDamage();//damage = the bullet damage
   }
 
   public MachineGun(Point p)
   {
      clipSize = 10;//clip size automatically is 20
      for(int i =0;i<10;i++)//fill the magazine with what type of bullet is being used
         magazine.add(new Bullet("MachineGunClip"));
      Damage = magazine.get(0).getDamage();//damage = the bullet damage
      point = p;
   }
   public MachineGun(short c, Point p)//send it the amount of bullets in the gun 
   {
      clipSize = c;//clip size = what you send it
      name = "Machine Gun";
      for(int i =0;i<c;i++)//add the number of bullets to the magazine
         magazine.add(new Bullet("MachineGunClip"));
      Damage = magazine.get(0).getDamage();//damage equals the bullet damage
      point = p;
   }
   
  
 
   
   
}