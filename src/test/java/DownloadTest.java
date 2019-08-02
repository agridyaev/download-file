import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by a.gridyaev on 5/8/18.
 */
public class DownloadTest {
    private void printHar(Har har) {
        List<HarEntry> entries = har.getLog().getEntries();
        for (HarEntry entry : entries) {
            System.out.println(entry.getRequest().getUrl());
        }
    }

    @BeforeMethod
    public void setUp() {
        DriverManager.initDriverWithMobProxy();
    }

    @AfterMethod
    public void tearDown(ITestContext testContext) {
        if (getWebDriver() != null) {
            getWebDriver().quit();
        }
    }

    @Test
    public void DownloadFileByHTTP() {
        Configuration.fileDownload = FileDownloadMode.HTTPGET;
        open("https://www.adam.com.au/support/blank-test-files");

        File expectedDownloadedFile = null;
        try {
            expectedDownloadedFile = $("table tbody tr:nth-child(1) td.last a").download();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assertThat(expectedDownloadedFile).isFile();
    }

    @Test
    public void DownloadFileByProxy() {
        Configuration.fileDownload = FileDownloadMode.PROXY;
//        open("https://www.adam.com.au/support/blank-test-files");
        open("https://www.engineerhammad.com/2015/04/Download-Test-Files.html");

        File expectedDownloadedFile = null;
        try {
            expectedDownloadedFile = $("tbody tr:nth-child(2) td:nth-child(3) a:nth-child(2)").download();
//            expectedDownloadedFile = $("table tbody tr:nth-child(1) td.last a").download(30000);
//            expectedDownloadedFile = Selenide.download("http://mirror.filearena.net/pub/speed/SpeedTest_16MB.md5");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(expectedDownloadedFile).isFile();
    }
}
