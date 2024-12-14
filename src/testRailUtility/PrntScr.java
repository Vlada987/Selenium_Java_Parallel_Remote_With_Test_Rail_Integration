package testRailUtility;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class PrntScr {

	public static File takeScreenshot(WebDriver driver) {

		TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
		File screenshot = screenshotDriver.getScreenshotAs(OutputType.FILE);
		return screenshot;
	}

}
