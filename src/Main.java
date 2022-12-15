import javax.swing.*;
/**
 * klasa główna zawierająca metodę statyczną main
 */

public class Main extends JFrame{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try{
                Window okno= new Window();
                okno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                okno.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        });
    }
}
