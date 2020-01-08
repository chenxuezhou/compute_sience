package selenium;


import org.openqa.selenium.By;
//打开firefox浏览器的http://baidu.com网址
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Case1 {

    public static void main(String[] args){

        //  WebDriver driver = new FirefoxDriver();
        System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("http://baidu.com");
        WebElement input = driver.findElement(By.xpath(".//*[@id='kw']"));
        CharSequence[] cs = new CharSequence[1];
        cs[0]="安居客";
        input.sendKeys(cs);

        WebElement btn = driver.findElement(By.xpath(".//*[@id='su']"));
        btn.click();
        // WebElement btn1 = driver.findElement(By.xpath(".//*[@id='w-75cn8k']/div/h2/a[1]"));
        //btn1.click();
        System.out.println("Page title is:"+driver.getTitle());
        //Sleep(2000);
        driver.close();
    }


}

