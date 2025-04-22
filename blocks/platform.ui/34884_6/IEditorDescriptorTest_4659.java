package org.eclipse.ui.tests.api;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.harness.util.ActionUtil;
import org.eclipse.ui.tests.harness.util.FileUtil;

public class IEditorActionDelegateTest extends IActionDelegateTest {

    public static String EDITOR_ID = "org.eclipse.ui.tests.api.IEditorActionDelegateTest";

    private MockEditorPart editor;

    public IEditorActionDelegateTest(String testName) {
        super(testName);
    }

    public void testSetActiveEditor() throws Throwable {

        testRun();

        MockActionDelegate delegate = getDelegate();
        assertNotNull(delegate);
        assertTrue(delegate.callHistory.verifyOrder(new String[] {
                "setActiveEditor", "selectionChanged", "run" }));
    }

    @Override
	protected Object createActionWidget() throws Throwable {
        editor = openEditor(fPage, "X");
        return editor;
    }

    @Override
	protected void runAction(Object widget) throws Throwable {
        MockEditorPart editor = (MockEditorPart) widget;
        MockEditorActionBarContributor contributor = (MockEditorActionBarContributor) editor
                .getEditorSite().getActionBarContributor();
        IMenuManager mgr = contributor.getActionBars().getMenuManager();
        ActionUtil.runActionWithLabel(this, mgr, "Mock Action");
    }

    @Override
	protected void fireSelection(Object widget) throws Throwable {
        MockEditorPart editor = (MockEditorPart) widget;
        editor.fireSelection();
    }

    protected MockEditorPart openEditor(IWorkbenchPage page, String suffix)
            throws Throwable {
        IProject proj = FileUtil.createProject("IEditorActionDelegateTest");
        IFile file = FileUtil.createFile("test" + suffix + ".txt", proj);
        return (MockEditorPart) page.openEditor(new FileEditorInput(file),
                EDITOR_ID);
    }

}

