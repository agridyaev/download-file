import com.codeborne.selenide.proxy.SelenideProxyServer;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.filters.RequestFilter;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getSelenideProxy;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * Created by a.gridyaev on 5/8/18.
 */
public class DownloadTest {
    private BrowserMobProxy proxy;
    private URL testUrl;
    {
        try {
            testUrl = new URL("https://www.adam.com.au/support/blank-test-files");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static final String downloadFolderPath = System.getProperty("downloadFolderPath");

    @BeforeMethod
    public void setUp() {
        proxy = DriverManager.initDriver(testUrl, downloadFolderPath);
    }

    @AfterMethod
    public void tearDown(ITestContext testContext) {
        if (getWebDriver() != null) {
            getWebDriver().quit();
        }
    }

    @Test(enabled = false)
    public void DownloadFileBySelenide() throws FileNotFoundException {
        System.out.println(String.format("Folder \"%s\" exists: %s", downloadFolderPath, new File(downloadFolderPath).exists()));

        File extraSmallFileMD5 = $("table tbody tr:nth-child(1) td.last a").download();

        System.out.println(String.format("Downloaded file path: %s", extraSmallFileMD5.getAbsolutePath()));
        System.out.println(String.format("Downloaded file size: %d bytes", extraSmallFileMD5.length()));

        Assert.assertTrue(extraSmallFileMD5.delete());
    }

    @Test(enabled = true)
    public void DownloadFileByClick() {
        File downloadFolder = new File(downloadFolderPath);

        System.out.println(String.format("Directory \"%s\" exists: %s", downloadFolder.getAbsolutePath(), downloadFolder.exists()));
        System.out.println(String.format("Path \"%s\" is directory: %s", downloadFolder.getAbsolutePath(), downloadFolder.isDirectory()));

        proxy.newHar(testUrl.getHost());
        $("table tbody tr:nth-child(1) td.last a").click();
        Har har = proxy.getHar();
        List<HarEntry> entries = har.getLog().getEntries();
        for (HarEntry entry : entries) {
            System.out.println(entry.getRequest().getUrl());
        }

        File expectedDownloadedFile = new File(downloadFolder.getAbsolutePath() + "/SpeedTest_16MB.md5");
        File actualDownloadedFile = new File(System.getProperty("user.home") + "/Downloads/" + "SpeedTest_16MB.md5");

        System.out.println(String.format("Expected downloaded file \"%s\" exists: %s", expectedDownloadedFile.getAbsoluteFile(), expectedDownloadedFile.exists()));
        System.out.println(String.format("Actual downloaded file \"%s\" exists: %s", actualDownloadedFile.getAbsoluteFile(), actualDownloadedFile.exists()));

        Assert.assertTrue(actualDownloadedFile.delete());
    }
}
