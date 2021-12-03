package org.frc5687.swerve.util;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import java.awt.*;

public class Blinkins extends AddressableLED {

    public AddressableLEDBuffer blinkinBuffer;
    private int bufferSize;

    /**
     * Constructs a new driver for a specific port.
     *
     * @param port the output port to use (Must be a PWM header, not on MXP)
     */
    public Blinkins(int port, int bufferSize) {
        super(port);
        /* Create buffer and set the LED length */
        this.blinkinBuffer = new AddressableLEDBuffer(bufferSize);
        this.setLength(this.blinkinBuffer.getLength());

        /* Set data and start the blinkins */
        this.setData(this.blinkinBuffer);
        this.start();
    }

    public void setSolidColor(Color color) {
        //iterate though the buffer and set the color to the passed in color
        for (int i = 0; i < this.blinkinBuffer.getLength(); i++) {
            //set the RGB value of the current position in the buffer
            this.blinkinBuffer.setRGB(i, color.getRed(), color.getGreen(), color.getBlue());
        }
        this.setData(this.blinkinBuffer);
    }

    public void update() {
        super.setData(this.blinkinBuffer);
    }

}
