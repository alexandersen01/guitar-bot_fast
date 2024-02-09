import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class robot {
    
    public static void main(String[] args) throws AWTException, InterruptedException{
        Point[] points = {
            new Point(119, 39),
            new Point(269, 39),
            new Point(415, 39),
            new Point(567, 39),
            new Point(720, 39)
        };

        String[] keys = {"a", "s", "j", "k", "l"};

        Thread.sleep(5000);

        while (true) {
            robot robot = new robot();
            Rectangle screenRect = new Rectangle(1306/2, 1837 / 2, 800 / 2, 100 / 2);
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);
            
            StringBuilder detectedKeys = new StringBuilder();

            for (int i = 0; i < points.length; i++) {
                Point point = points[i];
                int color = screenCapture.getRGB(point.x, point.y);
            }

            // Debug info
            // optional...
        }
    

    if (detectedKeys.length() > 0) {
        String keysToPress = detectedKeys.toString().split(" ");
        for (String key : keysToPress.split(keysToPress)) {
            pressKey(key);
            }
        }
    }

    public static void pressKey(String key) {
        try {
            robot robot = new robot();
            switch (key) {
                case "a":
                    robot.keyPress(KeyEvent.VK_A);
                    robot.keyRelease(KeyEvent.VK_A);
                    break;
                case "s":
                    robot.keyPress(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_S);
                    break;
                case "j":
                    robot.keyPress(KeyEvent.VK_J);
                    robot.keyRelease(KeyEvent.VK_J);
                    break;
                case "k":
                    robot.keyPress(KeyEvent.VK_K);
                    robot.keyRelease(KeyEvent.VK_K);
                    break;
                case "l":
                    robot.keyPress(KeyEvent.VK_L);
                    robot.keyRelease(KeyEvent.VK_L);
                    break;
            
                default:
                    break;
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}

