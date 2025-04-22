package org.eclipse.ui.tests.autotests;

import junit.framework.TestCase;

public class AutoTestWrapper extends TestCase {
    private AutoTest test;
    private AbstractTestLogger log;

    public AutoTestWrapper(AutoTest test, AbstractTestLogger resultLog) {
        super(test.getName());

        this.test = test;
        this.log = resultLog;
    }

    @Override
	protected void runTest() throws Throwable {
        String testName = test.getName();

        TestResult result;
        try {
            result = new TestResult(test.performTest());
        } catch (Throwable t) {
            result = new TestResult(t);
        }

        log.reportResult(testName, result);
    }
}
