public class Bullet
{
   protected byte damage;//how much damage the bullet will do
   protected String name;//the name/type of bullet
   private Point point;//where the bullet is on the map
 

   public Bullet(String n)//sending it which bullet should be created
   {
      name = n;
      if(name.equals("Laser"))//lasers will do 100 damage
         damage = 100;
      if(name.equals("Spear"))
         damage = 10;
      if(name.equals("Shells"))//shells will do 5 but create multiple if ur shooting a shotgun
         damage = 5;
      if(name.equals("MachineGunClip"))//machine gun bullets will do 10 damage
         damage = 10;
      if(name.equals("PistolClip"))//pistol clips will do 2 damage
         damage = 4;
      point = new Point(-1,-1);//defaults to out of the map
   }
   public Bullet(Bullet b, Point p)
   {
   if(b == null)
         this.damage = 0;
      else 
         this.damage = b.getDamage();
      this.name = b.getName();
      this.point = new Point(p.getX(),p.getY());
   }
   public Bullet(Bullet b)
   {
      if(b.getDamage() == 0)
      {
         this.damage = 0;
      }
      else
      
         this.damage = b.getDamage();
      this.name = b.getName();
      point = new Point(-1,-1);
   }
   public Bullet(String n, Point p)//sending it which bullet should be created and where it is on the map
   {
      name = n;
      if(n.equals("Laser"))//lasers will do 100 damage
         damage = 100;
      if(n.equals("Spear"))
         damage = 10;
      if(n.equals("Shells"))//shells will do 5 but create multiple if ur shooting a shotgun
         damage = 5;
      if(n.equals("MachineGunClip"))//machine gun bullets will do 10 damage
         damage = 10;
      if(n.equals("PistolClip"))//pistol clips will do 2 damage
         damage = 2;
      point = p;
   }
   public Point getPoint()
   {
      return point;
   }
   public String getName()//return the name of the bullet
   {
      return name;
   }
   public byte getDamage()//return the damage that the bullet will do 
   {
      return damage;
   }
}