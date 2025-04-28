import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        // Capture region coordinates
        int s_x = 455; // Left edge of capture area
        int s_y = 645; // Top edge of capture area
        int width = 560; // Width of capture
        int height = 138; // Height of capture
        
        // Points to check - center of each colored circle
        // These are absolute positions within the captured image
        int[][] points = {
            {112, 86}, // Green circle
            {189, 86}, // Red circle
            {277, 86}, // Yellow circle
            {367, 86}, // Blue circle
            {450, 86}  // Orange circle
        };
        
        // Key mappings for each circle position
        int[] keyCodes = {
            KeyEvent.VK_A, // Green
            KeyEvent.VK_S, // Red
            KeyEvent.VK_J, // Yellow
            KeyEvent.VK_K, // Blue
            KeyEvent.VK_L  // Orange
        };
        
        // Colors to detect for each circle
        // These should be calibrated for your specific game
        int[] targetColors = {
            -16711936, // Green (approximate RGB value)
            -65536,    // Red (approximate RGB value)
            -256,      // Yellow (approximate RGB value)
            -16776961, // Blue (approximate RGB value)
            -14120960  // Orange (approximate RGB value)
        };

        try {
            System.out.println("Starting in 5 seconds...");
            Thread.sleep(5000);
            Robot robot = new Robot();
            
            // Take an initial screenshot to verify the capture area
            BufferedImage initialScreenshot = robot.createScreenCapture(new Rectangle(s_x, s_y, width, height));
            
            // Save the screenshot to a file
            try {
                File outputfile = new File("capture_region.png");
                ImageIO.write(initialScreenshot, "png", outputfile);
                System.out.println("Initial screenshot saved as 'capture_region.png'");
                
                // Display color values at each point for calibration
                System.out.println("Color values at check points:");
                for (int i = 0; i < points.length; i++) {
                    int x = points[i][0];
                    int y = points[i][1];
                    
                    if (x >= 0 && x < width && y >= 0 && y < height) {
                        int color = initialScreenshot.getRGB(x, y);
                        Color rgbColor = new Color(color);
                        System.out.println("Point " + i + " [" + x + ", " + y + "]: RGB(" + 
                                           rgbColor.getRed() + "," + rgbColor.getGreen() + "," + 
                                           rgbColor.getBlue() + "), Color value: " + color);
                    } else {
                        System.out.println("Point " + i + " [" + x + ", " + y + "]: Out of bounds");
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to save screenshot: " + e.getMessage());
            }
            
            System.out.println("Starting main detection loop...");
            while (true) {
                BufferedImage screen = robot.createScreenCapture(new Rectangle(s_x, s_y, width, height));
    
                for (int i = 0; i < points.length; i++) {
                    int x = points[i][0];
                    int y = points[i][1];
                    
                    // Ensure point is within captured image bounds
                    if (x >= 0 && x < width && y >= 0 && y < height) {
                        int color = screen.getRGB(x, y);
                        Color rgbColor = new Color(color);
        
                        // Debug information - less verbose during main loop
                        System.out.println("Point " + i + " [" + x + ", " + y + "]: RGB(" + 
                                           rgbColor.getRed() + "," + rgbColor.getGreen() + "," + 
                                           rgbColor.getBlue() + ")");
        
                        // Note: The color detection should be calibrated based on the actual colors in your game
                        // The first screenshot will help with this calibration
                        // For now, using a simple detection method that works with many rhythm games:
                        // Usually we want to detect when a note is present (not just the colored circle)
                        
                        // Check if the pixel is significantly bright (indicating a note)
                        int brightness = rgbColor.getRed() + rgbColor.getGreen() + rgbColor.getBlue();
                        if (brightness > 500) {  // Threshold to adjust based on your game
                            System.out.println("HIT at position " + i);
                            robot.keyPress(keyCodes[i]);
                            robot.keyRelease(keyCodes[i]);
                            // Small delay to prevent double-hitting the same note
                            Thread.sleep(50);
                        }
                    }
                }
    
                // Adjust this delay based on how frequently you need to check
                // Lower values mean more frequent checks but higher CPU usage
                Thread.sleep(20);
            }
        } catch (InterruptedException | AWTException e) {
            e.printStackTrace();
        }
    }
}