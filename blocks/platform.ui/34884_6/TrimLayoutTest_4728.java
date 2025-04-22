package org.eclipse.ui.tests.api;

import junit.framework.TestSuite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.harness.util.FileUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.eclipse.ui.views.IStickyViewDescriptor;

public class StickyViewTest extends UITestCase {
	
	public static TestSuite suite() {
		return new TestSuite(StickyViewTest.class);
	}
	
    private IWorkbenchWindow window;

    private IWorkbenchPage page;

    public StickyViewTest(String testName) {
        super(testName);
    }

    public void testStackPlacementRight() {
        testStackPlacement("Right");
    }

    public void testStackPlacementLeft() {
        testStackPlacement("Left");
    }

    public void testStackPlacementTop() {
        testStackPlacement("Top");
    }

    public void testStackPlacementBottom() {
        testStackPlacement("Bottom");
    }

    private void testStackPlacement(String location) {
        try {
            IViewPart part1 = page
                    .showView("org.eclipse.ui.tests.api.StickyView" + location
                            + "1");
            assertNotNull(part1);
            IViewPart part2 = page
                    .showView("org.eclipse.ui.tests.api.StickyView" + location
                            + "2");
            assertNotNull(part2);
            IViewPart[] stack = page.getViewStack(part1);

            assertTrue(ViewUtils.findInStack(stack, part1));
            assertTrue(ViewUtils.findInStack(stack, part2));

        } catch (PartInitException e) {
            fail(e.getMessage());
        }

    }

    public void testStackContents() {
        try {
            IViewPart part1 = page
                    .showView("org.eclipse.ui.tests.api.StickyViewRight1");
            assertNotNull(part1);

            IViewPart[] stack = page.getViewStack(part1);

            for (int i = 0; i < stack.length; i++) {
                assertTrue(stack[i].getTitle(), ViewUtils.isSticky(stack[i]));
            }
        } catch (PartInitException e) {
            fail(e.getMessage());
        }
    }

    public void XXXtestClosableFlag() {
        testCloseable("org.eclipse.ui.tests.api.StickyViewRight1", true);
        testCloseable("org.eclipse.ui.tests.api.StickyViewRight2", false);
        testCloseable("org.eclipse.ui.tests.api.StickyViewLeft1", true);
    }

    public void XXXtestMoveableFlag() {
        testMoveable("org.eclipse.ui.tests.api.StickyViewRight1", true);
        testMoveable("org.eclipse.ui.tests.api.StickyViewRight2", false);
        testMoveable("org.eclipse.ui.tests.api.StickyViewLeft1", true);
    }

    private void testMoveable(String id, boolean expectation) {
        try {
            IViewPart part = page.showView(id);
            assertNotNull(part);
            assertTrue(ViewUtils.isSticky(part));

            IStickyViewDescriptor[] descs = PlatformUI.getWorkbench()
                    .getViewRegistry().getStickyViews();
            for (int i = 0; i < descs.length; i++) {
                if (descs[i].getId().equals(id)) {
                    assertEquals(expectation, descs[i].isMoveable());
                }
            }

            assertEquals(expectation, ViewUtils.isMoveable(part));
        } catch (PartInitException e) {
            fail(e.getMessage());
        }
    }

    private void testCloseable(String id, boolean expectation) {
        try {
            IViewPart part = page.showView(id);
            assertNotNull(part);
            assertTrue(ViewUtils.isSticky(part));

            IStickyViewDescriptor[] descs = PlatformUI.getWorkbench()
                    .getViewRegistry().getStickyViews();
            for (int i = 0; i < descs.length; i++) {
                if (descs[i].getId().equals(id)) {
                    assertEquals(expectation, descs[i].isCloseable());
                }
            }

            assertEquals(expectation, ViewUtils.isCloseable(part));
        } catch (PartInitException e) {
            fail(e.getMessage());
        }
    }

    public void testPerspectiveReset() {
        try {
            page.showView("org.eclipse.ui.tests.api.StickyViewRight1");
            page.resetPerspective();
            assertNotNull(page
                    .findView("org.eclipse.ui.tests.api.StickyViewRight1"));
        } catch (PartInitException e) {
            fail(e.getMessage());
        }
    }

    public void testPerspectiveOpen() {
        try {
            page.showView("org.eclipse.ui.tests.api.StickyViewRight1");
            page.setPerspective(WorkbenchPlugin.getDefault()
                    .getPerspectiveRegistry().findPerspectiveWithId(
                            "org.eclipse.ui.tests.api.SessionPerspective"));
            assertNotNull(page
                    .findView("org.eclipse.ui.tests.api.StickyViewRight1"));
        } catch (PartInitException e) {
            fail(e.getMessage());
        }
    }
    
