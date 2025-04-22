package org.eclipse.ui.tests.performance;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class ProgressReportingTest extends BasicPerformanceTest {
	public static final int ITERATIONS = 10000000;

	public static final int MAX_RUNTIME = 4000;
	private volatile boolean isDone;
	private Display display;

	public ProgressReportingTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		this.display = Display.getCurrent();
		super.doSetUp();
	}

	public void runAsyncTest(Runnable testContent) throws Exception {
		final Display display = Display.getCurrent();
		tagIfNecessary(getName(), Dimension.ELAPSED_PROCESS);
		exercise(new TestRunnable() {
			@Override
			public void run() throws Exception {
				startMeasuring();

				isDone = false;
				testContent.run();

				for (; !isDone;) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}

				stopMeasuring();
			}
		}, 3, 100, MAX_RUNTIME);

		commitMeasurements();
		assertPerformance();
	}

	public void endAsyncTest(Object ignored) {
		isDone = true;
		display.asyncExec(() -> {
		});
	}

	public void testJobNoMonitorUsage() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			Job.create("Test Job", monitor -> {
				int i = 0;
				long result = 0;
				while (i < ITERATIONS) {
					result += i;
					i++;
				}

				endAsyncTest(result);
			}).schedule();
		});
	}

	public void testJobSetTaskName() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			Job.create("Test Job", monitor -> {
				monitor.beginTask("Test Job", ITERATIONS);
				int i = 0;
				long result = 0;
				while(i < ITERATIONS) {
					monitor.setTaskName(Integer.toString(i));
					result += i;
					i++;
				}

				endAsyncTest(result);
			}).schedule();
		});
	}

	public void testJobSubTask() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			Job.create("Test Job", monitor -> {
				monitor.beginTask("Test Job", ITERATIONS);
				int i = 0;
				long result = 0;
				while (i < ITERATIONS) {
					monitor.subTask(Integer.toString(i));
					result += i;
					i++;
				}

				endAsyncTest(result);
			}).schedule();
		});
	}

	public void testJobIsCanceled() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			Job.create("Test Job", monitor -> {
				monitor.beginTask("Test Job", ITERATIONS);
				int i = 0;
				long result = 0;
				while (i < ITERATIONS) {
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
					result += i;
					i++;
				}

				endAsyncTest(result);
			}).schedule();
		});
	}

	public void testJobWorked() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			Job.create("Test Job", monitor -> {
				monitor.beginTask("Test Job", ITERATIONS);
				int i = 0;
				long result = 0;
				while (i < ITERATIONS) {
					monitor.worked(1);
					result += i;
					i++;
				}

				endAsyncTest(result);
			}).schedule();
		});
	}

	public void testJobSubMonitorSplit() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			Job.create("Test Job", monitor -> {
				SubMonitor subMonitor = SubMonitor.convert(monitor, ITERATIONS);
				int i = 0;
				long result = 0;
				while (i < ITERATIONS) {
					subMonitor.split(1);
					result += i;
					i++;
				}

				endAsyncTest(result);
			}).schedule();
		});
	}

	public void testJobSubMonitorWorked() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			Job.create("Test Job", monitor -> {
				SubMonitor subMonitor = SubMonitor.convert(monitor, ITERATIONS);
				int i = 0;
				long result = 0;
				while (i < ITERATIONS) {
					subMonitor.worked(1);
					result += i;
					i++;
				}

				endAsyncTest(result);
			}).schedule();
		});
	}

	public void testProgressServiceNoMonitorUsage() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			Job j = Job.create("Test Job", monitor -> {
				monitor.beginTask("Test Job", ITERATIONS);
				int i = 0;
				long result = 0;
				while (i < ITERATIONS) {
					result += i;
					i++;
				}

				endAsyncTest(result);
			});
			j.schedule();
			PlatformUI.getWorkbench().getProgressService().showInDialog(window.getShell(), j);
		});
	}

	public void testProgressServiceWorked() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			Job j = Job.create("Test Job", monitor -> {
				monitor.beginTask("Test Job", ITERATIONS);
				int i = 0;
				long result = 0;
				while (i < ITERATIONS) {
					monitor.worked(1);
					result += i;
					i++;
				}

				endAsyncTest(result);
			});
			j.schedule();
			PlatformUI.getWorkbench().getProgressService().showInDialog(window.getShell(), j);
		});
	}

	public void testProgressServiceSetTaskName() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			Job j = Job.create("Test Job", monitor -> {
				monitor.beginTask("Test Job", ITERATIONS);
				int i = 0;
				long result = 0;
				while (i < ITERATIONS) {
					monitor.setTaskName(Integer.toString(i));
					result += i;
					i++;
				}

				endAsyncTest(result);
			});
			j.schedule();
			PlatformUI.getWorkbench().getProgressService().showInDialog(window.getShell(), j);
		});
	}

	public void testProgressServiceSubTask() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			Job j = Job.create("Test Job", monitor -> {
				monitor.beginTask("Test Job", ITERATIONS);
				int i = 0;
				long result = 0;
				while (i < ITERATIONS) {
					monitor.subTask(Integer.toString(i));
					result += i;
					i++;
				}

				endAsyncTest(result);
			});
			j.schedule();
			PlatformUI.getWorkbench().getProgressService().showInDialog(window.getShell(), j);
		});
	}

	public void testProgressServiceIsCanceled() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			Job j = Job.create("Test Job", monitor -> {
				monitor.beginTask("Test Job", ITERATIONS);
				int i = 0;
				long result = 0;
				while (i < ITERATIONS) {
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
					result += i;
					i++;
				}

				endAsyncTest(result);
			});
			j.schedule();
			PlatformUI.getWorkbench().getProgressService().showInDialog(window.getShell(), j);
		});
	}

	public void testProgressMonitorDialogNoMonitorUsage() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			try {
				new ProgressMonitorDialog(window.getShell()).run(true, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) {
						monitor.beginTask("Test Job", ITERATIONS);
						int i = 0;
						long result = 0;
						while (i < ITERATIONS) {
							result += i;
							i++;
						}

						endAsyncTest(result);
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void testProgressMonitorDialogWorked() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			try {
				new ProgressMonitorDialog(window.getShell()).run(true, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) {
						monitor.beginTask("Test Job", ITERATIONS);
						int i = 0;
						long result = 0;
						while (i < ITERATIONS) {
							monitor.worked(1);
							result += i;
							i++;
						}

						endAsyncTest(result);
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void testProgressMonitorDialogIsCanceled() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			try {
				new ProgressMonitorDialog(window.getShell()).run(true, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) {
						monitor.beginTask("Test Job", ITERATIONS);
						int i = 0;
						long result = 0;
						while (i < ITERATIONS) {
							if (monitor.isCanceled()) {
								throw new OperationCanceledException();
							}
							result += i;
							i++;
						}

						endAsyncTest(result);
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void testProgressMonitorDialogSetTaskName() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			try {
				new ProgressMonitorDialog(window.getShell()).run(true, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) {
						monitor.beginTask("Test Job", ITERATIONS);
						int i = 0;
						long result = 0;
						while (i < ITERATIONS) {
							monitor.setTaskName(Integer.toString(i));
							result += i;
							i++;
						}

						endAsyncTest(result);
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public void testProgressMonitorDialogSubTask() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		runAsyncTest(() -> {
			try {
				new ProgressMonitorDialog(window.getShell()).run(true, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) {
						monitor.beginTask("Test Job", ITERATIONS);
						int i = 0;
						long result = 0;
						while (i < ITERATIONS) {
							monitor.subTask(Integer.toString(i));
							result += i;
							i++;
						}

						endAsyncTest(result);
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
	}

}
