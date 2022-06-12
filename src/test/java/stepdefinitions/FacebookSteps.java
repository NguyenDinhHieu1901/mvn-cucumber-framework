package stepdefinitions;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FacebookSteps {
	WebDriver driver;
	
    @Given("^Open Facebook application$")
    public void open_facebook_application()  {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();
        driver.get("https://www.facebook.com/");
    }

    @Then("^Verify email textbox is displayed$")
    public void verify_email_textbox_is_displayed()  {
       Assert.assertTrue(driver.findElement(By.id("email")).isDisplayed()); 
    }
    
    @And("^Verify password textbox is displayed$")
    public void verify_password_textbox_is_displayed()  {
    	Assert.assertTrue(driver.findElement(By.id("pass")).isDisplayed()); 
    }

    @And("Close application$")
    public void closeApplication()  {
    	driver.quit();
    }

}
