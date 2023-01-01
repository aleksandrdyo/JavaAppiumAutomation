import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass{
    @Test
    public void testGetLocalNumber(){

        //В классе MainClassTest написать тест, проверяющий, что метод getLocalNumber
        // возвращает число 14 (назвать: testGetLocalNumber).
        //Не забываем в проверку добавлять понятный текст ошибки.
        Assert.assertTrue("getLocalNumber not equal 14 ",14 == this.getLocalNumber());

    }

    @Test
    public void testGetClassNumber() {
        //В классе MainClassTest написать тест (назвать testGetClassNumber), который проверяет,
        // что метод getClassNumber возвращает число больше 45.
        Assert.assertTrue("getClassNumber less then 45",this.getClassNumber() > 45 );
    }

    @Test
    public void testGetClassString() {
        //В классе MainClassTest написать тест (назвать testGetClassString),
        // который проверяет, что метод getClassString возвращает строку,
        // в которой есть подстрока “hello” или “Hello”, если нет ни одной из подстрок - тест падает.
        Assert.assertTrue("No substring Hello", this.getClassString().contains("Hello"));
        Assert.assertTrue("No substring hello", this.getClassString().contains("hello"));

    }
}

