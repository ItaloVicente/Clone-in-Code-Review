package org.eclipse.ui.tests.performance;

import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class OpenCloseViewTest extends BasicPerformanceTest {

    private String viewId;

    public OpenCloseViewTest(String viewId, int tagging) {
        super("showView:" + viewId, tagging);
        this.viewId = viewId;
    }

    protected void runTest() throws Throwable {
        IWorkbenchWindow window = openTestWindow();
        final IWorkbenchPage page = window.getActivePage();
        
        IViewPart view1 = page.showView(viewId);
        page.hideView(view1);
        waitForBackgroundJobs();
        processEvents();
        
       	tagIfNecessary("UI - Open/Close " + view1.getTitle(), Dimension.ELAPSED_PROCESS);
       	if ("org.eclipse.ui.views.BookmarkView".equals(viewId))
       		setDegradationComment("The test results are influenced by the test machine setup. See bug 340136.");
        	
		for (int j = 0; j < 100; j++) {

			startMeasuring();
			for (int i = 0; i < 5; i++) {
				IViewPart view = page.showView(viewId);
				processEvents();

				page.hideView(view);
				processEvents();
			}
			stopMeasuring();
		}

        commitMeasurements();
        assertPerformance();
    }
}
