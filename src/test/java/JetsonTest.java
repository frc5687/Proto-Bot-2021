import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.frc5687.swerve.util.Jetson;

public class JetsonTest {
    
    private Jetson jetson;

    @Test
    public void testPostProcces(){
        jetson = new Jetson();
        assertEquals(false, jetson.isServerRunning());
    }
}
