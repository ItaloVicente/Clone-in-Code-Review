package org.eclipse.ui.tests.api;

import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPageService;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.tests.harness.util.EmptyPerspective;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class IPageServiceTest extends UITestCase implements IPageListener,
        org.eclipse.ui.IPerspectiveListener {
    private IWorkbenchWindow fWindow;

    private boolean pageEventReceived;

    private boolean perspEventReceived;

    public IPageServiceTest(String testName) {
        super(testName);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        fWindow = openTestWindow();
    }

    public void testLocalPageService() throws Throwable {
		IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
				getPageInput());

		MockViewPart view = (MockViewPart) page.showView(MockViewPart.ID);

		IPageService slaveService = view.getSite().getService(
				IPageService.class);

		assertTrue(fWindow != slaveService);

		perspEventReceived = false;
		slaveService.addPerspectiveListener(this);
		page.resetPerspective();

		assertTrue(perspEventReceived);


		page.hideView(view);
		
		perspEventReceived = false;
		page.resetPerspective();
		
		assertFalse(perspEventReceived);
	}
    
    public void testAddPageListener() throws Throwable {
         
         fWindow.addPageListener(this);
         
         pageEventReceived = false;
         IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);
         page.close();
         assertTrue(pageEventReceived);
         
         fWindow.removePageListener(this);		
    }

    public void XXXtestRemovePageListener() throws Throwable {

        fWindow.addPageListener(this);
        fWindow.removePageListener(this);

        pageEventReceived = false;
        IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID, getPageInput());
        page.close();
        assertTrue(!pageEventReceived);
    }

    public void testGetActivePage() throws Throwable {
         
         IWorkbenchPage page1 = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);
         assertEquals(fWindow.getActivePage(), page1);
         
         IWorkbenchPage page2 = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);
         assertEquals(fWindow.getActivePage(), page2);
         
         fWindow.setActivePage(page1);
         assertEquals(fWindow.getActivePage(), page1);
         fWindow.setActivePage(page2);
         assertEquals(fWindow.getActivePage(), page2);
         
         page1.close();
         page2.close();
    }

    public void testAddPerspectiveListener() throws Throwable {
         
         fWindow.addPerspectiveListener(this);
         
         perspEventReceived = false;
         IWorkbenchPage page = fWindow.openPage(IWorkbenchConstants.DEFAULT_LAYOUT_ID,
         fWorkspace);
         page.setEditorAreaVisible(false);
         page.setEditorAreaVisible(true);
         page.close();
         assertTrue(perspEventReceived);
         
         fWindow.removePerspectiveListener(this);		
    }

    public void XXXtestRemovePerspectiveListener() throws Throwable {

        fWindow.addPerspectiveListener(this);
        fWindow.removePerspectiveListener(this);

        perspEventReceived = false;
        IWorkbenchPage page = fWindow.openPage(IDE.RESOURCE_PERSPECTIVE_ID, getPageInput());
        page.setEditorAreaVisible(false);
        page.setEditorAreaVisible(true);
        page.close();
        assertTrue(!perspEventReceived);
    }

    @Override
	public void pageActivated(IWorkbenchPage page) {
        pageEventReceived = true;
    }

    @Override
	public void pageClosed(IWorkbenchPage page) {
        pageEventReceived = true;
    }

    @Override
	public void pageOpened(IWorkbenchPage page) {
        pageEventReceived = true;
    }

    @Override
	public void perspectiveActivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective) {
        perspEventReceived = true;
    }

    @Override
	public void perspectiveChanged(IWorkbenchPage page,
            IPerspectiveDescriptor perspective, String changeId) {
        perspEventReceived = true;
    }

}
