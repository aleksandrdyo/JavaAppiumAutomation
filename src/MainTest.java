import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainTest extends CoreTestCase {

    @Before
    public void testStartTest(){
        System.out.println("i am method \"Before\" i am start test first");
    }

    @After
    public void testEndTest(){
        System.out.println("i am method \"After\" i am finish test");
    }

    @Test
    public void myFirstTest() {
        System.out.println("FirstTest");
    }

    @Test
    public void myScondTest() {
        System.out.println("SecondTest");
    }


}
