public class Point
{
   protected final int EAST = 1;//directions
   protected final int WEST = -1;//directions 
   protected int x;//a position in the x direction
   protected int y;//a position in the y direction
   protected int direction;//which direction they are facing
   private boolean longP = false;
   private boolean shortP = false;
   private boolean spawnP = false;
   public Point(int x1,int y1,int d)//send it the (x,y) coordinate and the direction which they are facing 
   {
      x = x1;
      y = y1;
      direction = d;
   }
   public Point()//default constructor making the coordinate start at (1,1) facing East
   {
      x = 1;
      y = 1;
      direction = EAST;
   }
   public Point(int x1,int y1)
   {
      x = x1;
      y = y1;
      direction =EAST;
   }
   public Point(int x,int y,boolean l,boolean s,boolean sp)
   {
      this.x= x;
      this.y = y;
      longP = l;
      shortP = s;
      spawnP = sp;
   }
   public boolean isShort()
   {
      return shortP;
   }
   public boolean isLong()
   {
      return longP;
   }
   public boolean isSpawn()
   {
      return spawnP;
   }
   public void toggleLongP()
   {
      longP = !longP;
   }
   public void toggleShortP()
   {
      shortP = !shortP;
   }

   public int getX()//returns the x value
   {
      return x;
   }
   public int getY()//returns the y value
   {
      return y;
   }
   public boolean setX(int x1)
   {
      x = x1;
      return true;
   }
   public boolean setY(int y1)
   {
      y = y1;
      return true;
   }
   public int getDirection()//returns the direction
   {
      return direction;
   }
   public void goForwards(int direction1)//moves in the direction that they are facing 
   {
      if(direction1 == direction){
        
         if(direction == EAST)
            x+=7;
         else
            x-=7;
         
      }else
         direction *= -1;//now needs to check if the block its curretly on is standable if not move it down (falling)
   }
   public boolean isEqual(Point p)
   {
      return (this.x == p.getX()&&this.y == p.getY());
   }
   public void bgoForwards(int direction1/*,Bullet b,ArrayList<Player>players*/)//moves in the direction that they are facing 
   {
      if(direction1 == direction)
      {
         if(direction == EAST)
            x++;
         else
            x--;
      
      }
      else
         direction *= -1;//now needs to check if the block its curretly on is standable if not move it down (falling)
   }

   public void fall()
   {
      y-=25; 
   }
   
   public void changeDirection()//toggles the direction 
   {
      direction *= -1;
   }
   public String toString()
   {
      return "("+x+","+y+")";
   }
   //private boolean checkifStandable()
   //{
   //return true if the cell at t
   //}
}