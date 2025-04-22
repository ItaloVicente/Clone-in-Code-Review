package org.eclipse.ui.tests.api;

import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class IPageListenerTest extends UITestCase implements IPageListener {
    private IWorkbenchWindow fWindow;

    private int eventsReceived = 0;

    final private int OPEN = 0x01;

    final private int CLOSE = 0x02;

    final private int ACTIVATE = 0x04;

    private IWorkbenchPage pageMask;

    public IPageListenerTest(String testName) {
        super(testName);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        fWindow = openTestWindow();
        fWindow.addPageListener(this);
    }

    @Override
	protected void doTearDown() throws Exception {
        fWindow.removePageListener(this);
        super.doTearDown();
    }

    public void testPageOpened() throws Throwable {
         
         eventsReceived = 0;
         IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);
         assertEquals(eventsReceived, OPEN|ACTIVATE);
         
         page.close();
    }

    public void testPageClosed() throws Throwable {
         
         IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);
         
         eventsReceived = 0;
         pageMask = page;
         page.close();
         assertEquals(eventsReceived, CLOSE);
    }

    public void testPageActivate() throws Throwable {
         
         IWorkbenchPage page1 = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);
         IWorkbenchPage page2 = fWindow.openPage(EmptyPerspective.PERSP_ID,
         fWorkspace);
         
         eventsReceived = 0;
         pageMask = page1;
         fWindow.setActivePage(page1);
         assertEquals(eventsReceived, ACTIVATE);

         eventsReceived = 0;		
         pageMask = page2;
         fWindow.setActivePage(page2);
         assertEquals(eventsReceived, ACTIVATE);
         
         page1.close();
         page2.close();
    }

    @Override
	public void pageActivated(IWorkbenchPage page) {
        if (pageMask == null || page == pageMask)
            eventsReceived = eventsReceived | ACTIVATE;
    }

    @Override
	public void pageClosed(IWorkbenchPage page) {
        if (pageMask == null || page == pageMask)
        	eventsReceived = eventsReceived | CLOSE;
    }

    @Override
	public void pageOpened(IWorkbenchPage page) {
        if (pageMask == null || page == pageMask)
            eventsReceived |= OPEN;
    }

}
