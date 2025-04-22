package org.eclipse.ui.tests.performance.layout;

import junit.framework.Assert;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.ViewSite;
import org.eclipse.ui.tests.harness.util.EmptyPerspective;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.eclipse.ui.tests.performance.BasicPerformanceTest;

public class ViewWidgetFactory extends TestWidgetFactory {

    private String viewId;
    private Control ctrl;
    private IWorkbenchWindow window;
    
    public ViewWidgetFactory(String viewId) {
        this.viewId = viewId;
        Assert.assertNotNull(viewId);
    }
    
    public Point getMaxSize() {
        return new Point(1024, 768);
    }

    public static Composite getControl(IViewPart part) {
		ViewSite site = (ViewSite)part.getSite();
		MPart modelPart = site.getModel();
		return (Composite) modelPart.getWidget();
    }
    
    public void init() throws CoreException, WorkbenchException {
        window = PlatformUI.getWorkbench().openWorkbenchWindow(EmptyPerspective.PERSP_ID, UITestCase.getPageInput());
		IWorkbenchPage page = window.getActivePage();
        Assert.assertNotNull(page);

		IViewPart part = page.showView(viewId, null, IWorkbenchPage.VIEW_ACTIVATE);
        
        BasicPerformanceTest.waitForBackgroundJobs();
        
		ctrl = getControl(part);
        
        Point size = getMaxSize();
        ctrl.setBounds(0,0,size.x, size.y);
        window.getShell().setSize(size);
    }
    
    public String getName() {
        return "View " + viewId;
    }

    public Composite getControl() throws CoreException, WorkbenchException {
        return (Composite)ctrl;
    }
    
    public void done() throws CoreException, WorkbenchException {
    	window.close();
    	super.done();
    }
    
}
