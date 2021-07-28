public class MeleeWeapon extends Weapon
{
   protected byte Range;//every melee weapon has a range
   public MeleeWeapon()
   {
      isMelee = true;//ofcourse its melee
   }
   public short getRange()//every melee weapon has a get range method
   {
      return Range;
   }
}