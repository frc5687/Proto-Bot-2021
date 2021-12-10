/*********************************************************************************************
 * Control the led light strips on the robot
 * We don't extend the AddressableLED class as yes it does work, but it dosen't fix our format
**********************************************************************************************/
package org.frc5687.swerve.util;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.RobotMap;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
public class Blinken {

    private AddressableLEDBuffer buffer;
    private AddressableLED led;

    public Blinken() {
        //Setup the LED length(buffer) and map the port
        buffer = new AddressableLEDBuffer(Constants.Blinkens.buffer);
        led = new AddressableLED(RobotMap.PWM.BLINKEN);
        //Configure the settings for the blinkens
        led.setLength(this.buffer.getLength());
        led.setData(this.buffer);
        led.start();
   }

   public void setColor(){
       for(var i = 0; i < buffer.getLength(); i++){
           buffer.setRGB(i, 255, 255, 0);
        }
        led.setData(buffer);
   }

   public void setNamedColor(Colors color){
    for(var i = 0; i < buffer.getLength(); i++){
        buffer.setRGB(i, color.r, color.g, color.b);
     }
     led.setData(buffer);
   }

   public void breathingLED(int initR,int initG,int initB) {
        int rChangeBy = initR/8;
        int gChangeBy = initG/8;
        int bChangeBy = initB/8;
        int r = initR;
        int g = initG;
        int b = initB;
        for(var i = 0; i < 8; i++) {
            for(var j = 0; j < buffer.getLength(); j++){
                buffer.setRGB(i*8 + j,r,g,b);
            }
            r = r - rChangeBy;
            g = g - gChangeBy;
            b = b - bChangeBy;
        }
   }

   public void setDefault(){
       for(var i = 0; i < buffer.getLength(); i++){
           //Hot pink let's go
           buffer.setRGB(i, 255,105,180);
        }
        led.setData(buffer);
   }

   public void test(){
       for (var i = 0; i < buffer.getLength() / 2; i++) {
           // Sets the specified LED to the RGB values for red
           buffer.setRGB(i, 255, 0, 0);
        }
   }

   public void blinkensOFF(){
       //Shut off the blinkens
       led.stop();
   }

   public static enum Colors{
       //In the long run we will be using this class to store more dynamic colours

       RED(255,0,0),
       ORANGE(255,127,0),
       YELLOW(255,255,0),
       LIGHTGREEN(0,255,0),
       GREEN(0,100,0),
       CYAN(0,255,255)
       BLUE(0,0,255),
       LIGHTPURPLE(148,0,211),
       DARKPURPLE(75,0,130),
       PINK(255,0,255),
       BROWN(102,51,0),
       GRAY(65,65,65),
       OUTLIERSGARNET(241,230,8),
       OUTLIERSYELLOW(248,192,14);
       

        public final int r,g,b;

        Colors(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
       }

   }
}
