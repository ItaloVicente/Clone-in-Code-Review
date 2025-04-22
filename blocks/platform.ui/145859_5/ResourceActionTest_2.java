package org.eclipse.ui.tests.internal;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.operations.AdvancedValidationUserApprover;

import junit.framework.TestCase;

public abstract class ResourceActionTest extends TestCase {

	public ResourceActionTest() {
		super();
	}

	public ResourceActionTest(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		AdvancedValidationUserApprover.AUTOMATED_MODE = true;
	}

	@Override
	protected void tearDown() throws Exception {
		AdvancedValidationUserApprover.AUTOMATED_MODE = false;
		super.tearDown();
	}

	protected static void joinDeleteResourceActionJobs() {
		boolean joined = false;
		while (!joined) {
			try {
				Job.getJobManager().join(IDEWorkbenchMessages.DeleteResourceAction_jobName, null);
				joined = true;
			} catch (InterruptedException ex) {
				processUIEvents();
			}
		}

		joined = false;
		while (!joined) {
			try {
				Job.getJobManager().join(IDEWorkbenchMessages.DeleteResourceAction_jobName, null);
				joined = true;
			} catch (InterruptedException ex) {
				processUIEvents();
			}
		}
	}

	protected static class TestDeleteResourceAction extends DeleteResourceAction {

		public boolean fRan = false;

		public TestDeleteResourceAction(IShellProvider provider) {
			super(provider);
			fTestingMode = true;
		}

		@Override
		public void run() {
			super.run();
			fRan = true;
		}

		public boolean didRun() {
			return fRan;
		}
	}

	protected static void processUIEvents() {
		Display display = Display.getCurrent();
		while (display.readAndDispatch()) {
		}
	}

}
