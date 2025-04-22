package org.eclipse.ui.tests.zoom;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.api.MockEditorPart;
import org.eclipse.ui.tests.harness.util.FileUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class ZoomTestCase extends UITestCase {

    protected WorkbenchWindow window;

    protected WorkbenchPage page;

    protected IProject project;

    protected IFile file1, file2;

    protected IEditorPart editor1, editor2, editor3;

    protected IViewPart stackedView1;
    protected IViewPart stackedView2;
    protected IViewPart unstackedView;
    protected IViewPart fastView;

    private IFile file3;

	private boolean oldMinMaxState;

    public ZoomTestCase(String name) {
        super(name);
    }

    @Override
	protected void doTearDown() throws Exception {

        super.doTearDown();

        IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
        apiStore.setValue(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX, oldMinMaxState);
    }

    @Override
	protected void doSetUp() throws Exception {
        IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
        oldMinMaxState = apiStore.getBoolean(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX);

        super.doSetUp();

        window = (WorkbenchWindow) openTestWindow(ZoomPerspectiveFactory.PERSP_ID);
        page = (WorkbenchPage) window.getActivePage();

        apiStore.setValue(
                IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS,
                false);

        oldMinMaxState = apiStore.getBoolean(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX);

        apiStore.setValue(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX, false);

        try {
            project = FileUtil.createProject("IEditorPartTest"); //$NON-NLS-1$
            file1 = FileUtil.createFile("Test1.txt", project); //$NON-NLS-1$
            file2 = FileUtil.createFile("Test2.txt", project); //$NON-NLS-1$
            file3 = FileUtil.createFile("Test3.txt", project); //$NON-NLS-1$
            editor1 = page.openEditor(new FileEditorInput(file1),
                    MockEditorPart.ID1);
            editor2 = page.openEditor(new FileEditorInput(file2),
                    MockEditorPart.ID2);
            editor3 = page.openEditor(new FileEditorInput(file3),
                    MockEditorPart.ID2);

        } catch (PartInitException e) {
        } catch (CoreException e) {
        }

        stackedView1 = findView(ZoomPerspectiveFactory.STACK1_VIEW1);
        stackedView2 = findView(ZoomPerspectiveFactory.STACK1_VIEW2);
        unstackedView = findView(ZoomPerspectiveFactory.UNSTACKED_VIEW1);
    }

    protected void zoom(IWorkbenchPart part) {
        if (part == null) {
			throw new NullPointerException();
		}
        page.activate(part);
        page.toggleZoom(page.getReference(part));
        Assert.assertTrue(page.isPageZoomed());
        Assert.assertTrue(isZoomed(part));
    }

    protected void openEditor(IFile file, boolean activate) {
        try {
            if (file == file1) {
				editor1 = IDE.openEditor(page, file, activate);
			}
            if (file == file2) {
				editor2 = IDE.openEditor(page, file, activate);
			}
        } catch (PartInitException e) {
        }
    }

    protected IViewPart showRegularView(String id, int mode) {
        try {
            IViewPart view = page.showView(id, null, mode);
            return view;
        } catch (PartInitException e) {
        }
        return null;
    }

    protected IViewPart findView(String id) {
        IViewPart view = page.findView(id);
        assertNotNull("View " + id + " not found", view);
        return view;
    }

    protected MPart getPartModel(IWorkbenchPart part) {
        PartSite site = (PartSite) part.getSite();
        return site.getModel();
    }

    protected MUIElement getPartParent(IWorkbenchPart part) {
        MPart partModel = getPartModel(part);

        MUIElement partParent = partModel.getParent();
        if (partParent == null && partModel.getCurSharedRef() != null) {
			partParent = partModel.getCurSharedRef().getParent();
		}

        return partParent;
    }

    protected boolean isZoomed(IWorkbenchPart part) {
    	if (part == null) {
			return false;
		}

    	MUIElement toTest = page.getActiveElement(page.getReference(part));
    	if (toTest == null) {
			return false;
		}

    	return toTest.getTags().contains(IPresentationEngine.MAXIMIZED);
    }

    protected void assertZoomed(IWorkbenchPart part) {
        if (part == null) {
            Assert.assertFalse("Page should not be zoomed", isZoomed());
        } else {
            Assert.assertTrue("Expecting " + partName(part) + " to be zoomed", isZoomed(part));
            Assert.assertTrue("Page should be zoomed", isZoomed());
        }
    }

    protected void assertActive(IWorkbenchPart part) {
        IWorkbenchPart activePart = page.getActivePart();

        Assert.assertTrue("Unexpected active part: expected " + partName(part)
                + " and found " + partName(activePart), activePart == part);

        if (part instanceof IEditorPart) {
            assertActiveEditor((IEditorPart)part);
        }
    }

    protected String partName(IWorkbenchPart part) {
        if (part == null) {
            return "null";
        }

        return Util.safeString(part.getTitle());
    }

    protected void assertActiveEditor(IEditorPart part) {
        IWorkbenchPart activeEditor = page.getActiveEditor();

        Assert.assertTrue("Unexpected active editor: expected " + partName(part)
                + " and found " + partName(activeEditor), activeEditor == part);
    }

    protected boolean isZoomed() {
        return page.isPageZoomed();
    }

    public void close(IWorkbenchPart part) {
        if (part instanceof IViewPart) {
            page.hideView((IViewPart)part);
        } else if (part instanceof IEditorPart) {
            page.closeEditor((IEditorPart)part, false);
        }
    }
}