    public void testPerspectiveCloseStandaloneView() throws Throwable {
		page.setPerspective(WorkbenchPlugin.getDefault()
				.getPerspectiveRegistry().findPerspectiveWithId(
						PerspectiveViewsBug120934.PERSP_ID));

		try {
			IViewReference standAloneRef = page
					.findViewReference(IPageLayout.ID_OUTLINE);

			page.hideView(standAloneRef);
		} finally {
			page.closePerspective(page.getPerspective(), false, false);
		}
    }
    
	public void XXXtestPerspectiveCloseFastView() throws Throwable {
		page.setPerspective(WorkbenchPlugin.getDefault()
				.getPerspectiveRegistry().findPerspectiveWithId(
						PerspectiveViewsBug88345.PERSP_ID));

		try {
			IViewReference stickyRef = page
					.findViewReference(MockViewPart.IDMULT);
			IViewPart stickyView = (IViewPart) stickyRef.getPart(true);
			page.activate(stickyView);

			IViewReference viewRef = page
					.findViewReference(PerspectiveViewsBug88345.NORMAL_VIEW_ID);

			assertFalse(APITestUtils.isFastView(stickyRef));


			fail("facade.addFastView() had no implementation");
			
			assertTrue(APITestUtils.isFastView(stickyRef));

			fail("facade.addFastView() had no implementation");

			assertTrue(APITestUtils.isFastView(viewRef));

			
			
			fail("facade.getFVBContribution() not implemented");


		} finally {
			page.closePerspective(page.getPerspective(), false, false);
		}
	}
	
	public void XXXtestPerspectiveMoveFastView() throws Throwable {
		page.setPerspective(WorkbenchPlugin.getDefault()
				.getPerspectiveRegistry().findPerspectiveWithId(
						PerspectiveViewsBug88345.PERSP_ID));

		try {
			IViewReference stickyRef = page
					.findViewReference(MockViewPart.IDMULT, "1");

			IViewReference viewRef = page
					.findViewReference(PerspectiveViewsBug88345.NORMAL_VIEW_ID);

			assertFalse(APITestUtils.isFastView(viewRef));
			assertTrue(APITestUtils.isFastView(stickyRef));

			fail("facade.addFastView() had no implementation");

			assertTrue(APITestUtils.isFastView(viewRef));

			
			fail("facade.addFastView() had no implementation");



		} finally {
			page.closePerspective(page.getPerspective(), false, false);
		}
	}


	public void XXXtestPerspectiveViewToolBarVisible() throws Throwable {
        IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
        boolean oldMinMaxState = apiStore.getBoolean(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX);
		apiStore.setValue(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX, false);
        
		IPerspectiveDescriptor perspective = WorkbenchPlugin.getDefault()
				.getPerspectiveRegistry().findPerspectiveWithId(
						PerspectiveViewsBug88345.PERSP_ID);
		page.setPerspective(perspective);

		IEditorPart editor = null;
		IEditorRegistry registry = window.getWorkbench().getEditorRegistry();
		IPerspectiveDescriptor secondPerspective = WorkbenchPlugin.getDefault()
				.getPerspectiveRegistry().findPerspectiveWithId(
						SessionPerspective.ID);
		try {
			page.showView(PerspectiveViewsBug88345.PROP_SHEET_ID);
			IViewReference viewRef = page
					.findViewReference(PerspectiveViewsBug88345.PROP_SHEET_ID);

			IProject proj = FileUtil.createProject("TBTest");
			IFile test01 = FileUtil.createFile("test01.txt", proj);

			assertNotNull("The view must exist", viewRef.getPart(true));
			page.activate(viewRef.getPart(true));

			
			fail("facade.isViewPaneVisible() had no implementation");

			fail("facade.isViewToolbarVisible() had no implementation");


			editor = page.openEditor(new FileEditorInput(test01), registry
					.getDefaultEditor(test01.getName()).getId());
			assertNotNull("must have my editor", editor);

			IWorkbenchPartReference ref = page.getReference(editor);
			page.toggleZoom(ref);
			fail("facade.isViewPaneVisible() had no implementation");

			fail("facade.isViewToolbarVisible() had no implementation");


			page.setPerspective(secondPerspective);

			fail("facade.isViewPaneVisible() had no implementation");

			fail("facade.isViewToolbarVisible() had no implementation");


			page.setPerspective(perspective);
			processEvents();

			fail("facade.isViewPaneVisible() had no implementation");

			fail("facade.isViewToolbarVisible() had no implementation");

		} finally {
			if (editor != null) {
				page.closeEditor(editor, false);
			}
			page.closePerspective(perspective, false, false);
			page.closePerspective(secondPerspective, false, false);

			apiStore.setValue(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX, oldMinMaxState);
		}
	}

    @Override
	protected void doSetUp() throws Exception {
        window = openTestWindow();
        page = window.getActivePage();
    }
}
