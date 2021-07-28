import java.util.*;
import java.io.*;
import  javax.swing.Timer;
import javax.swing.ImageIcon;

public class Player
{
   protected Weapon weapon;//which weapon the player has 
   protected String name; //the name of the player
   protected short Health;//the health of the player
   protected Point position;//where they are on the map
   protected final int EAST = 1;//directions
   protected final int WEST = -1;//directions 
   protected boolean isDead;
   private boolean isInAir;
   private final int Delay = 30;
   private boolean toggle = false;
   private boolean toggle2= false;
   private boolean toggle3 = false;
   private boolean toggle4 = false;
   private Bullet temp = null;
   public static final TwoDArrayList<ImageIcon> playersprites = new TwoDArrayList<ImageIcon>();//the array of pictures/sprites
   private int velocityy= 0;
 
   //---------------------------------------------Constructors-----------------------------------------------------------
   public Player(String n)
   {
      isDead = false;
      position = new Point(0,0);
      name = n;
      Health = 100;
      weapon = new fists();
      if(name.equals("Sammy")|| name.equals("Jason"))//cheats :)
      {
         Health = 500;
         weapon = new Laser();
         //Spawn with a Laser
      }
      else
      {
         if(name.equals("Taemin")|| name.equals("5ly7h3r1n")|| name.equals("taemin")||name.equals("tae")||name.equals("taem")||name.equals("taemi"))
         {
            Health = 1;
         }
          
      } 
      
      //loadImages();
      
      
   }
   public Player(String n, Point p)//send it the name and the players position 
   {
      isDead = false;
      name = n;
      Health = 100;
      position = p;
      if(name.equals("Sammy")|| name.equals("Jason"))//cheats :)
      {
         Health = 500;
         weapon = new Laser();
         //Spawn with a Laser
      }
      else
      {
         if(name.equals("Taemin")|| name.equals("5ly7h3r1n")|| name.equals("taemin")||name.equals("tae")||name.equals("taem")||name.equals("taemi"))
            Health = 1;
         
         weapon = new fists();
      }
   }
   //Player( Name, Position, Weapon)
   public Player(String n, Point p,Weapon wep)//used for picking up objects or spawning with items 
   {
      isDead = false;
      name = n;
      weapon = wep;
      position =p;
      Health = 100;
      if(name.equals("Sammy")|| name.equals("Jason"))
      {
         Health = 500;
         weapon = new Laser();
         //Spawn with a Laser
      }
      if(name.equals("Taemin")|| name.equals("5ly7h3r1n")|| name.equals("taemin")||name.equals("tae")||name.equals("taem")||name.equals("taemi"))
         Health = 1;
         
      
      
   } 
   public Player(String n, Weapon wep)
   {
      isDead = false;
      name = n;
      weapon = wep;
      position = new Point(0,0);
      Health =100;
      if(name.equals("Sammy")|| name.equals("Jason"))
      {
         Health = 500;
         weapon = new Laser();
         //Spawn with a Laser
      }
      if(name.equals("Taemin")|| name.equals("5ly7h3r1n")|| name.equals("taemin")||name.equals("tae")||name.equals("taem")||name.equals("taemi"))
         Health = 1;
   } //-----------------------------------------------------------PickUp Methods--------------------------------------------------
   public boolean swapWeapon(Weapon w)//swaps the weapon the player has with another one returns true if successful
   {
      
      if(isBetween(this.position.getX(),w.getPosition().getX(),30) && isBetween(this.position.getY(),w.getPosition().getY(),20)){
         this.weapon = w;
         return true;
      }
      return false;
   }
   
