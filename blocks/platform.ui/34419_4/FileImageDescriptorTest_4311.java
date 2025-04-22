package org.eclipse.jface.tests.performance;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.tests.performance.TestRunnable;

public class FastTreeTest extends TreeAddTest {


	public FastTreeTest(String testName, int tagging) {
		super(testName, tagging);
	}

	public FastTreeTest(String testName) {
		super(testName);
	}
	
	public void testAddTenTenTimes() throws CoreException {

		doTestAdd(10, TEST_COUNT, false);
	}

	
	public void testAddFiftyTenTimes() throws CoreException {

		doTestAdd(50, TEST_COUNT, false);
	}

	public void testAddHundredTenTimes() throws CoreException {

		tagIfNecessary("JFace - Add 10000 items 100 at a time TreeViewer 10 times",
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
				for (int i = 0; i < 10; i++) {
					viewer.remove(input.children);
					for (int k = 0; k < batchArray.length; k++) {
						viewer.add(input, (Object[]) batchArray[k]);
						processEvents();
					}
				}
				

				stopMeasuring();

			}
		}, MIN_ITERATIONS, ITERATIONS, JFacePerformanceSuite.MAX_TIME);

		commitMeasurements();
		assertPerformance();

	}
}
