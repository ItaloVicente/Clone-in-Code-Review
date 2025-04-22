package org.eclipse.ui.tests.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.SubCoolBarManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IActionBars2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.api.MockAction;
import org.eclipse.ui.tests.api.MockEditorActionBarContributor;
import org.eclipse.ui.tests.api.MockEditorPart;
import org.eclipse.ui.tests.api.MockViewPart;
import org.eclipse.ui.tests.harness.util.FileUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class EditorActionBarsTest extends UITestCase {

    protected IWorkbenchWindow fWindow;

    protected IWorkbenchPage fPage;

    private String EDITOR_ID = "org.eclipse.ui.tests.internal.EditorActionBarsTest";

    public EditorActionBarsTest(String testName) {
        super(testName);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    public void testActionEnablementWhenActive() throws Throwable {
        MockEditorPart editor = openEditor(fPage, "1");
        MockEditorActionBarContributor contributor = (MockEditorActionBarContributor) editor
                .getEditorSite().getActionBarContributor();

        contributor.enableActions(true);
        verifyToolItemState(contributor, true);

        contributor.enableActions(false);
        verifyToolItemState(contributor, false);
    }

    public void testActionEnablementWhenInactive() throws Throwable {
        MockEditorPart editor = openEditor(fPage, "2");
        MockEditorActionBarContributor contributor = (MockEditorActionBarContributor) editor
                .getEditorSite().getActionBarContributor();

        contributor.enableActions(true);
        verifyToolItemState(contributor, true);

        fPage.showView(MockViewPart.ID);
        contributor.enableActions(false);
        fPage.activate(editor);
        verifyToolItemState(contributor, false);

        fPage.showView(MockViewPart.ID);
        contributor.enableActions(true);
        fPage.activate(editor);
        verifyToolItemState(contributor, true);
    }

    public void testCoolBarContribution() throws Throwable {

        MockEditorPart editor = openEditor(fPage, "3");
        MockEditorActionBarContributor contributor = (MockEditorActionBarContributor) editor
                .getEditorSite().getActionBarContributor();

        assertTrue(contributor.getActionBars() instanceof IActionBars2);
        IActionBars2 actionBars = (IActionBars2) contributor.getActionBars();

        assertTrue(actionBars.getCoolBarManager() instanceof SubCoolBarManager);
        SubCoolBarManager coolBarManager = (SubCoolBarManager) actionBars.getCoolBarManager();
        assertTrue("Coolbar should be visible", coolBarManager.isVisible());
    }



    protected MockEditorPart openEditor(IWorkbenchPage page, String suffix)
            throws Throwable {
        IProject proj = FileUtil.createProject("IEditorActionBarsTest");
        IFile file = FileUtil.createFile("test" + suffix + ".txt", proj);
        return (MockEditorPart) page.openEditor(new FileEditorInput(file),
                EDITOR_ID);
    }

    protected void verifyToolItemState(MockEditorActionBarContributor ctr,
            boolean enabled) {
        MockAction[] actions = ctr.getActions();
        for (MockAction action : actions) {
			verifyToolItemState(action, enabled);
		}
    }

    protected void verifyToolItemState(IAction action, boolean enabled) {
        String actionText = action.getText();
        ICoolBarManager tbm = ((WorkbenchWindow) fWindow).getCoolBarManager();
        IContributionItem[] coolItems = tbm.getItems();
        for (IContributionItem coolItem2 : coolItems) {
            if (coolItem2 instanceof ToolBarContributionItem) {
                ToolBarContributionItem coolItem = (ToolBarContributionItem) coolItem2;
                IToolBarManager citbm = coolItem.getToolBarManager();
                ToolBar tb = ((ToolBarManager) citbm).getControl();
                verifyNullToolbar(tb, actionText, citbm);
                if (tb != null && !tb.isDisposed()) {
                    ToolItem[] items = tb.getItems();
                    for (ToolItem item : items) {
                        String itemText = item.getToolTipText();
                        if (actionText.equals(itemText)) {
                            assertEquals(enabled, item.getEnabled());
                            return;
                        }
                    }
                }
            }
        }
        fail("Action for " + actionText + " not found");
    }

    private void verifyNullToolbar(ToolBar tb, String actionText,
            IToolBarManager manager) {
        if (tb == null) { // toolbar should only be null if the given manager is
            IContributionItem[] items = manager.getItems();
            for (int i = 0; i < items.length; i++) {
                if (!(items[i] instanceof Separator) && items[i].isVisible()) {
                    fail("No toolbar for a visible action text \"" + actionText
                            + "\"");
                }
            }

        }
    }

    public void test239945() throws Throwable {
		CoolBarManager coolBarManager = new CoolBarManager();
		coolBarManager.add(new Separator(CoolBarManager.USER_SEPARATOR));
		try {
			coolBarManager.createControl(fWindow.getShell());
			coolBarManager.update(true);
		} catch (ArrayIndexOutOfBoundsException e) {
			fail("Exception updating cool bar with a single separator");
		}

		CoolBarManager coolBarManager2 = new CoolBarManager();
		coolBarManager2.add(new Separator(CoolBarManager.USER_SEPARATOR));
		try {
			coolBarManager2.createControl(fWindow.getShell());
			coolBarManager2.update(true);
		} catch (ArrayIndexOutOfBoundsException e) {
			fail("Exception updating cool bar with multiple separators");
		}
    }
}

