package org.eclipse.ui.tests.performance.layout;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.tests.performance.BasicPerformanceTest;

public class ComputeSizeTest extends BasicPerformanceTest {

    private TestWidgetFactory widgetFactory;
    private int xIterations = 10;
    private int yIterations = 10;
    
    public ComputeSizeTest(TestWidgetFactory widgetFactory) {
        super(widgetFactory.getName() + " computeSize");
        
        this.widgetFactory = widgetFactory;
    }

    protected void runTest() throws CoreException, WorkbenchException {

        widgetFactory.init();
        final Composite widget = widgetFactory.getControl();
        final Point maxSize = widgetFactory.getMaxSize();
        
        final int[] counter = new int[] {0};
 
		for (int j = 0; j < 100; j++) {

			int count = counter[0];

			startMeasuring();
			for (int i = 0; i < 200; i++) {

               for (int xIteration = 0; xIteration < xIterations; xIteration++) {
                   
                   for (int yIteration = 0; yIteration < yIterations; yIteration++) {
                       int xSize = maxSize.x * ((xIteration + yIteration) % xIterations) / xIterations;
                       int ySize = maxSize.y * yIteration / yIterations;

                       boolean flushState = (count % 2) != 0;
                       
                       switch(count % 4) {
                           case 0: widget.computeSize(xSize, SWT.DEFAULT, flushState); break;
                           case 1: widget.computeSize(SWT.DEFAULT, ySize, flushState); break;
                           case 2: widget.computeSize(xSize, ySize, flushState); break;
                           case 3: widget.computeSize(SWT.DEFAULT, SWT.DEFAULT, flushState); break;
                       }

						count++;
					}
				}

			}
			stopMeasuring();
			processEvents();
			counter[0]++;
		}

		commitMeasurements();
		assertPerformance();
        widgetFactory.done();
    }
}
