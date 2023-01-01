import org.junit.Assert;
import org.junit.Test;

public class MainTest extends CoreTestCase {

    @Test
    public void myFirstTest() {
        int expected = 20;
        int actual = 5*5;
        Assert.assertTrue("5*5!=25", expected==actual);
    }

}
