import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;


public class Main {
    public static void main(String[] args) {
        int[][] points = {
            {119, 39},
            {269, 39},
            {415, 39},
            {567, 39},
            {720, 39}
        };
        int[] keyCodes = {
            KeyEvent.VK_A,
            KeyEvent.VK_S,
            KeyEvent.VK_J,
            KeyEvent.VK_K,
            KeyEvent.VK_L
        };

        try{
            
            Thread.sleep(5000);
    
            while (true) {
                BufferedImage screen = new Robot().createScreenCapture(new Rectangle(1837 / 2, 1306 / 2, 800 / 2, 100 / 2));
    
                for (int i = 0; i < points.length; i++){
                    int x = points[i][0];
                    int y = points[i][1];
                    int color = screen.getRGB(x, y);
    
                                    // Debug information
                    System.out.println("Point [" + x + ", " + y + "]: Detected color " + color);
    
                    if (color == -10383487 || color == -14559647 || color == -14258093 || color == -11408601) {
                        Robot robot = new Robot();
                        robot.keyPress(keyCodes[i]);
                        robot.keyRelease(keyCodes[i]);
                    }
    
                }
    
                Thread.sleep(100);
        }
        } catch (InterruptedException | AWTException e) {
            e.printStackTrace();
        }
    }
}
