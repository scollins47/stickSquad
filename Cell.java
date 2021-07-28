public class Cell
{
   private Point location;
   private boolean isStandable;
   public Cell()
   {
      location = new Point();
      isStandable = false;
   }
   public Cell(boolean i)
   {
      location = new Point();
      isStandable = i;
   }
   public boolean toggleStandable()
   {
      isStandable = !isStandable;
      return isStandable;
   }
   public boolean changetoTrue()
   {
      isStandable = true;
      return isStandable;
   }
   public boolean changetoFalse()
   {
      isStandable = false;
      return isStandable;
   }
   public boolean getStandable()
   {
      return isStandable;
   }
   public void changeStandable(boolean b)
   {
      isStandable = b;
   }
   public Point getLocation()
   {
      return location;
   }
   public String toString()
   {
      return ""+location.toString();
   }
   
   public void blockBorder(Map map)
   {
      for(int i=0;i<map.getXLength();i++)
      {
         for(int j=0;j<map.getYLength();j++)
         {
            if(i == 0 || i == map.getXLength()-1)
               (map.getCellAt(i,j)).changetoTrue();
            if(j == 0 || j == map.getYLength()-1)
               map.getCellAt(i,j).changetoTrue();
         }
      }
   }
}