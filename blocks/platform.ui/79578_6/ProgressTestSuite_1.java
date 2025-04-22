package org.eclipse.ui.tests.progress;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.ProgressMonitorUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.junit.Test;

public class AccumulatingProgressMonitorTest extends UITestCase {
	public AccumulatingProgressMonitorTest(String testName) {
		super(testName);
	}

	private class UIThreadAsserterMonitor implements IProgressMonitorWithBlocking {
		public boolean beginTaskCalled = false;
		public boolean setTaskNameCalled = false;
		public boolean subTaskCalled = false;
		public boolean setBlockedCalled = false;
		public boolean clearBlockedCalled = false;
		public boolean workedCalled = false;
		public boolean internalWorkedCalled = false;
		public boolean doneCalled = false;
		public boolean isCanceledCalled = false;
		public boolean setCanceledCalled = false;

		@Override
		public void beginTask(String name, int totalWork) {
			beginTaskCalled = true;
			assertTrue(Display.getDefault().getThread() == Thread.currentThread());
		}

		@Override
		public void done() {
			doneCalled = true;
			assertTrue(Display.getDefault().getThread() == Thread.currentThread());
		}

		@Override
		public void internalWorked(double work) {
			internalWorkedCalled = true;
			assertTrue(Display.getDefault().getThread() == Thread.currentThread());
		}

		@Override
		public boolean isCanceled() {
			isCanceledCalled = true;
			assertFalse(Display.getDefault().getThread() == Thread.currentThread());
			return false;
		}

		@Override
		public void setCanceled(boolean value) {
			setCanceledCalled = true;
			assertFalse(Display.getDefault().getThread() == Thread.currentThread());
		}

		@Override
		public void setTaskName(String name) {
			setTaskNameCalled = true;
			assertTrue(Display.getDefault().getThread() == Thread.currentThread());
		}

		@Override
		public void subTask(String name) {
			subTaskCalled = true;
			assertTrue(Display.getDefault().getThread() == Thread.currentThread());
		}

		@Override
		public void worked(int work) {
			workedCalled = true;
			assertTrue(Display.getDefault().getThread() == Thread.currentThread());
		}

		@Override
		public void setBlocked(IStatus reason) {
			setBlockedCalled = true;
			assertTrue(Display.getDefault().getThread() == Thread.currentThread());
		}

		@Override
		public void clearBlocked() {
			clearBlockedCalled = true;
			assertTrue(Display.getDefault().getThread() == Thread.currentThread());
		}
	}

	private class CollectorAsserterMonitor implements IProgressMonitorWithBlocking {
		ArrayList<String> receivedTaskNames = new ArrayList<String>();

		public ArrayList<String> getTaskNames() {
			return receivedTaskNames;
		}

		@Override
		public void beginTask(String name, int totalWork) {
		}

		@Override
		public void done() {
		}

		@Override
		public void internalWorked(double work) {
		}

		@Override
		public boolean isCanceled() {
			return false;
		}

		@Override
		public void setCanceled(boolean value) {
		}

		@Override
		public void setTaskName(String name) {
			receivedTaskNames.add(name);
		}

		@Override
		public void subTask(String name) {
		}

		@Override
		public void worked(int work) {
		}

		@Override
		public void setBlocked(IStatus reason) {
		}

		@Override
		public void clearBlocked() {
		}
	}


	@Test
	public void testAccumulatingMonitorInUIThread() {
		final Throwable[] death = new Throwable[1];
		final UIThreadAsserterMonitor[] mon2 = new UIThreadAsserterMonitor[1];
		TestThread t = new TestThread("Test Accumulating Monitor") {
			@Override
			public void run() {
				try {
					UIThreadAsserterMonitor tm = new UIThreadAsserterMonitor();
					mon2[0] = tm;
					IProgressMonitorWithBlocking wrapper = ProgressMonitorUtil.createAccumulatingProgressMonitor(tm,
							Display.getDefault());
					wrapper.beginTask("Some Task", 100);
					wrapper.setTaskName("Task Name");
					wrapper.subTask("Subtask");
					wrapper.worked(10);
					wrapper.isCanceled();
					wrapper.setCanceled(false);
					wrapper.setBlocked(new Status(IStatus.ERROR, "org.eclipse.ui.tests", "Some Error"));
					try {
						Thread.sleep(200);
					} catch (InterruptedException ie) {
					}
					wrapper.clearBlocked();
					wrapper.done();
				} catch (Throwable t) {
					death[0] = t;
				}
			}
		};
		t.start();
		runEventLoop(3000, t);
		assertFalse(t.isCanceled());
		assertNull("Wrapped monitor not executed in UI thread", death[0]);
		assertNotNull(mon2[0]);
		assertTrue(mon2[0].beginTaskCalled);
		assertTrue(mon2[0].setTaskNameCalled);
		assertTrue(mon2[0].subTaskCalled);
		assertTrue(mon2[0].setBlockedCalled);
		assertTrue(mon2[0].clearBlockedCalled);
		assertTrue(mon2[0].doneCalled);
		assertTrue(mon2[0].isCanceledCalled);
		assertTrue(mon2[0].setCanceledCalled);

		assertFalse(mon2[0].workedCalled);
		assertTrue(mon2[0].internalWorkedCalled);
	}


	@Test
	public void testCollector() {
		final CollectorAsserterMonitor[] mon = new CollectorAsserterMonitor[1];
		final int[] numLoops = new int[] { 10000 };
		TestThread t = new TestThread("Test Accumulating Monitor") {
			@Override
			public void run() {
				mon[0] = new CollectorAsserterMonitor();
				IProgressMonitorWithBlocking wrapper = ProgressMonitorUtil.createAccumulatingProgressMonitor(mon[0],
						Display.getDefault());
				wrapper.beginTask("Some Task", 100);
				for (int i = 0; i < numLoops[0]; i++) {
					wrapper.setTaskName("Task Name " + i);
				}
				wrapper.done();
			}
		};
		t.start();
		runEventLoop(4000, t);
		assertNotNull(mon[0]);
		ArrayList<String> tasks = mon[0].getTaskNames();
		int size = tasks.size();
		assertFalse("No events were fired in the UI thread. No events reached our intended monitor.", size == 0);
		assertTrue("Events received should be less than 20% of events fired: expected=" + (numLoops[0] / 5)
				+ ", actual=" + size, size < (numLoops[0] / 5));
		assertFalse(t.isCanceled());
	}

	private void runEventLoop(int ms, TestThread t) {
		long time = System.currentTimeMillis();
		long end = time + ms;
		Display display = Display.getCurrent();
		while (t.isAlive() && System.currentTimeMillis() < end && !t.isCanceled()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		if (t.isAlive()) {
			t.setCanceled(true);
		}
	}

	private abstract class TestThread extends Thread {
		boolean canceled;

		public TestThread(String name) {
			super(name);
		}

		synchronized boolean isCanceled() {
			return canceled;
		}

		synchronized void setCanceled(boolean b) {
			canceled = b;
		}

		@Override
		abstract public void run();
	}

}
