package org.eclipse.ui.tests.api;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.tests.harness.util.CallHistory;

public class IViewPartTest extends IWorkbenchPartTest {

    public IViewPartTest(String testName) {
        super(testName);
    }

    @Override
	protected MockPart openPart(IWorkbenchPage page) throws Throwable {
        return (MockWorkbenchPart) page.showView(MockViewPart.ID);
    }

    @Override
	protected void closePart(IWorkbenchPage page, MockPart part)
            throws Throwable {
        page.hideView((IViewPart) part);
    }
    
    public void XXXtestOpenAndCloseSaveNotNeeded() throws Throwable {
        SaveableMockViewPart part = (SaveableMockViewPart) fPage.showView(SaveableMockViewPart.ID);
        part.setDirty(true);
        part.setSaveNeeded(false);
        closePart(fPage, part);
        
        CallHistory history = part.getCallHistory();
        
        assertTrue(history.verifyOrder(new String[] { "setInitializationData", "init",
                "createPartControl", "setFocus", "isSaveOnCloseNeeded", 
                "widgetDisposed", "toolbarContributionItemWidgetDisposed", 
                "toolbarContributionItemDisposed", "dispose" }));
        assertFalse(history.contains("doSave"));
    }
    
}

