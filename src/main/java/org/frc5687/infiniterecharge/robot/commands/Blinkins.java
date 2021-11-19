package org.frc5687.infiniterecharge.robot.commands;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import java.awt.*;

public class Blinkins {

    public final int bufferSize;
    public final int port;
    private final AddressableLED blinkin;
    public AddressableLEDBuffer blinkinBuffer;

    public Blinkins(int port, int bufferSize) {
        this.port = port;
        this.bufferSize = bufferSize;

        //Initialize the blinkin
        this.blinkin = new AddressableLED(port);

        //Initialize the buffer
        this.blinkinBuffer = new AddressableLEDBuffer(bufferSize);

        //Set the buffer length
        this.blinkin.setLength(bufferSize);

        //Set the blinkin buffer
        this.blinkin.setData(this.blinkinBuffer);

        //start the blinkin
        this.blinkin.start();
    }

    /**
     * Set the color of every LED in the blinkin strip
     * For i the length of the buffer and set the color of that point in the buffer to color {@param Color}
     * @param color the color to set the blinkin strip to
     */
    public void setSolidColor(Color color) {
        //iterate though the buffer and set the color to the passed in color
        for (int i = 0; i < blinkinBuffer.getLength(); i++) {
            //set the RGB value of the current position in the buffer
            blinkinBuffer.setRGB(i, color.getRed(), color.getGreen(), color.getBlue());
        }
    }

    /**
     * Update the blinkin data
     */
    public void updateData() {
        //update the blinkin
        blinkin.setData(blinkinBuffer);
    }
}
