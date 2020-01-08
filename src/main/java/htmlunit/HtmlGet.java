package htmlunit;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * @author hj
 * @version 创建时间：2016-7-5 下午9:30:49
 */
public class HtmlGet {
    public static void main(String[] args) {
        // 得到浏览器对象，直接New一个就能得到，现在就好比说你得到了一个浏览器了
        WebClient webclient = new WebClient();


        // 这里是配置一下不加载css和javaScript,配置起来很简单，是不是
        webclient.getOptions().setCssEnabled(false);
        webclient.getOptions().setJavaScriptEnabled(false);

        // 做的第一件事，去拿到这个网页，只需要调用getPage这个方法即可
        HtmlPage htmlpage = null;
        try {
            htmlpage = (HtmlPage) webclient
                    .getPage("https://www.baidu.com/");
        } catch (FailingHttpStatusCodeException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // 根据名字得到一个表单，查看上面这个网页的源代码可以发现表单的名字叫“f”
        final HtmlForm form = htmlpage.getFormByName("f");
        // 同样道理，获取”百度一下“这个按钮
        final HtmlSubmitInput button = (HtmlSubmitInput) form.getInputByValue("百度一下");
        // 得到搜索框
        final HtmlTextInput textField = (HtmlTextInput) form.getInputByName("wd");
        // 最近周星驰比较火呀，我这里设置一下在搜索框内填入”周星驰“
        textField.setValueAttribute("苍井空");
        // 输入好了，我们点一下这个按钮
        HtmlPage nextPage = null;
        try {
            nextPage = (HtmlPage) button.click();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 我把结果转成String
        String result = nextPage.asXml();
        System.out.println(result);
    }
}
