public class Test
{

   public static void main(String[]arg)
   {
      Player p1 = new Player("Sammy", new Point(0,0));
      Player p2 = new Player("robert", new Point(2,0),new Dagger());
      Map map = new Map();
      p1.Attack(map,p2);
     
   
   }
}