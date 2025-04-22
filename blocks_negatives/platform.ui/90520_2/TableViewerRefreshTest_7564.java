/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.tests.performance;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.tests.performance.TestRunnable;

/**
 * ShrinkingTreeTest is a test to see how long it takes to refresh a tree that goes
 * from a large item count to a smaller one.
 * @since 3.3
 *
 */
public class ShrinkingTreeTest extends TreeTest {


	/**
	 * Create a new instance of the receiver.
	 *
	 * @param testName
	 */
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

	/**
	 * Run the test for one of the fast insertions.
	 *
	 * @param count
	 * @throws CoreException
	 */
	private void testRefresh(final int smallSize, final int largeSize)
			throws CoreException {

		exercise(new TestRunnable() {
			@Override
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
