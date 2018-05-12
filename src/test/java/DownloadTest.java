import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * Created by a.gridyaev on 5/8/18.
 */
public class DownloadTest {

    private static final String downloadFolderPath = System.getProperty("downloadFolderPath");

    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver("https://www.adam.com.au/support/blank-test-files", downloadFolderPath);
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

        extraSmallFileMD5.delete();
    }

    @Test(enabled = true)
    public void DownloadFileByClick() throws FileNotFoundException, InterruptedException {
        File downloadFolder = new File(downloadFolderPath);

        System.out.println(String.format("Directory \"%s\" exists: %s", downloadFolder.getAbsolutePath(), downloadFolder.exists()));
        System.out.println(String.format("Path \"%s\" is directory: %s", downloadFolder.getAbsolutePath(), downloadFolder.isDirectory()));

        $("table tbody tr:nth-child(1) td.last a").click();

        Thread.sleep(5000);

        File expectedDownloadedFile = new File(downloadFolder.getAbsolutePath() + "/SpeedTest_16MB.md5");
        File actualDownloadedFile = new File(System.getProperty("user.home") + "/Downloads/" + "SpeedTest_16MB.md5");

        System.out.println(String.format("Expected downloaded file \"%s\" exists: %s", expectedDownloadedFile.getAbsoluteFile(), expectedDownloadedFile.exists()));
        System.out.println(String.format("Actual downloaded file \"%s\" exists: %s", actualDownloadedFile.getAbsoluteFile(), actualDownloadedFile.exists()));

        actualDownloadedFile.delete();
    }
}
