import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass{
    @Test
    public void test(){

        Assert.assertTrue("getLocalNumber not equal 14 ",14 == this.getLocalNumber());
        //В классе MainClassTest написать тест, проверяющий,
        // что метод getLocalNumber возвращает число 14 (назвать: testGetLocalNumber).
        //Не забываем в проверку добавлять понятный текст ошибки.
    }
}

