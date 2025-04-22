package org.eclipse.ui.tests.api;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.harness.util.UITestCase;

public abstract class IWorkbenchPartSiteTest extends UITestCase {

    protected IWorkbenchWindow fWindow;

    protected IWorkbenchPage fPage;

    public IWorkbenchPartSiteTest(String testName) {
        super(testName);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    public void testGetId() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertEquals(getTestPartId(), site.getId());
    }

    public void testGetPage() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertEquals(fPage, site.getPage());
    }

    public void testGetPluginId() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertEquals(getTestPartPluginId(), site.getPluginId());
    }

    public void testGetRegisteredName() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertEquals(getTestPartName(), site.getRegisteredName());
    }

    public void testGetShell() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertEquals(fWindow.getShell(), site.getShell());
    }

    public void testGetWorkbenchWindow() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertEquals(fWindow, site.getWorkbenchWindow());
    }

    public void testGetSelectionProvider() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        assertTrue(part instanceof MockWorkbenchPart);
        MockWorkbenchPart mock = (MockWorkbenchPart) part;
        assertEquals(mock.getSelectionProvider(), site.getSelectionProvider());
    }

    public void testSetSelectionProvider() throws Throwable {

        IWorkbenchPart part = createTestPart(fPage);
        IWorkbenchPartSite site = part.getSite();
        site.setSelectionProvider(null);
        assertNull(site.getSelectionProvider());

        MockSelectionProvider provider = new MockSelectionProvider();
        site.setSelectionProvider(provider);
        assertEquals(provider, site.getSelectionProvider());
    }
    
    public void testINestableService() throws Throwable {
    	IWorkbenchPart part = createTestPart(fPage);
    	IWorkbenchPartSite site = part.getSite();
		DummyService service = site.getService(DummyService.class);

    	assertTrue(service.isActive());
    	if(part instanceof IViewPart)
    		fPage.hideView((IViewPart) part);
    	else
    		fPage.closeEditor((IEditorPart) part, false);
    	assertFalse(service.isActive());
    	
    }
    

    abstract protected IWorkbenchPart createTestPart(IWorkbenchPage page)
            throws Throwable;

    abstract protected String getTestPartId() throws Throwable;

    abstract protected String getTestPartName() throws Throwable;

    protected String getTestPartPluginId() throws Throwable {
        return "org.eclipse.ui.tests";
    }
}

