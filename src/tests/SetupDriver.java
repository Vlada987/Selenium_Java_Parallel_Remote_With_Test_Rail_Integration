package tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import pages.Byes;

public class SetupDriver {

	static String sauceUrl = "https://ondemand.eu-central-1.saucelabs.com:443/wd/hub";
	public WebDriver driver;

	public static WebDriver remoteForParallel(String browser, String os) throws MalformedURLException {

		String username = System.getenv("sauceUsername");
		String accessKey = System.getenv("saucePassword");
		WebDriver driver = null;
		if (browser.equalsIgnoreCase("edge")) {
			EdgeOptions browserOptions = new EdgeOptions();
			browserOptions.setPlatformName(os);
			browserOptions.setBrowserVersion("latest");
			Map<String, Object> sauceOptions = new HashMap<>();
			sauceOptions.put("username", username);
			sauceOptions.put("accessKey", accessKey);
			sauceOptions.put("build", "1.0");
			sauceOptions.put("name", "EdgeTest " + System.currentTimeMillis());
			sauceOptions.put("screenResolution", "1024x768");
			browserOptions.setCapability("sauce:options", sauceOptions);
			URL url = new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub");
			driver = new RemoteWebDriver(url, browserOptions);

		} else if (browser.equalsIgnoreCase("firefox")) {
			FirefoxOptions browserOptions = new FirefoxOptions();
			browserOptions.setPlatformName(os);
			browserOptions.setBrowserVersion("latest");
			Map<String, Object> sauceOptions = new HashMap<>();
			sauceOptions.put("username", username);
			sauceOptions.put("accessKey", accessKey);
			sauceOptions.put("build", "1.0");
			sauceOptions.put("name", "mozilaTest " + System.currentTimeMillis());
			sauceOptions.put("screenResolution", "1024x768");
			browserOptions.setCapability("sauce:options", sauceOptions);
			URL url = new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub");
			driver = new RemoteWebDriver(url, browserOptions);
		}
		return driver;
	}

	public WebDriver localDriver(String browser) {
		WebDriver driver;

		if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", Byes.gecko);
			driver = new FirefoxDriver();

		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", Byes.edge);
			driver = new EdgeDriver();
		} else {
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.manage().window().maximize();
		return driver;
	}
}