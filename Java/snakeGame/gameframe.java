// creating a gameframe child class using JFrame as the base class . It is used to create the screen in which we will play the game.
import javax.swing.JFrame;


public class GameFrame extends JFrame {
    GameFrame(){

        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
