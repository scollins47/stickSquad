public class Spear extends MeleeWeapon
{
   protected short throwDamage;
   protected byte throwRange;
   private Point point;
   public Spear()
   {
      name = "Spear";//name is Spear
      Damage = 15;//damage and range both 1
      throwDamage = 10;
      Range = 3;
      throwRange = 10;
   }
   public Spear(Point p)
   {
      name = "Spear";//name is Spear
      Damage = 15;//damage and range both 1
      throwDamage = 10;
      Range = 3;
      throwRange = 10;
      point =p;
   }
   
   public short getThrowDamage()
   {
      return throwDamage;
   }
   
   public byte throwRange()
   {
      return throwRange;
   }
   public Point getPosition()
   {
      return point;
   }
}