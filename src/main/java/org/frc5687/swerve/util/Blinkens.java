/*********************************************************************************************
 * Control the led light strips on the robot
 * We don't extend the AddressableLED class as yes it does work, but it dosen't fix our format
**********************************************************************************************/
package org.frc5687.swerve.util;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.RobotMap;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class Blinkens{

    private AddressableLEDBuffer buffer;
    private AddressableLED led;
    private short lenght;

    public Blinkens() {
        //Setup the LED lenght(buffer) and map the port
        buffer = new AddressableLEDBuffer(Constants.Blinken.BUFFER);
        led = new AddressableLED(RobotMap.PWM.BLINKEN);
        //Configure the settings for the blinkens
        led.setLength(this.buffer.getLength());
        led.setData(this.buffer);
        led.start();
   }

   public void setColor(int red, int green, int blue){
       for(var i = 0; i < buffer.getLength(); i++){
           buffer.setRGB(i, red, green, blue);
        }
        led.setData(buffer);
   }

   public void setDefualt(){
       for(var i = 0; i < buffer.getLength(); i++){
           //Hot pink lets go
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

   protected class Colors{
       //In the long run we will be useing this class to store more dynamic colours
       protected short[] green = {0,100,0};
       protected short[] blue = {0,0,255};
   }
}