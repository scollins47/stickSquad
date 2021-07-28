import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.HashSet;

import java.util.ArrayList;

public class KelvinPanel extends JPanel implements KeyListener{
   private int index = 1;// player image index for player 1
   private int index2 = 1;//player image index for player 2
   private BufferedImage background = loadImage("wpnsf/images2.jpg");
   private boolean canShoot1 = true; 
   private boolean toggle = false;
   private boolean toggle2 = false;
   private boolean toggle3 = false;
   private boolean canShoot2 = true;
   private boolean isShooting1 = false;//variable that changes if player 1 is shooting
   private boolean isShooting2 = false;//variable that changes if player 2 is shooting
   private boolean dropping1 = false;//variable that changes to true if player 1 dropped a weapon, false if they pickup weapon
   private boolean dropping2 = false;//variable that changes to true if player 2 dropped a weapon, false if they pickup weapon
  
   private ArrayList<Weapon> droppedWeapons = new ArrayList<Weapon>();//player 1 dropped weapons
   private BufferedImage platform = loadImage("wpnsf/platform.png");
   private BufferedImage LongPlatform  = loadImage("wpnsf/platformlong.png");
   private BufferedImage platformSpawn = loadImage("wpnsf/platformSpawn.png");
   
   private Map map;// the map for the game
   private ArrayList<Player> players= new ArrayList<Player>();//the array of players (for as many players as possible)
   private int player1PercentHP = 100;
   private int player2PercentHP= 100;
   private final int WEST =-1;//west final variable
   private final int EAST = 1;//east final variable
   private final int PLAYER1 = 0;//player 1 index
   private final int PLAYER2 = 1;//player 2 index
   private static ArrayList<Bullet>bullets;
   private static HashSet<Integer> keys;
   private Timer t;							//used to set the speed of the enemy that moves around the screen
   private static final int DELAY= (int)(1000/120);	//#miliseconds delay between each time the enemy moves and screen refreshes for the timer
   private static int frame;
   private static ArrayList<Point>platforms;
   private static ArrayList<HealthPack>healthpacks;
   private BufferedImage[] playerImages = new BufferedImage[] 
   {
     loadImage("wpnsf/playersprites/playersprite3.png"),//index 0 facing left
     loadImage("wpnsf/playersprites/playersprite1.png"),//index 1 nuetral
     loadImage("wpnsf/playersprites/playersprite2.png"),//index 2 facing right
     loadImage("wpnsf/playersprites/playerspritejumping.png"),//index 3 jumping right
     loadImage("wpnsf/playersprites/playerspritejumpingleft.png"),//index 4 jumping left
     loadImage("wpnsf/playersprites/playerspriteDead.png"),//index 5 dead
   };
   
   public KelvinPanel(String p1name, String p2name)
   {
      map = new Map();//creating the new map
      map.mapSetup();
      bullets = new ArrayList<Bullet>();
      platforms = (ArrayList<Point>)map.getPlatformList().clone();
      this.addKeyListener(this);//adding key listener
      this.setFocusable(true);//dont know what this does
      this.requestFocus();//same here
      keys = new HashSet<Integer>();
      t = new Timer(DELAY, new Listener());	
      healthpacks = new ArrayList<HealthPack>();
     		//the higher the value of the first argument, the slower the enemy will move
      t.start();
      frame =0;
      
      players.add(new Player((p1name),new Point(50,100)));
      players.add(new Player((p2name),new Point(1550,100)));
   }
   
