public class Sword extends MeleeWeapon
{

   public Sword()//dagger shouldnt need constructors
   {
      name = "Sword";//name is dagger
      Damage = 5;
      Range = 2;
   }
   public Sword(Point p )//dagger shouldnt need constructors
   {
      name = "Sword";//name is dagger
      Damage = 5;
      Range = 2;
      point = p;
   }
}