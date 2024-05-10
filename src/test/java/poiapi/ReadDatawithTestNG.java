package poiapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import extentreports.SimpleExtentReport;


public class ReadDatawithTestNG extends SimpleExtentReport {
	
	ChromeDriver driver;
	ExtentSparkReporter spark;
	ExtentReports extent;
	
	@Test(dataProvider="Supplier")
	public void readUsernameandpassword(String email,String password)
	{

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.get("https://tutorialsninja.com/demo/");
		driver.findElement(By.xpath("//i[@class='fa fa-user']")).click();
		driver.findElement(By.linkText("Login")).click();
		ExtentReports extent = new ExtentReports();
		File extentfile = new File(System.getProperty("user.dir")+"\\test-output\\ExtentReports\\extentreport.html");
		ExtentSparkReporter spark = new ExtentSparkReporter(extentfile);
		
		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("FirstExtentReport");
		extent.attachReporter(spark);
		ExtentTest test = extent.createTest("login page");
		test.log(Status.PASS, "Login page is gettin displayed");
		test.pass("login is passed");
		
		driver.findElement(By.xpath("//input[@type='text'][@name='email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@type='password'][@name='password']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@type='password'][@name='password']")).submit();
		
}

@DataProvider(name = "Supplier")
public Object[][] datasupplier() throws FileNotFoundException
{
	String path = System.getProperty("user.dir")+"\\Data\\testngdata.xlsx";
	File newfile = new File(path);
	FileInputStream fis = new FileInputStream(newfile);  //reading data from excel
	XSSFWorkbook workbook = null;
	try {
		workbook = new XSSFWorkbook(fis);
		
		ExtentTest test = extent.createTest("workbook page");
		test.log(Status.FAIL, " password is not matching");
		test.fail("email is not a valid on");
		
		//AssertJUnit.assertEquals("Warning: No match for E-Mail Address and/or Password.", "Warning: No match for E-Mail Address and/or Password.");
		//AssertJUnit.fail("email is not a valid one");
	
		
	} catch (IOException e) {
		
	}
	XSSFSheet sheetname = workbook.getSheet("login");
	int rows = sheetname.getLastRowNum();	
	int columns = sheetname.getRow(1).getLastCellNum();
	Object[][] data = new Object [rows][columns];
	for(int r=0;r<=rows;r++) {
		 XSSFRow row = sheetname.getRow(r);
		 
		 for(int c=0;c<columns;c++) {
			XSSFCell cell = row.getCell(c);
			
			CellType celltype = cell.getCellType();
			switch(celltype)
			{
			case STRING: 
			data[r][c] = cell.getStringCellValue();
			break;
			case NUMERIC: 
			data[r][c] = cell.getNumericCellValue();
			break;
			}
			
		 }
		}
	return data;
	}

	@AfterMethod
	public void quitbrowser()
	{
	driver.quit();
	extent.flush();
	}
}