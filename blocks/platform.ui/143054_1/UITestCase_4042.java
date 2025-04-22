package org.eclipse.ui.tests.harness.util;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public final class TestRunLogUtil {

	private static final String LINE_BREAK = System.getProperty("line.separator"); //$NON-NLS-1$

	public static TestWatcher LOG_TESTRUN = new TestWatcher() {
		@Override
		protected void starting(Description description) {
			System.out.println(formatTestStartMessage(description.getMethodName()));
		}

		@Override
		protected void finished(Description description) {
			System.out.println(formatTestFinishedMessage(description.getMethodName()));
		}
	};

	public static String formatTestStartMessage(String testName) {
		return "----- " + testName + LINE_BREAK + testName + ": setUp..."; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static String formatTestFinishedMessage(String testName) {
		return testName + ": tearDown...\n"; //$NON-NLS-1$
	}

	private TestRunLogUtil() {
	}
}
