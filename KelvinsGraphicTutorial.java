import java.awt.*;
import javax.swing.*;

public class KelvinsGraphicTutorial {
   public static JFrame frame;
   public static Menu menu;
   public static KelvinPanel panel;

   public static void main(String[] args) {
      frame = new JFrame("StickSquad");
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      menu = new Menu();
      frame.setContentPane(menu);

      frame.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
      frame.setVisible(true);
   }
}