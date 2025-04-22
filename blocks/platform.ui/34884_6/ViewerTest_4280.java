
package org.eclipse.jface.tests.performance;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;


public class TreeViewerRefreshTest extends ViewerTest {
    
    TreeViewer viewer;
    private RefreshTestTreeContentProvider contentProvider;

    public TreeViewerRefreshTest(String testName, int tagging) {
        super(testName, tagging);
    }

    public TreeViewerRefreshTest(String testName) {
        super(testName);
    }

    protected StructuredViewer createViewer(Shell shell) {
        viewer = new TreeViewer(shell);
        contentProvider = new RefreshTestTreeContentProvider();
        viewer.setContentProvider(contentProvider);
        viewer.setLabelProvider(getLabelProvider());
        return viewer;
    }

    public void testRefresh() throws Throwable {
        openBrowser();

        for (int i = 0; i < ITERATIONS; i++) {
            startMeasuring();
            viewer.refresh();
            processEvents();
            stopMeasuring();
        }
        
        commitMeasurements();
        assertPerformance();
    }
    

}
