
package org.eclipse.ui.tests.operations;

import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class MultiThreadedOperationsTests extends UITestCase {

	public MultiThreadedOperationsTests(String name) {
		super(name);
	}

	public void testOperationsAPIinThreads() {
		class OperationsTestJob extends Job {
			public OperationsTestJob() {
				super("Operations Test Job");
			}

			@Override
			public IStatus run(IProgressMonitor monitor) {
				new TestSuite(OperationsAPITest.class).run(new TestResult());
				new TestSuite(WorkbenchOperationHistoryTests.class).run(new TestResult());
				return Status.OK_STATUS;
			}
		}

		OperationsTestJob job1 = new OperationsTestJob();
		OperationsTestJob job2 = new OperationsTestJob();
		OperationsTestJob job3 = new OperationsTestJob();

		job1.schedule();
		job2.schedule();
		job3.schedule();

		try {
			job1.join();
		} catch (InterruptedException e) {
			System.out.println("Job interrupted in test case");
		}
		try {
			job2.join();
		} catch (InterruptedException e) {
			System.out.println("Job interrupted in test case");
		}
		try {
			job3.join();
		} catch (InterruptedException e) {
			System.out.println("Job interrupted in test case");
		}
	}
}
