import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JPanel implements KeyListener{

   private String p1name, p2name;
   private String buff;
   private BufferedImage background = KelvinPanel.loadImage("wpnsf/images2.jpg");
   
   public Menu(){
      buff = "";
      
      this.addKeyListener(this);
      this.setFocusable(true);
      this.requestFocus();
   }
   public void paintComponent(Graphics g){
      g.drawImage(background, 0, 0, null);
      
      g.setFont(new Font("Courier New", Font.BOLD, 128));
      g.setColor(Color.WHITE);
      
      g.drawString("Welcome", 200, 200);
      
      g.setFont(new Font("Courier New", Font.PLAIN, 48));
      if(p1name==null){
         g.drawString("Enter Player 1's name: " + buff, 200, 500);
      }else if(p2name==null){
         g.drawString("Player 1: " + p1name, 200, 500);
         g.drawString("Enter Player 2's name: " + buff, 200, 700);
      }
      g.setFont(new Font("Courier New", Font.PLAIN, 16));
      g.drawString("PLAYER1 Controls: W-Jump, S-Fall,E-PickUp,F-Drop, SPACEBAR- Attack",200,600);
      g.drawString("PLAYER2 Controls: ARROWKEY UP-jump, DOWN-Fall,Left and Right to move left and right, I- Pick Up,O,Drop,P-Attack",200,800);
      repaint();
   }
   public void keyPressed(KeyEvent e)
   {
      if(e.getKeyCode() != KeyEvent.VK_ENTER){
         if(Character.isLetterOrDigit(e.getKeyCode()))
            buff+=e.getKeyChar() + "";
         if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            buff = buff.substring(0,buff.length()-1);
      }
      else{
         if(p1name == null){
            p1name = buff;
            buff = "";
         }else if(p2name == null){
            p2name = buff;
            
            KelvinsGraphicTutorial.panel = new KelvinPanel(p1name, p2name);
            
            KelvinsGraphicTutorial.frame.setContentPane(KelvinsGraphicTutorial.panel);
            KelvinsGraphicTutorial.frame.revalidate();
            KelvinsGraphicTutorial.panel.requestFocus();
         }
      }
   }
   public void keyTyped(KeyEvent e)
   {
   
   }
   public void keyReleased(KeyEvent e)
   {
   
   }
}