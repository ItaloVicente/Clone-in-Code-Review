package org.eclipse.ui.tests.api;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.harness.util.ActionUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class IActionFilterTest extends UITestCase {

    protected IWorkbenchWindow fWindow;

    protected IWorkbenchPage fPage;

    protected String STATIC_MENU_VIEW_ID = "org.eclipse.ui.tests.api.IActionFilterTest1";

    protected String DYNAMIC_MENU_VIEW_ID = "org.eclipse.ui.tests.api.IActionFilterTest2";

    public IActionFilterTest(String testName) {
        super(testName);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    public void testStaticLifecycle() throws Throwable {
        testLifecycle(STATIC_MENU_VIEW_ID);
    }

    public void testDynamicLifecycle() throws Throwable {
        testLifecycle(DYNAMIC_MENU_VIEW_ID);
    }

    private void testLifecycle(String viewId) throws Throwable {
        ListView view = (ListView) fPage.showView(viewId);

        ListElement red = new ListElement("red");
        view.addElement(red);
        view.selectElement(red);

        ListElementActionFilter filter = ListElementActionFilter.getSingleton();

        MenuManager menuMgr = view.getMenuManager();
        ActionUtil.fireAboutToShow(menuMgr);
        assertTrue(filter.getCalled());
    }

    public void testDynamicMenuContribution() throws Throwable {
        testMenu(DYNAMIC_MENU_VIEW_ID);
    }

    public void testStaticMenuContribution() throws Throwable {
        testMenu(STATIC_MENU_VIEW_ID);
    }

    private void testMenu(String viewId) throws Throwable {
        ListElement red = new ListElement("red");
        ListElement blue = new ListElement("blue");
        ListElement green = new ListElement("green");
        ListElement redTrue = new ListElement("red", true);

        ListView view = (ListView) fPage.showView(viewId);
        MenuManager menuMgr = view.getMenuManager();
        view.addElement(red);
        view.addElement(blue);
        view.addElement(green);
        view.addElement(redTrue);

        ListElementActionFilter filter = ListElementActionFilter.getSingleton();

        view.selectElement(red);
        ActionUtil.fireAboutToShow(menuMgr);
        assertTrue(filter.getCalled());
        assertNotNull(ActionUtil.getActionWithLabel(menuMgr, "redAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "blueAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "trueAction_v1"));
        assertNotNull(ActionUtil.getActionWithLabel(menuMgr, "falseAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "redTrueAction_v1"));
        view.verifyActions(this, menuMgr);

        filter.clearCalled();
        view.selectElement(blue);
        ActionUtil.fireAboutToShow(menuMgr);
        assertTrue(filter.getCalled());
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "redAction_v1"));
        assertNotNull(ActionUtil.getActionWithLabel(menuMgr, "blueAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "trueAction_v1"));
        assertNotNull(ActionUtil.getActionWithLabel(menuMgr, "falseAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "redTrueAction_v1"));
        view.verifyActions(this, menuMgr);

        filter.clearCalled();
        view.selectElement(green);
        ActionUtil.fireAboutToShow(menuMgr);
        assertTrue(filter.getCalled());
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "redAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "blueAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "trueAction_v1"));
        assertNotNull(ActionUtil.getActionWithLabel(menuMgr, "falseAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "redTrueAction_v1"));
        view.verifyActions(this, menuMgr);

        filter.clearCalled();
        view.selectElement(redTrue);
        ActionUtil.fireAboutToShow(menuMgr);
        assertTrue(filter.getCalled());
        assertNotNull(ActionUtil.getActionWithLabel(menuMgr, "redAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "blueAction_v1"));
        assertNotNull(ActionUtil.getActionWithLabel(menuMgr, "trueAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "falseAction_v1"));
        assertNotNull(ActionUtil
                .getActionWithLabel(menuMgr, "redTrueAction_v1"));
        view.verifyActions(this, menuMgr);

        filter.clearCalled();
        view.selectElement(null);
        ActionUtil.fireAboutToShow(menuMgr);
        assertTrue(!filter.getCalled());
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "redAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "blueAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "trueAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "falseAction_v1"));
        assertNull(ActionUtil.getActionWithLabel(menuMgr, "redTrueAction_v1"));
        view.verifyActions(this, menuMgr);
    }
}
