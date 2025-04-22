package org.eclipse.ui.tests.performance;

import java.util.Enumeration;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.osgi.framework.BundleContext;

public class FilteredTestSuite extends TestSuite {

	static final public String FILTER_TEST_NAME = "org.eclipse.ui.tests.filter";

    private String filterTestClassName;
    private String filterTestName;

    public FilteredTestSuite() {
    	BundleContext context = UIPerformancePlugin.getDefault().getContext();
    	if (context == null) { // most likely run in a wrong launch mode
    		System.err.println("UIPerformanceTestSuite was unable to retirieve bundle context; test filtering is disabled");
    		return;
    	}
		String filterString = context.getProperty(FILTER_TEST_NAME);
		if (filterString == null)
			return;
		if (filterString.endsWith("()"))
			filterString = filterString.substring(0, filterString.length() - 2);

		int methodSeparator = filterString.indexOf('#');
		if (methodSeparator != -1) {

			if (methodSeparator == 0)
				filterTestClassName = null;
			else
				filterTestClassName = filterString.substring(0, methodSeparator);
			
			if (methodSeparator + 1 < filterString.length())
				filterTestName = filterString.substring(methodSeparator + 1);
			else
				filterTestName = null;
		} else {
			filterTestClassName = filterString;
			filterTestName = null;
		}
    }

    public void addTest(Test test) {
    	if ((filterTestClassName != null) || (filterTestName != null)) {
    		if (test instanceof TestSuite) {
    			addFilteredTestSuite((TestSuite)test);
    			return;
    		} else if (test instanceof TestCase) {
    			addFilteredTestCase((TestCase)test);
    			return;
    		}
    	}
    	super.addTest(test);
    }

    private void addFilteredTestSuite(TestSuite testSuite) {
		for(Enumeration allTests = testSuite.tests(); allTests.hasMoreElements(); ) {
			Object subTest = allTests.nextElement();
			
			if (subTest instanceof TestSuite) {
				addFilteredTestSuite((TestSuite)subTest);
				continue;
			}

			if (!(subTest instanceof TestCase))
				continue;

			if (filterTestClassName != null) {
				Class testClass = subTest.getClass();
				String subTestQualName = testClass.getName(); // qualified class name
				if (subTestQualName == null)
					subTestQualName = "";
				int index = subTestQualName.lastIndexOf('.');
				String subTestName = ""; // short class name
				if ((index != -1) && ((index +1) < subTestQualName.length()))
					subTestName = subTestQualName.substring(index+1);
				if (!subTestName.matches(filterTestClassName) && !subTestQualName.matches(filterTestClassName))
					continue;
			}
			addFilteredTestCase((TestCase)subTest);
		}
    }

    private void addFilteredTestCase(TestCase testCase) {
   		if (filterTestName == null) {
   			super.addTest(testCase);
   			return;
   		}
		String testCaseName = testCase.getName();
   		if (testCaseName == null)
   			return;
   		if (testCaseName.matches(filterTestName))
   			super.addTest(testCase);
    }
}
