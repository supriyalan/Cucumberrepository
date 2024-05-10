package poiapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import extentreports.SimpleExtentReport;


public class ReadDataone extends SimpleExtentReport {
		
	 ExtentReports extent = new ExtentReports();
	 ExtentSparkReporter spark = new ExtentSparkReporter("target/spark.html");
	WebDriver driver;
	
	
	@Test
	public static void readdata() throws IOException
	{
		String path = System.getProperty("user.dir")+"\\Data\\exceltestdata.xlsx";
		File newfile = new File(path);
		FileInputStream fis = new FileInputStream(newfile);  //reading data from excel
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheetname = workbook.getSheet("one");
		int rows = sheetname.getLastRowNum();	
		int columns = sheetname.getRow(1).getLastCellNum();
		
		for(int r=0;r<=rows;r++) {
		 XSSFRow row = sheetname.getRow(r);
		 
		 for(int c=0;c<columns;c++) {
			XSSFCell cell = row.getCell(c);
			
			CellType celltype = cell.getCellType();
			switch(celltype)
			{
			case STRING: System.out.print(cell.getStringCellValue());
			break;
			case NUMERIC: System.out.print(cell.getNumericCellValue());
			break;
			}
			
		 }
		 System.out.println();
		}
}
	
	@Test
	public void logic()
	{
		ExtentTest test = extent.createTest("login page");
		test.log(Status.PASS, "Login page is gettin displayed");
		test.pass("login is passed");
		
	}

	/*@AfterMethod
	public void extentone()
	{
		ExtentReports extent = new ExtentReports();
		File extentfile = new File(System.getProperty("user.dir")+"\\test-output\\ExtentReports\\extentreport.html");
		ExtentSparkReporter spark = new ExtentSparkReporter(extentfile);
		extent.attachReporter(spark);
	}
	*/	

	
	@AfterTest
	public void browserquit()
	{
		extent.flush();
	}
}
