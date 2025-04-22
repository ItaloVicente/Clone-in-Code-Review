package org.eclipse.jface.tests.performance;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.tests.performance.TestRunnable;

public class ShrinkingTreeTest extends TreeTest {


	public ShrinkingTreeTest(String testName) {
		super(testName);
	}

	public ShrinkingTreeTest(String testName, int tagging) {
		super(testName, tagging);
	}

	public void testTreeViewerRefresh() throws CoreException {
		
		tagIfNecessary("JFace - Refresh from 1000 items to 100 items",
				Dimension.ELAPSED_PROCESS);
		
		openBrowser();
				testRefresh(100, 1000);
	}

	private void testRefresh(final int smallSize, final int largeSize)
			throws CoreException {

		exercise(new TestRunnable() {
			public void run() {

				TestTreeElement input = new TestTreeElement(0, null);
				viewer.setInput(input);
				input.createChildren(largeSize);

				processEvents();
				viewer.refresh();
				viewer.expandAll();
				input.createChildren(smallSize);
				startMeasuring();
				viewer.refresh();

				stopMeasuring();

			}
		}, MIN_ITERATIONS, ITERATIONS, JFacePerformanceSuite.MAX_TIME);

		commitMeasurements();
		assertPerformance();

	}

	

}
