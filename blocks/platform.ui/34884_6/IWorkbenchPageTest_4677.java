package org.eclipse.ui.tests.api;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

public class IViewSiteTest extends IWorkbenchPartSiteTest {

    public IViewSiteTest(String testName) {
        super(testName);
    }

    @Override
	protected String getTestPartName() throws Throwable {
        return MockViewPart.NAME;
    }

    @Override
	protected String getTestPartId() throws Throwable {
        return MockViewPart.ID;
    }

    @Override
	protected IWorkbenchPart createTestPart(IWorkbenchPage page)
            throws Throwable {
        return page.showView(MockViewPart.ID);
    }

    public void testGetActionBars() throws Throwable {

        IViewPart view = (IViewPart) createTestPart(fPage);
        IViewSite site = view.getViewSite();
        assertNotNull(site.getActionBars());
    }

}

