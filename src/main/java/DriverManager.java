import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.ChromeDriverManager;

/**
 * Created by agr on 5/8/18.
 */
class DriverManager {
    /**
     * Initialize webdriver.
     */
    static void initDriverWithMobProxy() {
        ChromeDriverManager.chromedriver().setup();

        Configuration.pageLoadStrategy = "normal";
        Configuration.proxyEnabled = true;
    }
}
