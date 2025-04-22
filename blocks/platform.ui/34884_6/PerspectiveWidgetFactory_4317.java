package org.eclipse.ui.tests.performance.layout;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.tests.performance.BasicPerformanceTest;

public class LayoutTest extends BasicPerformanceTest {

	private TestWidgetFactory widgetFactory;

	private int xIterations = 100;

	private int yIterations = 10;

	private boolean flushState;

	public LayoutTest(TestWidgetFactory widgetFactory, boolean flushState) {
		super(widgetFactory.getName() + " layout("
				+ (flushState ? "true" : "false") + ")");

		this.widgetFactory = widgetFactory;
		this.flushState = flushState;
	}

	protected void runTest() throws CoreException, WorkbenchException {

		widgetFactory.init();
		final Composite widget = widgetFactory.getControl();
		final Point maxSize = widgetFactory.getMaxSize();
		Rectangle initialBounds = widget.getBounds();
		final Rectangle newBounds = Geometry.copy(initialBounds);

		for (int xIteration = 0; xIteration < xIterations; xIteration++) {

			processEvents();

			startMeasuring();

			for (int yIteration = 0; yIteration < yIterations; yIteration++) {
				int xSize = maxSize.x
						/ xIterations;
				int ySize = maxSize.y * yIteration / yIterations;

				newBounds.width = xSize;
				newBounds.height = ySize;

				widget.setBounds(newBounds);
				widget.layout(flushState);
			}

			stopMeasuring();
		}

		commitMeasurements();
		assertPerformance();

		widget.setBounds(initialBounds);
		widgetFactory.done();
	}
}

