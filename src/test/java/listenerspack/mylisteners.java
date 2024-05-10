package listenerspack;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class mylisteners implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Test Started");
	
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test Success");

	}

	@Override
	public void onTestFailure(ITestResult result) {
	String testname = result.getName();
	System.out.println(testname+ "got failed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("Test Started");
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Test ended");
	}


}