   public Weapon dropWeapon()//throws away the weapon the player currently has and swaps it with fists return 
   {
      
      Weapon temp = weapon;
      (temp).setPoint(new Point(position.getX(),0));
      weapon = new fists();
      
      return temp;
   }
   public boolean pickUp(Weapon w, Map map)//swaps the weapon they have with the weapon sent to it exactly the same as the swap method but for clarification
   {
      if(isBetween(this.position.getX(),w.getPosition().getX(),30) && this.position.getY() == w.getPosition().getY()){
         this.weapon = w;
         return true;
      }
      return false;
   }
   //---------------------------------------------- Get Methods -------------------------------------------
   public Weapon getWeapon()//returns the weapon
   {
      return weapon;
   }
   public String getName()//return name
   {
      return name;
   }
   public short getHealth()//returns the players health
   {
      if(Health<=0)
         Health = 0;
      return Health;
   } 
   public void addHealth(short h)
   {
      if(Health+h<=200)
         Health +=h;
   }
   public Point getPosition()
   {
      return position;
   }
   public int getDirection()
   {
      return position.getDirection();
   }
   public boolean getIsDead()
   {
      return isDead;
   }
   public boolean getisInAir()
   {
      return isInAir;
   }
   public void isOnHealth(ArrayList<HealthPack>list)
   {
      if(list == null)
         return;
      for(HealthPack p:list)
      {
         if(isBetween(p.getPosition().getX(),this.position.getX(),30) && p.getPosition().getY()==this.getPosition().getY())
         {
            this.addHealth(p.getHealth());
            list.remove(p);
            return;
         }
      }
      return;
   }
   public void update(Map map)//
   {
     
      int tempVelocity = 0;
      if(this.errorCheck(this,map))
         tempVelocity++;
      position.setY(position.getY()+(velocityy-tempVelocity));
      isOnHealth(map.getHealthPacks());
      isInAir = true;
      if(checkifStandable(map))
      {
         velocityy = 0;
         isInAir = false;
      }
      if(velocityy<=0)
         for(Point p:map.getPlatformList())
         {
            if(isBetween(this.getPosition().getX(),p.getX(),30)&& isBetween(p.getY(),this.getPosition().getY(),Math.abs(velocityy)))
            {
               this.getPosition().setY(p.getY());
               isInAir = false;
               velocityy = 0;
               break;
            }
            if(p.isLong() && isBetween(this.getPosition().getX(),p.getX(),90)&& isBetween(p.getY(),this.getPosition().getY(),Math.abs(velocityy)))
            {
               this.getPosition().setY(p.getY());
               isInAir = false;
               velocityy = 0;
               break;
            }
         }
      if(toggle&& !checkifStandable(map)&&isInAir)
      {
          //{
         velocityy--;
       //toggle2 = !toggle2;
      }
      toggle= !toggle; 
      
      if(velocityy<=0)
         for(Point p:map.getPlatformList())
         {
         // if(this.getPosition().isEqual(p))
         // {
            //System.out.println(this.getPosition());
         // }  
            if(isBetween(this.getPosition().getX(),p.getX(),30)&& isBetween(p.getY(),this.getPosition().getY(),Math.abs(velocityy)))
            {
               this.getPosition().setY(p.getY());
               isInAir = false;
               velocityy = 0;
               break;
            
            }
         }
    
      
   }
   
   //---------------------------------------------- Move Methods--------------------------------------------
   public void move(Map map, int direction)
   {
      this.errorCheck(this,map);
   
      position.goForwards(direction);//advances the player forwards in the direction that they are facing
      this.errorCheck(this,map);
      //System.out.println(this.position.getX() +" "+ this.position.getY());
   }
   public boolean errorCheck(Player player, Map map)
   {
      boolean temp = false;
      if(player.getPosition().getY()<0)
      {
         player.getPosition().setY(0);
         temp = true;
      }
      if(player.getPosition().getX()<0)
      {
         player.getPosition().setX(0);
         temp = true;
      }
      if(player.getPosition().getY()>=map.getYLength())
      {
         player.getPosition().setY(map.getYLength()-1);
         temp= true;
      }
      if(player.getPosition().getX()>=map.getXLength())
      {
         player.getPosition().setX(map.getXLength()-1);
         temp = true;
      }
      
      return temp;
   }
   public Bullet getBullet()
   {
      return temp;
   }
   public void jump(Map map)
   {
      if(checkifStandable(map))
         velocityy =12;
   }
   public void moveDown()
   {
      position.setY(position.getY()-5);
   }
   public boolean checkifStandable(Map map)
   {
      if(position.getY()<=0)//if they are at the bottom of the map then they can stand on it
         return true;
      this.errorCheck(this,map);
    
      return map.getCellAt(position.getY(),position.getX()).getStandable();//gets the cell that the player is at and checks if it is standable
   }
   public void changeDirection()
   {
      position.changeDirection();//changes the direction of the player (calls the point's change direciton)
   }//----------------------------------------------Damage and stuff-----------------------------------------
   public void takeBulletDamage(Bullet bullet)
   {
      Health-=bullet.getDamage();//reduces the players health by the bullet damage
      if(Health <= 0)
         isDead = true;
   }
   public void takeDamage(Weapon wep)
   {
      Health-=wep.getDamage();
      if(Health<=0)
         isDead = true;
   }
   private boolean isBetween(int num1, int num2, int range)
   {
      if(Math.abs(num1-num2)<range)
         return true;
      return false;
   }
  
