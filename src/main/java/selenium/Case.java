package selenium;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;


import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class Case {
    private ChromeDriver driver;

    private Manage manage;

    public Case() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        driver = new ChromeDriver(options);
//        manage=new BaiDuWangPanLogin();
        manage = new BokeYuanLogin();
    }

    public void login() {

        driver.get(System.getProperty("url"));
        System.out.println(driver.getWindowHandles());
        try {
            String title = driver.getTitle();
//            Thread.sleep(3000);
            manage.loginOpt();


        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
        }
    }

    public void loginOpt() {
        driver.switchTo().frame("login-iframe");
        Actions action = new Actions(driver);
        By username = By.name("username");
        By userpass = By.name("userpass");
        By by = By.className("domain-body-submit-control");
        action.sendKeys(driver.findElement(username), System.getProperty("name")).perform();
        action.sendKeys(driver.findElement(userpass), System.getProperty("pass")).perform();
        action.moveToElement(driver.findElement(by));
        action.click();
        action.perform();
    }

    public void base() {

        Actions action = new Actions(driver);
        By username = By.name("username");
        By userpass = By.name("userpass");
        By by = By.className("domain-body-submit-control");
        action.sendKeys(driver.findElement(username), System.getProperty("name")).perform();
        action.sendKeys(driver.findElement(userpass), System.getProperty("pass")).perform();
        action.moveToElement(driver.findElement(by));
        action.click();
        action.perform();
    }

    public static void main(String[] args) throws IOException {
        Properties properties = System.getProperties();
        properties.load(Case.class.getClassLoader().getResourceAsStream("selenuim.properties"));
        Case demo = new Case();
//        System.out.println(demo.driver.getTitle());
        demo.login();
//        test();
    }

    /**
     * 切换窗口
     *
     * @param driver       webDriver
     * @param windowsTitle 要切换到的windows的title
     * @return
     */
    public static boolean switchWindows(WebDriver driver, String windowsTitle) {
        //获取所有的窗口句柄
        Set<String> handles = driver.getWindowHandles();
        //获取当前窗口的句柄
        String currentHandle = driver.getWindowHandle();
        //获取当前窗口的title
        String currentTitle = driver.getTitle();
        //要切换窗口为当前窗口则直接返回true
        if (currentTitle.equals(windowsTitle)) {
            return true;
        }
        //处理要切换到的窗口非当前窗口的情况
        for (String string : handles) {
            //略过当前窗口
            if (string.equals(currentHandle)) {
                continue;
            }
            //切换并检查其title是否和目标窗口的title是否相同，是则返回true，否则继续
            if ((driver.switchTo().window(string).getTitle()).equals(windowsTitle)) {
                return true;
            }
        }
        return false;
    }

    //安全验证滑动条出错，淘宝网也是一样
    public class CSDNLogin implements Manage {

        @Override
        public void loginOpt() {
            Actions action = new Actions(driver);
            By by = By.xpath("");
            action.click(driver.findElementByXPath("//a[text()='账号登录']")).perform();
            WebElement elementById = driver.findElementByXPath("//input[@placeholder=\"手机号/邮箱/用户名\"]");
            elementById.clear();//elementById文本内容还是空的
            action.sendKeys(elementById, System.getProperty("name")).perform();
            elementById = driver.findElementByXPath("//input[@placeholder=\"密码\"]");
            elementById.clear();
            action.sendKeys(elementById, System.getProperty("pass")).perform();
            action.click(driver.findElementByXPath("//button[text()='登录']")).perform();
        }
    }

    public class BokeYuanLogin implements Manage {

        @Override
        public void loginOpt() {
            Actions action = new Actions(driver);

            WebElement elementById = driver.findElementByXPath("//input[@placeholder='登录用户名 / 邮箱']");
            elementById.clear();//elementById文本内容还是空的
            action.sendKeys(elementById, "乐可2016").perform();
            elementById = driver.findElementByXPath("//input[@placeholder=\"密码\"]");
            elementById.clear();
            action.sendKeys(elementById, System.getProperty("password")).perform();
            elementById = driver.findElementByXPath("//label[text()='记住我']");
            action.click(elementById).perform();
            action.click(driver.findElementByXPath("//span[text()='登录']")).perform();
            //滑块验证
            elementById=driver.findElementByClassName("geetest_canvas_slice geetest_absolute");
//            .moveToElement(elementById,130,80)
            action.clickAndHold(elementById).moveByOffset(0,130).perform();
        }
    }

    public class  BaiDuWangPanLogin implements Manage {

        @Override
        public void loginOpt() {
            Actions action = new Actions(driver);

            WebElement elementById = driver.findElementByXPath("//input[@placeholder=\"手机/邮箱/用户名\"]");
            elementById.clear();//elementById文本内容还是空的
            action.sendKeys(elementById, System.getProperty("name")).perform();
            elementById = driver.findElementByXPath("//input[@placeholder=\"密码\"]");
            elementById.clear();
            action.sendKeys(elementById, System.getProperty("pass")).perform();
            action.click(driver.findElementByXPath("//input[@value='登录']")).perform();

            try {
                Thread.sleep(3000);//等待交互元素出现
                driver.findElementByXPath("//span[text()='验证手机']");
            } catch (Exception e) {
                return;
            }
            action.click(driver.findElementByXPath("//p[text()='跳过']")).perform();
        }
    }

    public interface Manage {
        public void loginOpt();
    }


}
