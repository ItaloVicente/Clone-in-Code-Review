
package org.eclipse.jface.tests.performance;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.tests.performance.TestRunnable;

public class ListViewerRefreshTest extends ViewerTest {

	ListViewer viewer;

	private RefreshTestContentProvider contentProvider;

	public ListViewerRefreshTest(String testName, int tagging) {
		super(testName, tagging);

	}

	public ListViewerRefreshTest(String testName) {
		super(testName);

	}

	protected StructuredViewer createViewer(Shell shell) {
		viewer = new ListViewer(shell);
		contentProvider = new RefreshTestContentProvider(
				RefreshTestContentProvider.ELEMENT_COUNT);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(getLabelProvider());
		return viewer;
	}

	public void testRefresh() throws Throwable {

		tagIfNecessary("JFace - Refresh 100 item ListViewer 10 times",
				Dimension.ELAPSED_PROCESS);

		openBrowser();

		exercise(new TestRunnable() {
			public void run() {
				startMeasuring();
				viewer.refresh();
				processEvents();
				stopMeasuring();
			}
		}, MIN_ITERATIONS, ITERATIONS,
				JFacePerformanceSuite.MAX_TIME);

		commitMeasurements();
		assertPerformance();
	}

}
