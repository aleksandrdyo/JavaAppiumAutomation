import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;


public class FirstTest {

    //объявление appium driver
    private AppiumDriver driver;

    //метод где устанавливаются все необходимые параметры для запуска аппиум драйвера и поднятия нужного приложения на устройстве
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/dyoaleksandr/Git/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    //метод для выключения драйвера после окончания теста
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Object-oriented programming language topic searching by Java",
                15
        );

    }


    @Test
    public void testCancelSearch() {
        //
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find search wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );

    };

    //тест на сравнение статьи с ОР и ФР
    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find search wikipedia input",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                5

        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );


    }

    //Ex2: Создание метода
    //Также, необходимо написать тест, который проверяет, что поле ввода для поиска статьи содержит текст
    // (в разных версиях приложения это могут быть тексты "Search..." или "Search Wikipedia",
    // правильным вариантом следует считать тот, которые есть сейчас).
    // Очевидно, что тест должен использовать написанный ранее метод.
    @Test
    public void testCheckNamePlaceholderInSearchInput() {
        assertElementHasText(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Search Wikipedia",
                "Cannot find placeholder text",
                5
        );

    }

/*    Ex3: Тест: отмена поиска
    Написать тест, который:+
    Ищет какое-то слово+
    Убеждается, что найдено несколько статей+
    Отменяет поиск
    Убеждается, что результат поиска пропал*/
    @Test
    public void testSearchTextCheckArticlesCancelSearch() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "bmw",
                //"iiiiiiiiiiiioooooooo",
                "Cannot find search input",
                5
        );

        waitForElementsPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot find search elements",
                5

        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/search_empty_message"),
                "Found elements is still present on the page",
                5
        );
    }

    //Ex4*: Тест: проверка слов в поиске
    //Написать тест, который делает поиск по какому-то слову.
    // Например, JAVA. Затем убеждается, что в каждом результате поиска есть это слово.
    //Внимание, прокручивать результаты выдачи поиска не надо.
    // Это мы научимся делать на следующих занятиях.
    // Пока надо работать только с теми результатами поиска, который видны сразу, без прокрутки.
    @Test
    public void testCheckWordsInSearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "bmw",
                "Cannot find search input",
                5
        );

        List <WebElement> test = waitForElementsPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find search elements",
                5
        );

        for(int i = 0; i<test.size(); i++){
            String yyy = test.get(i).getAttribute("text");
            //System.out.println(yyy);
            Assert.assertTrue("no bmw", yyy.contains("BMW"));

        }
    }


    //lesson 01. Swipe: start_x, Touch action, dimensions
    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find search wikipedia input",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                5

        );

        swipeUP(2000);
        swipeUP(2000);
        swipeUP(2000);
        swipeUP(2000);
        swipeUP(2000);



    }


    //
    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message+"\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));

    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long tomeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, tomeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    //Метода проверке отсутствия элемента на странице. Метод возвращает true or false
    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message+"\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    //метод
    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, error_message, 5);
        element.clear();
        return element;
    }

    //Ex2: Создание метода
    //Необходимо написать функцию, которая проверяет наличие ожидаемого текста у элемента.
    // Предлагается назвать ее assertElementHasText. На вход эта функция должна принимать локатор элемент,
    // ожидаемый текст и текст ошибки, который будет написан в случае, если элемент по этому локатору не содержит текст,
    // который мы ожидаем.
    private WebElement assertElementHasText(By by, String expected_result, String error_message, long timeout) {

        WebElement element = waitForElementPresent(by, error_message);

        Assert.assertEquals(error_message, expected_result, element.getText());

        return element;
    }

    //Ex3: Тест: отмена поиска
    //Метод для поиска нескольких одинаковых элементов
    private List <WebElement> waitForElementsPresent(By by, String error_message, long timeoutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message+"\n");
        //List<WebElement> elementName = driver.findElements(by);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));

    }

    protected void swipeUP(int timeOfSwipe) {

        TouchAction action = new TouchAction(driver);

        Dimension size = driver.manage().window().getSize();//передаем параметры экрана девайса

        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform();
    }

    //Ex4*: Тест: проверка слов в поиске
    //Метод для поиска слова в каждом результате поиска.
    /*private List <WebElement> waitForWordInEveryElementsPresent(By by, String error_message, long timeoutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_message+"\n");
        //List<WebElement> elementName = driver.findElements(by);
        //return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        return wait.until(ExpectedConditions.);

    }*/




}
