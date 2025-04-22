package org.eclipse.egit.ui.test;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.Test.None;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.eclipse.swtbot.swt.finder.junit.ScreenshotCaptureListener;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class CapturingSWTBotJunit4Runner extends BlockJUnit4ClassRunner {

	public CapturingSWTBotJunit4Runner(Class<?> testClass)
			throws InitializationError {
		super(testClass);
	}

	@Override
	protected Statement methodBlock(FrameworkMethod method) {
		return super.methodBlock(
				new CapturingFrameworkMethod(method.getMethod()));
	}

	private static class CapturingFrameworkMethod extends FrameworkMethod {

		private static final String ASSUMPTION_VIOLATED_EXCEPTION_NAME = "AssumptionViolatedException";

		private final Class<? extends Throwable> expectedException;

		public CapturingFrameworkMethod(Method method) {
			super(method);
			Test annotation = method.getAnnotation(Test.class);
			expectedException = getExpectedException(annotation);
		}

		@Override
		public Object invokeExplosively(Object target, Object... params)
				throws Throwable {
			Object result = null;
			try {
				result = super.invokeExplosively(target, params);
			} catch (Throwable e) {
				if (!ASSUMPTION_VIOLATED_EXCEPTION_NAME
						.equals(e.getClass().getSimpleName())) {
					if (expectedException == null || !expectedException
							.isAssignableFrom(e.getClass())) {
						createScreenshot(e); // Unexpected exception
					}
				}
				throw e;
			}
			if (expectedException != null) {
				createScreenshot(null); // No exception, but we expected one
			}
			return result;
		}

		private Class<? extends Throwable> getExpectedException(
				Test annotation) {
			if (annotation == null || annotation.expected() == None.class) {
				return null;
			} else {
				return annotation.expected();
			}
		}

		private void createScreenshot(Throwable t) {
			Description testDescription = Description.createTestDescription(
					getDeclaringClass(), getMethod().getName());
			Failure failure = new Failure(testDescription, t);
			ScreenshotCaptureListener screenshotCreator = new ScreenshotCaptureListener();
			try {
				screenshotCreator.testFailure(failure);
			} catch (Exception e) {
			}
		}
	}
}
