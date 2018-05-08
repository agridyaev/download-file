import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;

import static com.codeborne.selenide.Selenide.open;

/**
 * Created by agr on 5/8/18.
 */
public class DriverManager {

    /**
     * Initialize webdriver and open home page.
     * @param   startPage    Starting page for test
     */
    public static void initDriver(String startPage, String downloadFolderPath) {
        ChromeDriverManager.getInstance().setup();

        HashMap<String, Object> chromePreferences = new HashMap<>();
        chromePreferences.put("download.default_directory", downloadFolderPath);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", chromePreferences);

        Configuration.browserCapabilities = new DesiredCapabilities();
        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        Configuration.browser = System.getProperty("selenide.browser");
        Configuration.pageLoadStrategy = "normal";

        open(startPage);
    }
}
