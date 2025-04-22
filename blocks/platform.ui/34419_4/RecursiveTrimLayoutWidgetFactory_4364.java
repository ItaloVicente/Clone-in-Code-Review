package org.eclipse.ui.tests.performance.layout;

import junit.framework.Assert;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class PerspectiveWidgetFactory extends TestWidgetFactory {

    private String perspectiveId;
    private IWorkbenchWindow window;
    
    public PerspectiveWidgetFactory(String initialPerspective) {
        perspectiveId = initialPerspective;
    }
    
    public Point getMaxSize() {
        return new Point(1024, 768);
    }

    public void init() throws WorkbenchException {
        window = PlatformUI.getWorkbench().openWorkbenchWindow(perspectiveId, UITestCase.getPageInput());
		IWorkbenchPage page = window.getActivePage();
        Assert.assertNotNull(page);
    }
    
    public String getName() {
        return "Perspective " + perspectiveId;
    }

    public Composite getControl() {
        return window.getShell();
    }

    public void done() throws CoreException, WorkbenchException {
    	window.close();
    	super.done();
    }
}
