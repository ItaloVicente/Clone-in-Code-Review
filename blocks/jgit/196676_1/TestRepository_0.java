package org.eclipse.jgit.junit;

import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

public abstract class TestInfoRetriever {

	protected TestInfo testInfo;

	@BeforeEach
	protected void testInfo(TestInfo info) {
		this.testInfo = info;
	}

	protected String getTestMethodName() {
		Optional<Method> testMethod = testInfo.getTestMethod();
		if (testMethod.isPresent()) {
			return testMethod.get().getName();
		}
		return "Test method name unavailable";
	}

}
