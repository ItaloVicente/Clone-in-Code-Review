package org.eclipse.ui.tests.autotests;

import org.eclipse.ui.tests.harness.util.UITestCase;

public abstract class UITestCaseWithResult extends UITestCase implements AutoTest {
    private AbstractTestLogger resultLog;
    
    public UITestCaseWithResult(String testName, AbstractTestLogger log) {
        super(testName);
        this.resultLog = log;
    }
    
    @Override
	protected final void runTest() throws Throwable {
        String testName = getName();
        
        TestResult result;
        try {
            result = new TestResult(performTest());
        } catch (Throwable t) {
            result = new TestResult(t);
        }
        
        resultLog.reportResult(testName, result);
    }
    
}
