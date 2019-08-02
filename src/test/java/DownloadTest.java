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
    private static final String TEST_URL = "https://www.adam.com.au/support/blank-test-files";
    private static final String DOWNLOAD_SELECTOR = "table tbody tr:nth-child(1) td.last a";

    private static File downloadFile() {
        File expectedDownloadedFile = null;
        try {
            expectedDownloadedFile = $(DOWNLOAD_SELECTOR).download();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expectedDownloadedFile;
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
        open(TEST_URL);

        File expectedDownloadedFile = downloadFile();

        assertThat(expectedDownloadedFile).isFile();
    }

    @Test
    public void DownloadFileByProxy() {
        Configuration.fileDownload = FileDownloadMode.PROXY;
        open(TEST_URL);

        File expectedDownloadedFile = downloadFile();

        assertThat(expectedDownloadedFile).isFile();
    }
}
