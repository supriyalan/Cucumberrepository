package extentreports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class SimpleExtentReport {

	 ExtentReports extent = new ExtentReports();
	 File file = new File(System.getProperty("user.dir")+"\\target\\spark.html");
	 ExtentSparkReporter spark = new ExtentSparkReporter(file);
	 static WebDriver driver;
	
	@BeforeTest
	public void openurl()
	{
		spark.config().setTheme(Theme.STANDARD);
		spark.config().setDocumentTitle("FirstExtentReport");
		extent.attachReporter(spark);
		driver = new ChromeDriver();
		driver.get("https://www.youtube.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		
	}
	
	@Test
	public void search() throws IOException
	{
		ExtentTest test = extent.createTest("searching in the searchbar of youtube","This test is to verify the search bar with extent report keyword").assignAuthor("Supriya").assignCategory("Regression").assignDevice("Windows");
		test.info("Searching extent reports keyword");
		test.info(MarkupHelper.createLabel("Searching extent reports keyword", ExtentColor.ORANGE));
		driver.findElement(By.xpath("//input[@id='search']")).sendKeys("Extent Report");
		test.pass("<i>Home page clicked successfully</i>");	
		test.addScreenCaptureFromPath(screenshot(driver),"Search page screenshot");
		test.addScreenCaptureFromBase64String(bse64screenshot(),"Search page screenshot from base64");
	}
	
	
	@Test
	public void homepage() throws IOException
	{
		ExtentTest test = extent.createTest("Clicking the home page","This test is to check the home page").assignAuthor("Supriya").assignCategory("Regression").assignDevice("Windows");
		test.info(MediaEntityBuilder.createScreenCaptureFromBase64String(bse64screenshot(), "Clicking the home page on YouTube at log level").build());
		//test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromBase64String(bse64screenshot(), "Clicking the home page on YouTube at at level").build());
		driver.findElement(By.linkText("Home")).click();
		test.fail("Home page not clicked properly");
		test.addScreenCaptureFromPath(screenshot(driver),"Home page screenshot at test level from path");
		test.addScreenCaptureFromBase64String(bse64screenshot(),"Home page screenshot from base64");
		
	}
	
	public static String screenshot(WebDriver driver) throws IOException
	{
		File sourcefile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String  destinationfile = System.getProperty("user.dir")+"\\Images\\screenshot.png" ; 
		FileHandler.copy(sourcefile,new File(destinationfile));
		return destinationfile;
	}
	
		public static String bse64screenshot()
		{
		String base64screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		return base64screenshot;
	}
			 
	@AfterTest
	public void closebrowser() throws IOException
	{
		driver.quit();
		//Desktop.getDesktop().browse(file.toURI());
		extent.flush();
	}
}
