import org.junit.*;
import static org.junit.Assert.assertEquals;


public class MaverickTest {
    private static double DELTA = 1e-2;
    @Test
    public void testGetTheAngluarVelocityVector(){
        assertEquals(5.0, getTheAngluarVelocityVector(4.0, 3.0), DELTA);
    }

    public double getTheAngluarVelocityVector(double vx, double vy){
        //Using the pythagorean theorem where a^2 is the X velocity 
        //b^2 is the Y velocity
        //Thus c^2 is the angular movement velocity
        //Square root of X velocity squared added to Y velocity squared
        return Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
    }
}