
package org.eclipse.jface.tests.performance;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.tests.performance.TestRunnable;

public class ComboViewerRefreshTest extends ViewerTest {

	ComboViewer viewer;

	private RefreshTestContentProvider contentProvider;

	private static int ELEMENT_COUNT;

	public ComboViewerRefreshTest(String testName, int tagging) {
		super(testName, tagging);

	}

	public ComboViewerRefreshTest(String testName) {
		super(testName);

	}

	protected StructuredViewer createViewer(Shell shell) {

		viewer = new ComboViewer(shell);
		contentProvider = new RefreshTestContentProvider(ELEMENT_COUNT);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(getLabelProvider());
		return viewer;
	}

	public void testRefresh() throws Throwable {
		ELEMENT_COUNT = 1000;
		openBrowser();

		exercise(new TestRunnable() {
			public void run() {
				startMeasuring();
				viewer.refresh();
				processEvents();
				stopMeasuring();
			}
		}, MIN_ITERATIONS, ITERATIONS, JFacePerformanceSuite.MAX_TIME);

		commitMeasurements();
		assertPerformance();
	}

	public void testRefreshSmall() throws Throwable {
		ELEMENT_COUNT = 50;
		openBrowser();

		exercise(new TestRunnable() {
			public void run() {
				startMeasuring();
				for (int i = 0; i < 1000; i++) {
					viewer.refresh();
				}
				processEvents();
				stopMeasuring();
			}
		}, MIN_ITERATIONS, slowGTKIterations(), JFacePerformanceSuite.MAX_TIME);

		commitMeasurements();
		assertPerformance();
	}

}
