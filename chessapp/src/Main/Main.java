package Main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("My Chess");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);

            //add game panel to
            GamePanel gp = new GamePanel();
            window.add(gp);
            //packing will adjust window to gp
            window.pack();
            
            window.setLocationRelativeTo(null);
            window.setVisible(true);

            //when window up it runs game
            gp.launchGame();
        });
    }
}
