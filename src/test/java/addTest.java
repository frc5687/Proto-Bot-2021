package org.frc5687.swerve;

import org.junit.*;

import static org.junit.Assert.assertEquals;

import org.frc5687.swerve.subsystems.Add;

public class addTest {
    Add add;
    @Test
    public void addTest(){
        add = new Add();
        assertEquals(2, add.add(1, 1));
    }

    @Test
    public void mulitiTest(){
        add = new Add();
        assertEquals(4, add.muliti(2, 2));
    }
}
