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
                "iiiiiiooooooooooo",
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
    //lesson 02. Swipe till element found: swipeQuick, counter,...
    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' article in search",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                5

        );

        //swipeUp(2000);
        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of the article",
                10
        );
    }

    //lesson03. Save first article: overlay, swipe left, variable
    //04. Swipe: debugging
    @Test
    public void testSaveFirstArticleToMyList() {

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
                15

        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button X to open article options",
                5
        );

       waitForElementAndClick(
                By.xpath("//android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

       waitForElementAndClick(
                By.xpath("//*[@text = 'GOT IT']"),
                "Cannot find Got it",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find text input to set name of articles folder",
                5
        );

        String nameOfFolder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into articles folder input",
                5
        );

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot find OK button",
                5
        );

        waitForElementAndClick(

                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X button",
                5
        );

        waitForElementAndClick(

                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                15
        );

        waitForElementAndClick(

                By.xpath("//*[@text='"+nameOfFolder+"']"),
                "Cannot open Learning programming",
                15
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find created article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5

                );
    }

    //05. Assert: basic
    @Test
    public void testAmountOfNotEmptySearch() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search wikipedia input",
                5
        );

        //String search_name = "Linkin park discography";
        String search_name = "java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_name,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_container']";

        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_name,
                15
        );

        int amount_of_search_results = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        System.out.println(amount_of_search_results);

        Assert.assertTrue(
                "we found too few results",
                amount_of_search_results > 0
                );

    }

    @Test
    public void testAmountOfEmptySearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search wikipedia input",
                5
        );

//        String search_name = "ioioioioioioioioi";
        String search_name = "java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_name,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_container']";

        String empty_result_label = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty results label " + search_name,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We found some results by request"
        );
    }


    //Ex5: Тест: сохранение двух статей. Написать тест, который:
    //1. Сохраняет две статьи в одну папку
    //2. Удаляет одну из статей
    //3. Убеждается, что вторая осталась
    //4. Переходит в неё и убеждается, что title совпадает
    @Test
    public void testSaveTwoArticles() {
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
                15

        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button X to open article options",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text = 'GOT IT']"),
                "Cannot find Got it",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find text input to set name of articles folder",
                5
        );

        String nameOfFolder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into articles folder input",
                5
        );

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot find OK button",
                5
        );

        waitForElementAndClick(

                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X button",
                5
        );


        //find and save second element
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
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='High-level programming language']"),
                "Cannot find search wikipedia input",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15

        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button X to open article options",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text = '" + nameOfFolder + "']"),
                "Cannot folder " + nameOfFolder,
                5
        );

        waitForElementAndClick(

                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X button",
                5
        );

        waitForElementAndClick(

                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                15
        );

        waitForElementAndClick(

                By.xpath("//*[@text='"+nameOfFolder+"']"),
                "Cannot open Learning programming",
                15
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/reading_list_contents']//*[@resource-id='org.wikipedia:id/page_list_item_container']";

        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot found element in folder",
                10
        );

        assertElementsPresent(
                By.xpath(search_result_locator),
                "is not equal 2"
        );


    }

    //Ex6: Тест: assert title
    //Написать тест, который открывает статью и убеждается, что у нее есть элемент title.
    //Важно: тест не должен дожидаться появления title, проверка должна производиться сразу.
    // Если title не найден - тест падает с ошибкой. Метод можно назвать assertElementPresent.

    //Ex7*: Поворот экрана
    //Appium устроен так, что может сохранить у себя в памяти поворот экрана,
    // который использовался в предыдущем тесте, и начать новый тест с тем же поворотом.
    // Мы написали тест на поворот экрана, и он может сломаться до того, как положение экрана восстановится.
    // Следовательно, если мы запустим несколько тестов одновременно, последующие тесты будут выполняться в
    // неправильном положении экрана, что может привести к незапланированным проблемам.
    //Как нам сделать так, чтобы после теста на поворот экрана сам экран всегда оказывался в правильном положении,
    // даже если тест упал в тот момент, когда экран был наклонен?

    //Методы
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

    protected void swipeUp(int timeOfSwipe) {

        TouchAction action = new TouchAction(driver);

        Dimension size = driver.manage().window().getSize();//передаем параметры экрана девайса

        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {

        //driver.findElements(by);
        //driver.findElements(by).size();

        int already_swiped = 0;

        while (driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "cannot find element by swiping up. \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message) {

        WebElement element = waitForElementPresent(by, error_message, 10);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();

        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();

        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();

    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        //System.out.println(elements.size());
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element " + by.toString() + "supposed to by not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private void assertElementsPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements != 2) {
            //System.out.println(error_message);
            String default_message = "Count of found elements by " + by.toString() + " ";
            throw new AssertionError(default_message + " " + error_message);
        }

    }



}
