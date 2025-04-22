package org.eclipse.ui.tests.dnd;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.dnd.TestDropLocation;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.api.MockEditorPart;
import org.eclipse.ui.tests.autotests.AbstractTestLogger;
import org.eclipse.ui.tests.autotests.UITestCaseWithResult;
import org.eclipse.ui.tests.harness.util.FileUtil;

public class DragTest extends UITestCaseWithResult {
    TestDragSource dragSource;

    TestDropLocation dropTarget;

    String intendedResult;

    static IProject project;

    static IFile file1, file2;

    IEditorPart editor1, editor2;

    static IFile file3;

    IEditorPart editor3;

    static WorkbenchWindow window;

    static WorkbenchPage page;

    public DragTest(TestDragSource dragSource, TestDropLocation dropTarget, AbstractTestLogger log, String suffix) {
        super("drag " + dragSource.toString() + " to " + dropTarget.toString() + suffix, log);
        this.dragSource = dragSource;
        this.dropTarget = dropTarget;
    }

    public DragTest(TestDragSource dragSource, TestDropLocation dropTarget, AbstractTestLogger log) {
    	this(dragSource, dropTarget, log, "");
    }

    @Override
	public void doSetUp() throws Exception {
        manageWindows(false);

        if (window == null) {
            window = (WorkbenchWindow) fWorkbench.openWorkbenchWindow(
            	"org.eclipse.ui.tests.dnd.dragdrop", getPageInput());

            page = (WorkbenchPage) window.getActivePage();

            project = FileUtil.createProject("DragTest"); //$NON-NLS-1$
            file1 = FileUtil.createFile("DragTest1.txt", project); //$NON-NLS-1$
            file2 = FileUtil.createFile("DragTest2.txt", project); //$NON-NLS-1$
            file3 = FileUtil.createFile("DragTest3.txt", project); //$NON-NLS-1$

            IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
            apiStore.setValue(
                    IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS,
                    false);
        }

        page.resetPerspective();
        page.closeAllEditors(false);

        page.showView("org.eclipse.ui.views.ContentOutline");
        page.hideView(page.findView("org.eclipse.ui.internal.introview"));
        editor1 = page.openEditor(new FileEditorInput(file1),
                MockEditorPart.ID1);
        editor2 = page.openEditor(new FileEditorInput(file2),
                MockEditorPart.ID2);
        editor3 = page.openEditor(new FileEditorInput(file3),
                MockEditorPart.ID2);

        window.getShell().setActive();
        DragOperations
                .drag(editor2, new EditorDropTarget(new ExistingWindowProvider(window), 0, SWT.CENTER), false);
        DragOperations
                .drag(editor3, new EditorAreaDropTarget(new ExistingWindowProvider(window), SWT.RIGHT), false);
    }

    public void stallTest() {
    	String[] testNames = {
    	};

    	boolean testNameMatches = false;
    	for (String testName : testNames) {
    		if (testName.equals(this.getName())) {
    			testNameMatches = true;
    			break;
    		}
    	}

    	if (testNames.length == 0 || testNameMatches) {
	    	Display display = Display.getCurrent();
	    	Shell loopShell = new Shell(display, SWT.SHELL_TRIM);
	    	loopShell.setBounds(0,0,200,100);
	    	loopShell.setText("Test Stall Shell");
	    	loopShell.setVisible(true);

	    	while (loopShell != null && !loopShell.isDisposed()) {
	    		if (!display.readAndDispatch()) {
					display.sleep();
				}
	    	}
    	}
    }

    @Override
	public String performTest() throws Throwable {

        IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
        boolean curMinMaxState = apiStore.getBoolean(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX);
    	if (getName().indexOf("drag maximized") >= 0) {
			apiStore.setValue(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX, false);
		}

    	dragSource.setPage(page);

        dragSource.drag(dropTarget);



		apiStore.setValue(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX, curMinMaxState);

        return DragOperations.getLayoutDescription(page);
    }
}
