import java.util.ArrayList;

public class Map
{
   private int x = 1600;//the size of the map Size x Size 1600x900 for baram
   private int y= 900;
   private ArrayList<Point>platforms;
   private ArrayList<HealthPack>healthpacks;
   private Cell[][]map;// a 2d array of cells
   public Map()
   {
      platforms = new ArrayList<Point>();
      map= new Cell[x][y];
      for(int r =0;r<map.length;r++)
         for(int c = 0;c<map[0].length;c++)
            map[r][c] = new Cell();
      // for(int i =1;i<1;i++)
      // {
         // for(int x = 0; x<10;x++)
         // {
            // map[i][x].changetoTrue();
         // }
      // }
   }  

   // public Map(int x1, int y1)
   // {
     // 
      // map= new Cell[x][y];
      // for(int r =0;r<map.length;r++)
         // for(int c = 0;c<map[0].length;c++)
            // map[r][c] = new Cell();
   //      
   // }
   public int getSize()
   {
      return x;
   }  
   public void setHealths(ArrayList<HealthPack>list)
   {
      healthpacks = list;
   }
   public ArrayList<HealthPack> getHealthPacks()
   {
      return healthpacks;
   }
   public int getXLength()
   {
      return x;
   }
   public int getYLength()
   {
      return y;
   }
   public Cell getCellAt(int r,int c)
   {   
      return map[c][r];
   }
   public String toString()
   {
      String temp="";
      for(int i =0;i<map.length;i++)
      {
         for(int r = 0;r<map[i].length;r++)
            temp+=map[i][r].toString();
         temp+="\n";
      }
      return temp;
   }
   public ArrayList<Point> getPlatformList()
   {
      return platforms;
   }
   public void addPlatform(Point p)
   {
      for(int i =-25;i<25;i++)
      {
         map[p.getX()+i][p.getY()].changetoTrue();
         platforms.add(new Point(p.getX(),p.getY(),false,true,false));
      }
   }
   public void addSpawnPlatform(Point p)
   {
      for(int i =-25;i<25;i++)
      {
         map[p.getX()+i][p.getY()].changetoTrue();
         platforms.add(new Point(p.getX(),p.getY(),false,false,true));
      }
   }
   
   public void addBigPlatform(Point p)
   {
      for(int i =-25;i<110;i++)
      {
         map[p.getX()+i][p.getY()].changetoTrue();
         platforms.add(new Point(p.getX(),p.getY(),true,false,false));
      }
   }
   
   public void mapSetup()
   {
      addBigPlatform(new Point(130,225));
      addBigPlatform(new Point(350,350));
      addPlatform(new Point(395,100));
      addSpawnPlatform(new Point(570,225));
      addBigPlatform(new Point(700,350));
      addSpawnPlatform(new Point(745,100));
      addBigPlatform(new Point(getXLength()-215,225));
      addSpawnPlatform(new Point(800,225));
      addSpawnPlatform(new Point(974,225));
      addSpawnPlatform(new Point(1200,225));
      addPlatform(new Point(getXLength()-300,100));
      addBigPlatform(new Point(1100,350));
      addSpawnPlatform(new Point(1019,100));
      
      
   }
   
}