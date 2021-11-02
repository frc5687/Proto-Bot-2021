package org.frc5687.swerve.subsystems;

import org.junit;

public class Add {
    public int add(int one, int two){
        return one + two;
    }

    @Test
    public void testAdd(){
        assertEquals(1, add(1, 1));
        assertEquals(1, add(2, 1));
    };
}
