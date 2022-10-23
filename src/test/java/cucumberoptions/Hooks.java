package cucumberoptions;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.inf.commons.GlobalConstants;

import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks {
	private static WebDriver driver;
	private static final Logger LOGGER = LogManager.getLogger(Hooks.class.getName());
	
	@Before
	public synchronized static WebDriver openAndQuitBrowser() {
		String browserName = System.getProperty("Browser");
		LOGGER.info("*** Browser name run by command line = " + browserName);
		
		// Check does driver initialize?
		if (driver == null) {
			try {
				if (browserName == null) {
					browserName = System.getenv("Browser");
					if (browserName == null) {
						browserName = "chrome";
					}
				}
				
				switch(browserName) {
				case "chrome":
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver();
					break;
				case "firefox":
					WebDriverManager.firefoxdriver().setup();
					driver = new FirefoxDriver();
					break;
				case "edge":
					WebDriverManager.edgedriver().setup();
					driver = new EdgeDriver();
					break;
				default:
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver();
					break;
				}
			} catch(Exception e) {
				LOGGER.info("*** Browser name invalid. Please check again! ***", e);
			} finally {
				Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
			}
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
			driver.manage().window().maximize();
			driver.get(GlobalConstants.BANK_GURU_URL);
		}
		
		return driver;
	}
	
	public static void close() {
		try {
			if (driver != null) {
				openAndQuitBrowser().quit();
				LOGGER.info("*** Closed the browser ***");
			}
		} catch(UnreachableBrowserException e) {
			LOGGER.info("*** Can not close the browser ***", e);
		}
	}
	
	private static class BrowserCleanup implements Runnable {
		@Override
		public void run() {
			close();
		}
	}
}
