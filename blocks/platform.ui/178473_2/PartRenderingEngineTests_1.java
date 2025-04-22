
package org.eclipse.e4.ui.tests.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.equinox.log.ExtendedLogReaderService;
import org.eclipse.equinox.log.SynchronousLogListener;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogService;

public class LogListenerErrorCollector implements TestRule {

	private SynchronousLogListener listener = this::addEntry;

	private List<Throwable> errors = new ArrayList<Throwable>();

	private ExtendedLogReaderService logReaderService;

	@Override
	public Statement apply(final Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				base.evaluate();
				if (logReaderService != null) {
					logReaderService.removeLogListener(listener);
				}
				verify();
			}
		};
	}

	protected void verify() throws Throwable {
		MultipleFailureException.assertEmpty(errors);
	}

	public void installListener(ExtendedLogReaderService logReaderService) {
		this.logReaderService = logReaderService;
		logReaderService.addLogListener(listener, (b, n, l) -> l <= LogService.LOG_ERROR);
	}

	public void addEntry(LogEntry entry) {
		Throwable error = entry.getException();
		if (error == null) {
			errors.add(new AssertionError(entry.getMessage()));
		} else if (error instanceof AssumptionViolatedException) {
			AssertionError e = new AssertionError(error.getMessage());
			e.initCause(error);
			errors.add(e);
		} else {
			errors.add(error);
		}
	}

	public void checkLastLogged(Class<? extends Throwable> expectedThrowable) {
		Throwable lastThrown = errors.isEmpty() ? null : errors.get(errors.size() - 1);

		if (expectedThrowable.isInstance(lastThrown)) {
			errors.remove(errors.size() - 1);
		} else {
			assertNotNull("No exception logged", lastThrown);
			assertEquals("Unexpected exception logged", expectedThrowable, lastThrown.getClass());
		}
	}

}
