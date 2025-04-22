package org.eclipse.jface.tests.performance;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.tests.performance.TestRunnable;

public class TreeAddTest extends TreeTest {

	static int TEST_COUNT = 1000;

	public TreeAddTest(String testName, int tagging) {
		super(testName, tagging);
	}

	public TreeAddTest(String testName) {
		super(testName);
	}

	public void testAddOneAtATime() {
		openBrowser();

		for (int i = 0; i < ITERATIONS / 10; i++) {
			TestTreeElement input = new TestTreeElement(0, null);
			viewer.setInput(input);
			input.createChildren(TEST_COUNT);
			processEvents();
			startMeasuring();
			for (int j = 0; j < input.children.length; j++) {

				viewer.add(input, input.children[j]);
				processEvents();

			}
			stopMeasuring();
		}

		commitMeasurements();
		assertPerformance();
	}

	public void testAddTen() throws CoreException {

		doTestAdd(10, TEST_COUNT, false);
	}

	public void testAddFifty() throws CoreException {

		doTestAdd(50, TEST_COUNT, false);
	}

	public void testAddHundred() throws CoreException {

		tagIfNecessary("JFace - Add 1000 items in 10 blocks to TreeViewer",
				Dimension.ELAPSED_PROCESS);

		doTestAdd(100, TEST_COUNT, false);
	}

	protected void doTestAdd(final int increment, final int total,final boolean preSort)
			throws CoreException {

		openBrowser();

		exercise(new TestRunnable() {
			public void run() {

				TestTreeElement input = new TestTreeElement(0, null);
				viewer.setInput(input);
				input.createChildren(total);
				if (preSort)
					viewer.getSorter().sort(viewer, input.children);
				Collection batches = new ArrayList();
				int blocks = input.children.length / increment;
				for (int j = 0; j < blocks; j = j + increment) {
					Object[] batch = new Object[increment];
					System.arraycopy(input.children, j * increment, batch, 0,
							increment);
					batches.add(batch);
				}
				processEvents();
				Object[] batchArray = batches.toArray();
				startMeasuring();

				for (int k = 0; k < batchArray.length; k++) {
					viewer.add(input, (Object[]) batchArray[k]);
					processEvents();
				}

				stopMeasuring();

			}
		}, MIN_ITERATIONS, ITERATIONS, JFacePerformanceSuite.MAX_TIME);

		commitMeasurements();
		assertPerformance();

	}

	public void testAddThousand() throws CoreException {
		doTestAdd(1000, 2000, false);
	}

	public void testAddTwoThousand() throws CoreException {

		doTestAdd(2000, 4000, false);

	}

	public void testAddHundredPreSort() throws CoreException {

		doTestAdd(100, 1000, true);
	}

	public void testAddThousandPreSort() throws CoreException {
		tagIfNecessary("JFace - Add 2000 items in 2 blocks to TreeViewer",
				Dimension.ELAPSED_PROCESS);

		doTestAdd(1000, 2000, true);
	}

}
