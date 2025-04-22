package org.eclipse.ui.tests.performance.layout;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.tests.performance.BasicPerformanceTest;

public class ResizeTest extends BasicPerformanceTest {

	private TestWidgetFactory widgetFactory;

	private int xIterations = 5;

	private int yIterations = 5;

	private String tagString;

	
	
	public ResizeTest(TestWidgetFactory factory) {
		this(factory, NONE, factory.getName() + " setSize");
	}

	
	
	public ResizeTest(TestWidgetFactory factory, int tagging,
			String tag) {
		super(factory.getName() + " setSize", tagging);
		this.tagString = tag;
		this.widgetFactory = factory;
	}

	protected void runTest() throws CoreException, WorkbenchException {

		tagIfNecessary(tagString, Dimension.ELAPSED_PROCESS);

		widgetFactory.init();
		final Composite widget = widgetFactory.getControl();
		Rectangle initialBounds = widget.getBounds();
		final Point maxSize = widgetFactory.getMaxSize();

		waitForBackgroundJobs();
		processEvents();
		for (int j = 0; j < 50; j++) {

			startMeasuring();
			for (int i = 0; i < 2; i++) {

				for (int xIteration = 0; xIteration < xIterations; xIteration += 5) {

					for (int yIteration = 0; yIteration < yIterations; yIteration++) {
						int xSize = maxSize.x
								/ xIterations;
						int ySize = maxSize.y * yIteration / yIterations;

						widget.setSize(xSize, ySize);

						processEvents();
					}

				}

			}
			stopMeasuring();
		}
		commitMeasurements();
		assertPerformance();

		widget.setBounds(initialBounds);
		widgetFactory.done();
	}

}
