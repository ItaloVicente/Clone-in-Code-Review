package org.eclipse.ui.tests.autotests;

import java.net.URL;

import org.eclipse.ui.WorkbenchException;

public class AutoTestLogger extends AbstractTestLogger {

    private TestResults errors = new TestResults();
    private TestResults expectedResults;
    private TestResults unknownTests = new TestResults();

    public AutoTestLogger(URL expectedResultsFile) throws WorkbenchException {
        this(new TestResults(XmlUtil.read(expectedResultsFile)));
    }

    public AutoTestLogger(TestResults expectedResults) {
        this.expectedResults = expectedResults;
    }

    public AutoTestLogger() {
        this(new TestResults());
    }

    public void setExpectedResults(TestResults results) {
        this.expectedResults = results;
    }

    public TestResults getErrors() {
        return errors;
    }

    public TestResults getUnknownTests() {
        return unknownTests;
    }

    @Override
	public void reportResult(String testName, TestResult result) throws Throwable {
        TestResultFilter expectedResult = expectedResults.get(testName);

        if (expectedResult == null) {
            unknownTests.put(testName, new TestResultFilter(result));
        } else {
            try {
                expectedResult.assertResult(result);
            } catch (Throwable t) {
                errors.put(testName, new TestResultFilter(result));
                throw t;
            }
        }

    }
}