   public boolean Attack(Map map,Player p2)//send it the map
   {//---------------------------------------------------------remember when calling getcellat you have to do (y,x) not (x,y) because x would be the row which is the y and vice versa
      p2.errorCheck(p2,map);
      this.errorCheck(this,map);
      boolean toggletemp = false;
      if(weapon instanceof Gun)//if weapon is  a gun
      {
         if(((Gun)this.weapon).getClipSize()==0)//if the gun has no bullets dont shoot aka return false
            return false;
         temp = ((Gun)this.weapon).Shoot();
         if(temp == null)
         {
            return false;
         }
         temp=new Bullet((temp),this.position);//cast weapon into gun and shoot this returns the bullet
         if(temp==null)
            return false;
         if(this.position.getDirection() ==EAST)//get the players direction
         {
            while(/*(map.getCellAt(this.position.getY(),this.position.getX()).getStandable()!=true)&&*/ ((temp.getPoint().getX())<map.getXLength()))
            {//the bullet goes forward while its not at a standable cell
               if(toggletemp)
               {
                  if(isBetween(temp.getPoint().getY(),p2.getPosition().getY(),20)&& (temp.getPoint().getX()==p2.getPosition().getX()))//if the bullet is on a player the player takes damage and ends the loop
                  {
                     p2.takeBulletDamage(temp);
                     break;
                  }
                  temp.getPoint().bgoForwards(this.getPosition().getDirection());
               }
               toggletemp = !toggletemp;
            }
         }
         if(this.position.getDirection() == WEST)//get the players direction
         {
            while(/*(map.getCellAt(this.position.getY(),this.position.getX()).getStandable()!=true)*/(temp.getPoint().getX())>0)
            {//the bullet goes forward while its not at a standable cell or  while it isnt on a player
               if(toggletemp)
               {
                  if(isBetween(temp.getPoint().getY(),p2.getPosition().getY(),20)&& (temp.getPoint().getX()==p2.getPosition().getX()))//if the bullet is on a player the player takes damage and ends the loop
                  {
                     p2.takeBulletDamage(temp);//makes the second player take damage 
                     break;
                  }
                  temp.getPoint().bgoForwards(this.getPosition().getDirection());//advances the bullet forwards in the direction the player is facing 
               }
               toggletemp = !toggletemp;
            }
         }
      }
      
      
      else
      {
         int sh = ((Short)(((MeleeWeapon)(weapon)).getRange())).intValue()*15;
         if(this.position.getDirection()==EAST)
         {
           //gets the int value of the short 
            for(int i =sh+10;i>0;i--)//a for loop to go while the range is not 0
            {
               if(isBetween(this.getPosition().getY(),p2.getPosition().getY(),20)&&this.position.getX()+i==p2.position.getX())//if the position  of the player is between their position and the range of the weapon 
               {
                  p2.takeDamage(weapon);//make the second player take damage
                  break;
               }
            }
         }
         if(this.position.getDirection()==WEST)
         {
            for(int i=sh+10;i>0;i--)
            {
               if(isBetween(this.getPosition().getY(),p2.getPosition().getY(),20)&&this.position.getX()-i==p2.position.getX())//if the position  of the player is between their position and the range of the weapon 
               {
                  p2.takeDamage(weapon);//make the second player take damage
                  break;
               }
            } 
         }
      }
      return true;
   }
}