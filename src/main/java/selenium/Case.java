package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Case {
    public static void test(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Administrator\\Downloads\\chromeDriver.exe");
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.baidu.com/");

    }

    public static void main(String[] args) {
        test();
    }
}
