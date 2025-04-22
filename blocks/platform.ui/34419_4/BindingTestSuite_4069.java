
package org.eclipse.jface.tests.databinding;

import java.util.Locale;

import org.eclipse.core.databinding.util.ILogger;
import org.eclipse.core.databinding.util.Policy;
import org.eclipse.core.runtime.IStatus;

import junit.extensions.TestSetup;
import junit.framework.Test;

public class BindingTestSetup extends TestSetup {

	private Locale oldLocale;
	private ILogger oldLogger;
	private org.eclipse.jface.util.ILogger oldJFaceLogger;

	public BindingTestSetup(Test test) {
		super(test);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		oldLocale = Locale.getDefault();
		Locale.setDefault(Locale.US);
		oldLogger = Policy.getLog();
		Policy.setLog(new ILogger() {
			@Override
			public void log(IStatus status) {
				if (status.getException() != null) {
					throw new RuntimeException(status.getException());
				}
				fail();
			}
		});
		oldJFaceLogger = org.eclipse.jface.util.Policy.getLog();
		org.eclipse.jface.util.Policy.setLog(new org.eclipse.jface.util.ILogger(){
			@Override
			public void log(IStatus status) {
				if (status.getException() != null) {
					throw new RuntimeException(status.getException());
				}
				fail();
			}
		});
	}

	@Override
	protected void tearDown() throws Exception {
		Locale.setDefault(oldLocale);
		Policy.setLog(oldLogger);
		org.eclipse.jface.util.Policy.setLog(oldJFaceLogger);
		super.tearDown();
	}
}
