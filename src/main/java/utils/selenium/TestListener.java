package utils.selenium;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.common.LogUtil;

public class TestListener implements ITestListener {
    @Override
    public void onStart(ITestContext Result) {
    }

    @Override
    public void onTestStart(ITestResult Result) {
        LogUtil.info("");
        LogUtil.info("----------------------------------------------------------------------");
        LogUtil.info("Start test case: " + Result.getMethod().getDescription());
        LogUtil.info("----------------");
    }

    @Override
    public void onTestSuccess(ITestResult Result) {
        LogUtil.info("----------------");
        LogUtil.info("Test case Passed");
    }

    @Override
    public void onTestFailure(ITestResult Result) {
        LogUtil.info("----------------");
        LogUtil.info("Test case Failed");
    }

    @Override
    public void onTestSkipped(ITestResult Result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult Result) {
    }

    @Override
    public void onFinish(ITestContext Result) {
    }
}