   private class Listener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)	//this is called for each timer iteration - make the enemy move randomly
      {
         frame++;
        
         if(frame == Integer.MAX_VALUE)
            frame =0;
         for(int i=0;i<droppedWeapons.size();i++)
         {
         
            if(frame>droppedWeapons.get(i).getFrameRemoved()+1000)
            {
               //System.out.println("REMOVED WEAPON");
               droppedWeapons.remove(i);
            }
         }
         for(int i=0;i<healthpacks.size();i++)
         {
         
            if(frame>healthpacks.get(i).getFrameRemoved()+1000)
            {
               //System.out.println("REMOVED WEAPON");
               healthpacks.remove(i);
            }
         }
         // for(int i =0;i<players.size();i++)//loop through the player arraylist and update the players position
         // {
            // players.get(i).update(map);
         // }
        
         movePlayers();
         for(int i =0;i<players.size();i++)//loop through the player arraylist and update the players position
         {
            players.get(i).update(map);
         }
      
         moveBullets();
         repaint();
      }
   }
   public void moveBullets()
   {
      for(Bullet b:bullets)
      {
         b.getPoint().bgoForwards(b.getPoint().getDirection());
      }
   }
  
   
   public void paintComponent(Graphics g)
   {
      if((droppedWeapons.size()<6))
         SpawnWeapon();
     
      g.drawImage(background,0,0,getWidth(),getHeight(),null);
              // for(int i =0;i<map.getPlatformList().size();i++)
      // {
         // g.drawRect(map.getPlatformList().get(i).getX(),map.getPlatformList().get(i).getY(),100,100);
         // g.fillRect(map.getPlatformList().get(i).getX(),map.getPlatformList().get(i).getY(),100,100);
      // }
      for(Point p:platforms)
      {
         if(p.isShort())
            g.drawImage(platform,p.getX(),getHeight()-p.getY()-getHeight()/10,null);
         if(p.isLong())
            g.drawImage(LongPlatform,p.getX(),getHeight()-p.getY()-getHeight()/10,null);
         if(p.isSpawn())
            g.drawImage(platformSpawn,p.getX(),getHeight()-p.getY()-getHeight()/10,null);
      }
     
      int tempindex = index;//temporary index to be changed for player 1
      int tempindex2 = index2;//temporary index to be changed for player 2
      
      //Displays the health of each player
      g.setColor(Color.white);
      g.setFont(new Font("Courier New", Font.BOLD, 16));
      
      //Kelvin made this
      FontMetrics fm = g.getFontMetrics();
      //Kelvin did not make the rest
      
      g.drawString("Player 1: "+players.get(PLAYER1).getName(),20,25);
      g.drawString("HEALTH: " +players.get(PLAYER1).getHealth(),20,55);
      
      g.drawString("Player 2: "+players.get(PLAYER2).getName(),getWidth()-fm.stringWidth("Player 2: "+players.get(PLAYER2).getName()) - 20,25);
      g.drawString("HEALTH: " +players.get(PLAYER2).getHealth(), getWidth()-fm.stringWidth("HEALTH: " +players.get(PLAYER2).getHealth()) - 20,55);   
      
      //g.drawRect(players.get(PLAYER1).getPosition().getX(),getHeight()-players.get(PLAYER1).getPosition().getY()-10,10,20);
      if(players.get(PLAYER1).getWeapon() instanceof Gun)
      {
         g.drawString("AMMO: " +((Gun)(players.get(PLAYER1).getWeapon())).getMagazine()+" / "+((Gun)(players.get(PLAYER1).getWeapon())).getClipSize(),20,90);
      }
      if(players.get(PLAYER2).getWeapon() instanceof Gun)
      {
         g.drawString("AMMO: " +((Gun)(players.get(PLAYER2).getWeapon())).getMagazine()+" / "+((Gun)(players.get(PLAYER2).getWeapon())).getClipSize(),getWidth()-130,90);
      }
        
      if(players.get(PLAYER1).getisInAir())//if the player is in the air 
      {
         if(players.get(PLAYER1).getisInAir()&& index == 2)//if their image is facing left 
            tempindex  =3;//set the facing left jump animation
         else 
            if(players.get(PLAYER1).getisInAir()&& index ==0)//if their index is facing right
               tempindex = 4;//set the facing right jump animation
      }
      else
         if(players.get(PLAYER2).getisInAir())//if player 2 is in the air
         {
            if(players.get(PLAYER2).getisInAir()&& index2 == 2)//if the players index is facing the left
               tempindex2 =3;//set it to the jumping left animation
            else 
               if(players.get(PLAYER2).getisInAir()&& index2 ==0)//if it is facing right
                  tempindex2 = 4;//set it to the jumping right animation
         }
         else
         {
            index = tempindex;//set the primary indexs to the temporary indexs
            index2 = tempindex2;
         }
      map.setHealths(healthpacks);
      for(HealthPack p:healthpacks)
      {
         g.drawImage(loadImage("wpnsf/HealthPack.png"),p.getPosition().getX()+30,map.getYLength()-p.getPosition().getY()+50,null);
      }
   
      for(int temporary = 0;temporary<droppedWeapons.size();temporary++)
      {
         if(droppedWeapons.get(temporary) instanceof MachineGun)//and the dropped weapon is a machine gun
            g.drawImage(loadImage("wpnsf/ArRight/ar1.png"),(droppedWeapons.get(temporary)).getPosition().getX(),map.getYLength()- (droppedWeapons.get(temporary)).getPosition().getY()+40,null);
         if(droppedWeapons.get(temporary) instanceof Sword)//and the dropped weapon is a machine gun
            g.drawImage(loadImage("wpnsf/sword/SwordR.png"),(droppedWeapons.get(temporary)).getPosition().getX(),map.getYLength()- (droppedWeapons.get(temporary)).getPosition().getY()+40,null);
         if(droppedWeapons.get(temporary) instanceof Pistol)//and the dropped weapon is a machine gun
            g.drawImage(loadImage("wpnsf/pistolRight/pistol1.png"),(droppedWeapons.get(temporary)).getPosition().getX(),map.getYLength()- (droppedWeapons.get(temporary)).getPosition().getY()+40,null);
         if(droppedWeapons.get(temporary) instanceof Laser)//and the dropped weapon is a machine gun
            g.drawImage(loadImage("wpnsf/Laser/lazer1.png"),(droppedWeapons.get(temporary)).getPosition().getX(),map.getYLength()- (droppedWeapons.get(temporary)).getPosition().getY()+40,null);
      }
      if(players.get(PLAYER1).getIsDead())//if player 1 is dead 
      {
         g.drawImage(playerImages[5],players.get(PLAYER1).getPosition().getX(),map.getYLength()+30-players.get(PLAYER1).getPosition().getY(),null);
      }//set the players index to the dead index
      else
      {//draw the image of player 1
         g.drawImage(playerImages[tempindex],players.get(PLAYER1).getPosition().getX(),map.getYLength()-1- players.get(PLAYER1).getPosition().getY(), null);
         if(!(players.get(PLAYER1).getWeapon() instanceof fists))//if player1's weapon isnt a fist
         {
            if(players.get(PLAYER1).getWeapon() instanceof MeleeWeapon)//if the player has a melee wepaon
            {
               if(players.get(PLAYER1).getDirection()==EAST)//and the player is facing east
               {
                  if(players.get(PLAYER1).getWeapon() instanceof Sword)//and the player has a sword
                  {
                     g.drawImage(loadImage("wpnsf/sword/swordR.png"),players.get(PLAYER1).getPosition().getX()+20,map.getYLength()-players.get(PLAYER1).getPosition().getY()+10,null);
                   
                  }
               }//draw the image of the sword facing Right
               if(players.get(PLAYER1).getDirection()==WEST)//if the player is facing west
               {
                  if(players.get(PLAYER1).getWeapon() instanceof Sword)//and they have a sword
                     g.drawImage(loadImage("wpnsf/sword/swordL.png"),players.get(PLAYER1).getPosition().getX()-13,map.getYLength()-players.get(PLAYER1).getPosition().getY()+13,null);
               //draw a sword facing left
               }
            }
            else
            {            
               // if(!canShoot1)//if they are not able to shoot
               // {
                  // g.setColor(Color.black);//set the graphics color to black
                  // g.drawString("EMPTY", players.get(PLAYER1).getPosition().getX()+20,getHeight()-players.get(PLAYER1).getPosition().getY()-40);// draw Empty over their player
               // }
               if(players.get(PLAYER1).getDirection()== EAST)//if the player is facing east
               {
                  if(players.get(PLAYER1).getWeapon() instanceof Laser)
                     g.drawImage(loadImage("wpnsf/Laser/lazer1.png"),players.get(PLAYER1).getPosition().getX()+30,map.getYLength()-players.get(PLAYER1).getPosition().getY()+30,null);
                  else
                  {
                     if(players.get(PLAYER1).getWeapon() instanceof MachineGun)
                     {
                        g.drawImage(loadImage("wpnsf/ArRight/ar1.png"),players.get(PLAYER1).getPosition().getX()+30,map.getYLength()-players.get(PLAYER1).getPosition().getY()+30,null);
                        if(keys.contains(KeyEvent.VK_SPACE) && ((Gun)(players.get(PLAYER1).getWeapon())).getMagazine() != 0)
                           g.drawImage(loadImage("wpnsf/ArRight/ar2.png"),players.get(PLAYER1).getPosition().getX()+30,map.getYLength()-players.get(PLAYER1).getPosition().getY()+30,null);
                     }
                     if(players.get(PLAYER1).getWeapon() instanceof Pistol)
                     {
                        g.drawImage(loadImage("wpnsf/pistolRight/pistol1.png"),players.get(PLAYER1).getPosition().getX()+25,map.getYLength()-players.get(PLAYER1).getPosition().getY()+25,null);
                        if(keys.contains(KeyEvent.VK_SPACE) && ((Gun)(players.get(PLAYER1).getWeapon())).getMagazine() != 0)
                           g.drawImage(loadImage("wpnsf/pistolRight/pistol2.png"),players.get(PLAYER1).getPosition().getX()+25,map.getYLength()-players.get(PLAYER1).getPosition().getY()+25,null);
                     }
                     if(players.get(PLAYER1).getWeapon() instanceof Shotgun)
                     {
                        g.drawImage(loadImage("wpnsf/shotgunRight/shotgun1.png"),players.get(PLAYER1).getPosition().getX()+30,map.getYLength()-players.get(PLAYER1).getPosition().getY()+35,null);
                        if(keys.contains(KeyEvent.VK_SPACE) && ((Gun)(players.get(PLAYER1).getWeapon())).getMagazine() != 0)
                           g.drawImage(loadImage("wpnsf/shotgunRight/shotgun2.png"),players.get(PLAYER1).getPosition().getX()+30,map.getYLength()-players.get(PLAYER1).getPosition().getY()+35,null);
                     }
                  //   if(players.get(PLAYER1).getBullet()!=null)
                        //g.drawImage(loadImage("wpnsf/bullet.png"),players.get(PLAYER1).getBullet().getPoint().getX()+30,map.getYLength() - players.get(PLAYER1).getBullet().getPoint().getY(),null);
                  
                  
                  }
               }
               if(players.get(PLAYER1).getDirection()==WEST)
               {
                  if(players.get(PLAYER1).getWeapon() instanceof MachineGun)
                  {
                     g.drawImage(loadImage("wpnsf/ArLeft/ar1.png"),players.get(PLAYER1).getPosition().getX()+10,map.getYLength()-players.get(PLAYER1).getPosition().getY()+30,null);
                     if(keys.contains(KeyEvent.VK_SPACE) && ((Gun)(players.get(PLAYER1).getWeapon())).getMagazine() != 0)
                        g.drawImage(loadImage("wpnsf/ArLeft/ar2.png"),players.get(PLAYER1).getPosition().getX()+10,map.getYLength()-players.get(PLAYER1).getPosition().getY()+30,null);
                  }
                  if(players.get(PLAYER1).getWeapon() instanceof Laser)
                     g.drawImage(loadImage("wpnsf/LaserLeft/Laser1.png"),players.get(PLAYER1).getPosition().getX()+10,map.getYLength()-players.get(PLAYER1).getPosition().getY()+30,null);
                  if(players.get(PLAYER1).getWeapon() instanceof Pistol)
                  {
                     g.drawImage(loadImage("wpnsf/pistolLeft/pistol1.png"),players.get(PLAYER1).getPosition().getX()+10,map.getYLength()-players.get(PLAYER1).getPosition().getY()+20,null);
                     if(keys.contains(KeyEvent.VK_SPACE) && ((Gun)(players.get(PLAYER1).getWeapon())).getMagazine() != 0)
                        g.drawImage(loadImage("wpnsf/pistolLeft/pistol2.png"),players.get(PLAYER1).getPosition().getX()+10,map.getYLength()-players.get(PLAYER1).getPosition().getY()+20,null);
                  }
                  if(players.get(PLAYER1).getWeapon() instanceof Shotgun)
                  {
                     g.drawImage(loadImage("wpnsf/shotgunLeft/shotgun1.png"),players.get(PLAYER1).getPosition().getX(),map.getYLength()-players.get(PLAYER1).getPosition().getY()+31,null);
                     if(keys.contains(KeyEvent.VK_SPACE) && ((Gun)(players.get(PLAYER1).getWeapon())).getMagazine() != 0)
                        g.drawImage(loadImage("wpnsf/shotgunLeft/shotgun2.png"),players.get(PLAYER1).getPosition().getX(),map.getYLength()-players.get(PLAYER1).getPosition().getY()+31,null);
                  }
               }
            }
         }
      }
      if(players.get(PLAYER2).getIsDead())
      {
         g.drawImage(playerImages[5],players.get(PLAYER2).getPosition().getX(),map.getYLength()+20- players.get(PLAYER2).getPosition().getY(),null);
      }
      else
      {
         g.drawImage(playerImages[tempindex2],players.get(PLAYER2).getPosition().getX(),map.getYLength()-1- players.get(PLAYER2).getPosition().getY(),null);
      
         if(!(players.get(PLAYER2).getWeapon() instanceof fists))
         {
            if(players.get(PLAYER2).getWeapon() instanceof MeleeWeapon)
            {
               if(players.get(PLAYER2).getDirection()==EAST)
               {
                  if(players.get(PLAYER2).getWeapon() instanceof Sword)
                     g.drawImage(loadImage("wpnsf/sword/swordR.png"),players.get(PLAYER2).getPosition().getX()+13,map.getYLength()-players.get(PLAYER2).getPosition().getY()+13,null);
               }
               if(players.get(PLAYER2).getDirection()==WEST)
               {
                  g.drawImage(loadImage("wpnsf/sword/swordL.png"),players.get(PLAYER2).getPosition().getX()-13,map.getYLength()-players.get(PLAYER2).getPosition().getY()+13,null);
               }
            }
            else
            {
               if(players.get(PLAYER2).getDirection()== EAST)
               {
                  if(players.get(PLAYER2).getWeapon() instanceof MachineGun)
                  {
                     g.drawImage(loadImage("wpnsf/ArRight/ar1.png"),players.get(PLAYER2).getPosition().getX()+30,map.getYLength()-players.get(PLAYER2).getPosition().getY()+30,null);
                     if(keys.contains(KeyEvent.VK_P) && ((Gun)(players.get(PLAYER2).getWeapon())).getMagazine() != 0)
                        g.drawImage(loadImage("wpnsf/ArRight/ar2.png"),players.get(PLAYER2).getPosition().getX()+30,map.getYLength()-players.get(PLAYER2).getPosition().getY()+30,null);
                  }
                  if(players.get(PLAYER2).getWeapon() instanceof Laser)
                     g.drawImage(loadImage("wpnsf/Laser/lazer1.png"),players.get(PLAYER2).getPosition().getX()+30,map.getYLength()-players.get(PLAYER2).getPosition().getY()+30,null);
                  if(players.get(PLAYER2).getWeapon() instanceof Pistol)
                  {
                     g.drawImage(loadImage("wpnsf/pistolRight/pistol1.png"),players.get(PLAYER2).getPosition().getX()+25,map.getYLength()-players.get(PLAYER2).getPosition().getY()+25,null);
                     if(keys.contains(KeyEvent.VK_P) && ((Gun)(players.get(PLAYER2).getWeapon())).getMagazine() != 0)
                        g.drawImage(loadImage("wpnsRight/pistol2.png"),players.get(PLAYER2).getPosition().getX()+30,map.getYLength()-players.get(PLAYER2).getPosition().getY()+25,null);
                  }
                  if(players.get(PLAYER2).getWeapon() instanceof Shotgun)
                  {
                     g.drawImage(loadImage("wpnsf/shotgunRight/shotgun1.png"),players.get(PLAYER2).getPosition().getX()+30,map.getYLength()-players.get(PLAYER2).getPosition().getY()+35,null);
                     if(keys.contains(KeyEvent.VK_P) && ((Gun)(players.get(PLAYER2).getWeapon())).getMagazine() != 0)
                        g.drawImage(loadImage("wpnsf/shotgunRight/shotgun2.png"),players.get(PLAYER2).getPosition().getX()+30,map.getYLength()-players.get(PLAYER2).getPosition().getY()+35,null);
                  }
               }
               if(players.get(PLAYER2).getDirection()==WEST)
               {
                  if(players.get(PLAYER2).getWeapon() instanceof MachineGun){
                     g.drawImage(loadImage("wpnsf/ArLeft/ar1.png"),players.get(PLAYER2).getPosition().getX()+10,map.getYLength()-players.get(PLAYER2).getPosition().getY()+30,null);
                     if(keys.contains(KeyEvent.VK_P) && ((Gun)(players.get(PLAYER2).getWeapon())).getMagazine() != 0)
                        g.drawImage(loadImage("wpnsf/ArLeft/ar2.png"),players.get(PLAYER2).getPosition().getX()+10,map.getYLength()-players.get(PLAYER2).getPosition().getY()+30,null);
                  }
                  if(players.get(PLAYER2).getWeapon() instanceof Laser)
                     g.drawImage(loadImage("wpnsf/LaserLeft/Laser1.png"),players.get(PLAYER2).getPosition().getX()+10,map.getYLength()-players.get(PLAYER2).getPosition().getY()+30,null);
                  if(players.get(PLAYER2).getWeapon() instanceof Pistol)
                  {
                     g.drawImage(loadImage("wpnsf/pistolLeft/pistol1.png"),players.get(PLAYER2).getPosition().getX()+10,map.getYLength()-players.get(PLAYER2).getPosition().getY()+20,null);
                     if(keys.contains(KeyEvent.VK_P) && ((Gun)(players.get(PLAYER2).getWeapon())).getMagazine() != 0)             
                        g.drawImage(loadImage("wpnsf/pistolLeft/pistol2.png"),players.get(PLAYER2).getPosition().getX()+10,map.getYLength()-players.get(PLAYER2).getPosition().getY()+20,null);
                  }
                  if(players.get(PLAYER2).getWeapon() instanceof Shotgun){
                     g.drawImage(loadImage("wpnsf/shotgunLeft/shotgun1.png"),players.get(PLAYER2).getPosition().getX(),map.getYLength()-players.get(PLAYER2).getPosition().getY()+31,null);
                     if(keys.contains(KeyEvent.VK_P) && ((Gun)(players.get(PLAYER2).getWeapon())).getMagazine() != 0)
                        g.drawImage(loadImage("wpnsf/shotgunLeft/shotgun2.png"),players.get(PLAYER2).getPosition().getX(),map.getYLength()-players.get(PLAYER2).getPosition().getY()+31,null);
                  }
               } 
            }
         }
      }
      g.setColor(Color.WHITE);
      for(Player p:players)
      {
         if(p.getHealth()<=25)
            g.setColor(Color.RED);
         else if(p.getHealth()<=75)
            g.setColor(Color.YELLOW);
         else if(p.getHealth()>100)
            g.setColor(Color.GREEN);
         
         g.drawRect(p.getPosition().getX(),getHeight()-p.getPosition().getY()-150, 100,10);
         g.fillRect(p.getPosition().getX(),getHeight()-p.getPosition().getY()-150,Math.min(100, (int)(p.getHealth() % 100.000000001)),10);
         g.setColor(Color.WHITE);
      }
     // g.drawString("EMPTY",300,300);
           
      //repaint();
   }
  
   public void SpawnWeapon()
   {
      double chances = Math.random();
      Weapon temp;
      if(droppedWeapons.size()>6)
      {
         return;
      }
      if(chances>.98)
      {
         temp = new Laser(new Point(974+7,225-3));
         temp.setFrameRemoved(frame);
         droppedWeapons.add(temp);
         return;
      //draw and spawn a laser at a certain point on the map
      }
      else if(chances>.87)
      {
         temp =new MachineGun(new Point(800+10,225));
         temp.setFrameRemoved(frame);
         droppedWeapons.add(temp);
         return;
      //draw and spawn a machine gun
      }
      else if(chances>.55)
      {
         temp = new Pistol(new Point(570+10,225));
         temp.setFrameRemoved(frame);
         droppedWeapons.add(temp);
         return;
         
       
      //draw and spawn a shotgun
      }
      else if(chances>.15)
      {
         temp = new Sword(new Point(1210,230+10));
         temp.setFrameRemoved(frame);
         droppedWeapons.add(temp);//draw and spawn a pistol
         return;
      }
      else
      {
         HealthPack temp1 = new HealthPack(new Point(740,100));
         temp1.setFrameRemoved(frame);
         healthpacks.add(temp1);
         
         HealthPack temp2 = new HealthPack(new Point(1015,100));
         temp2.setFrameRemoved(frame);
         healthpacks.add(temp2);
         
         return;
      }
      
   }
   
   public void keyPressed(KeyEvent e)
   {
      if(e.getKeyCode() == KeyEvent.VK_SPACE)
      {
         canShoot1 = players.get(PLAYER1).Attack(map,players.get(PLAYER2));
         isShooting1 = true;  
      }
      if(e.getKeyCode() == KeyEvent.VK_P)
      {
         canShoot2 = (players.get(PLAYER2).Attack(map,players.get(PLAYER1)));
         isShooting2 = true;     
      }
      keys.add(e.getKeyCode());
   }
   
   
   public void movePlayers()
   {
      
      for(Integer currentKey:keys)
      {
      
         if(!players.get(PLAYER1).getIsDead())
         {
            if(currentKey == KeyEvent.VK_D)
            {
            
               if(index>1){}
               else
                  index++;
               players.get(PLAYER1).move(map, EAST);
            }  
            
            else if(currentKey == KeyEvent.VK_A)
            {
            
               if(index==0){}  
               else
                  index--;
               players.get(PLAYER1).move(map, WEST);
            }
            else if(currentKey == KeyEvent.VK_S)
            {
               players.get(PLAYER1).moveDown();
            }
            
            else if(currentKey == KeyEvent.VK_W)
            {
            
               players.get(PLAYER1).jump(map);  
            }
            // else if(currentKey == KeyEvent.VK_SPACE)
            //             {
            //             
            //                canShoot1 = players.get(PLAYER1).Attack(map,players.get(PLAYER2));
            //                isShooting1 = true;  
            //             }
            else if(currentKey == KeyEvent.VK_F)
            {
               if(!(players.get(PLAYER1).getWeapon() instanceof fists))
               {
                  players.get(PLAYER1).getWeapon().setFrameRemoved(frame);
                  droppedWeapons.add(players.get(PLAYER1).dropWeapon());
               }
            }
            
            else if(currentKey == KeyEvent.VK_E)
            {
               //int closestDistance
               //Weapon closestWeapon
               //go through droppedWeapons
               //
               int closest = Integer.MAX_VALUE;
               int indexOfClosest = -1;
               for(int i =0;i<droppedWeapons.size();i++)
               {
                  if(Math.abs((players.get(PLAYER1).getPosition().getX()-droppedWeapons.get(i).getPosition().getX()))<closest)
                  {
                     closest = Math.abs(((players.get(PLAYER1).getPosition().getX())-(droppedWeapons.get(i).getPosition().getX())));
                     indexOfClosest = i;
                  }
               }
               if(closest < 20)
                  if(players.get(PLAYER1).swapWeapon(droppedWeapons.get(indexOfClosest)))
                     droppedWeapons.remove(indexOfClosest);                 
               
               // if(players.get(PLAYER1).getWeapon() instanceof fists)
               // {
               //    
               //    
                  // if(players.get(PLAYER1).getPosition().getX() >= (((Gun)droppedWeapon1).getPosition()).getX()-50 && players.get(PLAYER1).getPosition().getX() <= (((Gun)droppedWeapon1).getPosition()).getX()+20)
                  // 
                     // players.get(PLAYER1).swapWeapon(droppedWeapon1);
                  // 
                  // else if(players.get(PLAYER1).getPosition().getX() >= (((Gun)droppedWeapon2).getPosition()).getX()-50 && players.get(PLAYER1).getPosition().getX() <= (((Gun)droppedWeapon2).getPosition()).getX()+20)
                     // players.get(PLAYER1).swapWeapon(droppedWeapon2);
               //   // else
               //    //   players.get
               //   // dropping1 = false;
            }
         }
      
      
            
            //-----------------------------------------------------PLAYER 2---------------------------------
      
         if(!(players.get(PLAYER2).getIsDead())) 
         {
            if(currentKey == KeyEvent.VK_RIGHT)
            {
            
               if(index2 >= 2){}
               else
                  index2++;
               players.get(PLAYER2).move(map, EAST);
            }  
            
            // else if(currentKey == KeyEvent.VK_UP&& key+1== KeyEvent.VK_RIGHT)
            // {
               // System.out.println("WORKS");
            // }
            else if(currentKey == KeyEvent.VK_LEFT)
            {
            
               if(index2 == 0){}
               else
                  index2--;
               players.get(PLAYER2).move(map, WEST);
              
            }  
            else if(currentKey == KeyEvent.VK_DOWN)
            {
               players.get(PLAYER2).moveDown();
            }
            else if(currentKey == KeyEvent.VK_UP)
            {  
            
            //index2 = 4;
               players.get(PLAYER2).jump(map);  
             
            }  
            // else if(currentKey == KeyEvent.VK_P)
            //             {
            //                canShoot2 = (players.get(PLAYER2).Attack(map,players.get(PLAYER1)));
            //                isShooting2 = true;  
            //               
            //             }
            else if(currentKey == KeyEvent.VK_O)
            {
               if(!(players.get(PLAYER2).getWeapon() instanceof fists))
               {
                  players.get(PLAYER2).getWeapon().setFrameRemoved(frame);
                  droppedWeapons.add(players.get(PLAYER2).dropWeapon());     
               }
               
            }
            else if(currentKey == KeyEvent.VK_I)
            {
               int closest = Integer.MAX_VALUE;
               int indexOfClosest = -1;
               for(int i =0;i<droppedWeapons.size();i++)
               {
                  if(Math.abs(((players.get(PLAYER2).getPosition().getX())-(droppedWeapons.get(i).getPosition().getX())))<closest )
                  {
                     closest = Math.abs((players.get(PLAYER2).getPosition().getX()-droppedWeapons.get(i).getPosition().getX()));
                     indexOfClosest = i;
                  }
               }
               
               if(closest < 20)
                  if(players.get(PLAYER2).swapWeapon(droppedWeapons.get(indexOfClosest)))
                     droppedWeapons.remove(indexOfClosest);
                  
            }
         }
       
      }
   }
   
   public void keyReleased(KeyEvent e){
      if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_P)
      {
         isShooting1 = false;
         isShooting2 = false;
         
      }
      keys.remove(e.getKeyCode());
      /*
      if(e.getKeyCode() == KeyEvent.VK_P)
         isShooting2 = false;
      if(e.getKeyCode() == KeyEvent.VK_SPACE)
         isShooting1 = false;
      */
   }
   
   public void keyTyped(KeyEvent e){
      
   }
   
   public Map getMap()
   {
      return map;
   }
   public void setMap(Map map)
   {
      this.map = map;
   }
   
   public static BufferedImage loadImage(String imgName){
      try{
         return ImageIO.read(new File(imgName));
      }catch(IOException e){
         e.printStackTrace();
      }
      return null;
   }
}