
package org.eclipse.ui.tests.performance;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.osgi.framework.BundleContext;

public abstract class BasicPerformanceTest extends UITestCase {

	static final public String INTERACTIVE = "org.eclipse.ui.tests.performance.interactive";

	public static final int NONE = 0;

	public static final int LOCAL = 1;

	public static final int GLOBAL = 2;

	private PerformanceTester tester;

	private IProject testProject;

	final private boolean tagAsGlobalSummary;

	final private boolean tagAsSummary;

	private static long startMeasuringTime;

	private static long stopMeasuringTime;
	
	private static boolean interactive;

	public BasicPerformanceTest(String testName) {
		this(testName, NONE);
		BundleContext context = UIPerformancePlugin.getDefault().getContext();
		if (context == null) { // most likely run in a wrong launch mode
			System.err.println("Unable to retrieve bundle context from BasicPerformanceTest; interactive mode is disabled");
			return;
		}
		String filterString = context.getProperty(INTERACTIVE);
		if (filterString != null && filterString.toLowerCase().equals("true")) {
			interactive = true;
		}
	}

	public BasicPerformanceTest(String testName, int tagging) {
		super(testName);
		tagAsGlobalSummary = ((tagging & GLOBAL) != 0);
		tagAsSummary = ((tagging & LOCAL) != 0);
	}

	private boolean shouldGloballyTag() {
		return tagAsGlobalSummary;
	}

	private boolean shouldLocallyTag() {
		return tagAsSummary;
	}

	protected void doSetUp() throws Exception {
		super.doSetUp();
		if (interactive) {
			return;
		}
		tester = new PerformanceTester(this);
	}

	protected void doTearDown() throws Exception {
		super.doTearDown();
		if (interactive) {
			return;
		}
		tester.dispose();
	}

	protected IProject getProject() {
		if (testProject == null) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			testProject = workspace.getRoot().getProject(
					UIPerformanceTestSetup.PROJECT_NAME);
		}
		return testProject;
	}

	public void assertPerformance() {
		if (interactive) {
			return;
		}
		tester.assertPerformance();
	}

	public void assertPerformanceInRelativeBand(Dimension dim,
			int lowerPercentage, int upperPercentage) {
		if (interactive) {
			return;
		}
		tester.assertPerformanceInRelativeBand(dim, lowerPercentage,
				upperPercentage);
	}

	public void commitMeasurements() {
		if (interactive) {
			return;
		}
		tester.commitMeasurements();
	}

	public void startMeasuring() {
		if (interactive) {
			startMeasuringTime = System.currentTimeMillis();
			return;
		}
		tester.startMeasuring();
	}

	public void stopMeasuring() {
		if (interactive) {
			stopMeasuringTime = System.currentTimeMillis();
			return;
		}
		tester.stopMeasuring();
	}

	private void tagAsGlobalSummary(String shortName, Dimension dimension) {
		System.out.println("GLOBAL " + shortName);
		if (interactive) {
			return;
		}
		tester.tagAsGlobalSummary(shortName, dimension);
	}

	private void tagAsSummary(String shortName, Dimension dimension) {
		System.out.println("LOCAL " + shortName);
		if (interactive) {
			return;
		}
		tester.tagAsSummary(shortName, dimension);
	}

	public void tagIfNecessary(String shortName, Dimension dimension) {
		if (shouldGloballyTag()) {
			tagAsGlobalSummary(shortName, dimension);
		}
		if (shouldLocallyTag()) {
			tagAsSummary(shortName, dimension);
		}
	}

	public static void waitForBackgroundJobs() {

		Job backgroundJob = new Job(
				"This is a test job which sits around being low priority until everything else finishes") {
			protected IStatus run(IProgressMonitor monitor) {
				return Status.OK_STATUS;
			}
		};

		backgroundJob.setPriority(Job.DECORATE);

		boolean hadEvents = true;
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (display != null) {
			while (hadEvents) {
				hadEvents = false;
				backgroundJob.schedule(0);
				try {
					backgroundJob.join();
				} catch (InterruptedException e) {
				}

				while (display.readAndDispatch()) {
					hadEvents = true;
				}
			}
		}
	}

	public static void exercise(TestRunnable runnable) throws CoreException {
		exercise(runnable, 3, 100, 4000);
	}

	public static void exercise(TestRunnable runnable, int minIterations,
			int maxIterations, int maxTime) throws CoreException {
		if (interactive) {
			NumberFormat f = new DecimalFormat("##.000");
			NumberFormat p = new DecimalFormat("#0.0");
			try {
				runnable.run();
				int initialRuns = 3;
				long startTime = System.currentTimeMillis();
				for (int i=0; i<initialRuns; i++) {
					runnable.run();
				}
				long currentTime = System.currentTimeMillis();
				double timePerRun = (currentTime - startTime) / 1000.0 / initialRuns;
				int totalRuns = initialRuns;
				double interval = 10.0; // ten seconds
				long intervalMillis = (long) (1000 * interval);
				double averagePerInterval = interval/timePerRun;
				System.out.println("Time per run (roughly): " + f.format(timePerRun) + " - expecting " + f.format(averagePerInterval) + " runs per 10 seconds.");
				System.err.println("Remember - higher means faster: the following shows number of runs per interval (seconds=" + p.format(interval) + ").");
				while (true) {
					int numOperations = 0;
					long elapsed = 0;
					while (elapsed < intervalMillis) {
						startMeasuringTime = -1;
						stopMeasuringTime = -1;
						startTime = System.currentTimeMillis();
						numOperations++;
						runnable.run();
						currentTime = System.currentTimeMillis();
						if (startMeasuringTime != -1 && stopMeasuringTime != -1) {
							elapsed += stopMeasuringTime - startMeasuringTime;
						} else {
							elapsed += currentTime - startTime;
						}
					}
					timePerRun = elapsed / 1000.0 / numOperations;
					double operationsPerInterval = interval/timePerRun;
					double deviation = (operationsPerInterval - averagePerInterval) / averagePerInterval * 100.0;
					System.out.println(f.format(operationsPerInterval) + " runs/interval    (" + (deviation>=0.0?"+":"-") + p.format(Math.abs(deviation)) + "% relative to avg=" + f.format(averagePerInterval) + ")");
					averagePerInterval = ((averagePerInterval * totalRuns) + (operationsPerInterval * numOperations)) / (totalRuns + numOperations);
					totalRuns += numOperations;
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			return;
		}
		long startTime = System.currentTimeMillis();

		for (int counter = 0; counter < maxIterations; counter++) {

			try {
				runnable.run();
			} catch (Exception e) {
				throw new CoreException(new Status(IStatus.ERROR,
						UIPerformancePlugin.getDefault().getBundle()
								.getSymbolicName(), IStatus.OK,
						"An exception occurred", e));
			}

			long curTime = System.currentTimeMillis();
			if (curTime - startTime > maxTime && counter >= minIterations - 1) {
				break;
			}
		}
	}

	public void setDegradationComment(String string) {
		if (interactive) {
			return;
		}
		tester.setDegradationComment(string);

	}

}
