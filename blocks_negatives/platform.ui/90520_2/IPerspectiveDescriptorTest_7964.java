/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.api;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.SlavePartService;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.harness.util.CallHistory;
import org.eclipse.ui.tests.harness.util.EmptyPerspective;
import org.eclipse.ui.tests.harness.util.FileUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

/**
 * Tests the IPartService, IPartListener and IPartListener2 interfaces.
 */
public class IPartServiceTest extends UITestCase {

    private IWorkbenchWindow fWindow;

    private IWorkbenchPage fPage;

    private IWorkbenchPart eventPart;

    private IWorkbenchPartReference eventPartRef;

    class TestPartListener implements IPartListener {
        @Override
		public void partActivated(IWorkbenchPart part) {
            history.add("partActivated");
            eventPart = part;
        }

        @Override
		public void partBroughtToTop(IWorkbenchPart part) {
            history.add("partBroughtToTop");
            eventPart = part;
        }

        @Override
		public void partClosed(IWorkbenchPart part) {
            history.add("partClosed");
            eventPart = part;
        }

        @Override
		public void partDeactivated(IWorkbenchPart part) {
            history.add("partDeactivated");
            eventPart = part;
        }

        @Override
		public void partOpened(IWorkbenchPart part) {
            history.add("partOpened");
            eventPart = part;
        }
    }

    class TestPartListener2 implements IPartListener2 {
        @Override
		public void partActivated(IWorkbenchPartReference ref) {
            history2.add("partActivated");
            eventPartRef = ref;
        }

        @Override
		public void partBroughtToTop(IWorkbenchPartReference ref) {
            history2.add("partBroughtToTop");
            eventPartRef = ref;
        }

        @Override
		public void partClosed(IWorkbenchPartReference ref) {
            history2.add("partClosed");
            eventPartRef = ref;
        }

        @Override
		public void partDeactivated(IWorkbenchPartReference ref) {
            history2.add("partDeactivated");
            eventPartRef = ref;
        }

        @Override
		public void partOpened(IWorkbenchPartReference ref) {
            history2.add("partOpened");
            eventPartRef = ref;
        }

        @Override
		public void partHidden(IWorkbenchPartReference ref) {
            history2.add("partHidden");
            eventPartRef = ref;
        }

        @Override
		public void partVisible(IWorkbenchPartReference ref) {
            history2.add("partVisible");
            eventPartRef = ref;
        }

        @Override
		public void partInputChanged(IWorkbenchPartReference ref) {
            history2.add("partInputChanged");
            eventPartRef = ref;
        }
    }

    private IPartListener partListener = new TestPartListener();

    private IPartListener2 partListener2 = new TestPartListener2();

    private CallHistory history = new CallHistory(partListener);

    private CallHistory history2 = new CallHistory(partListener2);

    public IPartServiceTest(String testName) {
        super(testName);
    }

    /**
     * Clear the event state.
     */
    private void clearEventState() {
        eventPart = null;
        eventPartRef = null;
        history.clear();
        history2.clear();
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        fWindow = openTestWindow();
        fPage = fWindow.getActivePage();
    }

    private IWorkbenchPartReference getRef(IWorkbenchPart part) {
    	return fPage.getReference(part);
    }

    /**
     * Tests the addPartListener method on IWorkbenchPage's part service.
     */
    public void testAddPartListenerToPage() throws Throwable {
        fPage.addPartListener(partListener);
        fPage.addPartListener(partListener2);

        clearEventState();
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        assertTrue(history.verifyOrder(new String[] { "partOpened",
                "partActivated" }));
        assertEquals(view, eventPart);
        assertTrue(history2.verifyOrder(new String[] { "partOpened",
                "partVisible", "partActivated" }));
        assertEquals(getRef(view), eventPartRef);

        clearEventState();
        fPage.hideView(view);
        assertTrue(history.verifyOrder(new String[] { "partDeactivated",
                "partClosed" }));
        assertEquals(view, eventPart);
        assertTrue(history2.verifyOrder(new String[] { "partDeactivated",
                "partHidden", "partClosed" }));
        assertEquals(getRef(view), eventPartRef);

        fPage.removePartListener(partListener);
        fPage.removePartListener(partListener2);
    }

    public void testLocalPartService() throws Throwable {
    	IPartService service = fWindow
				.getService(IPartService.class);

		MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
		MockViewPart view2 = (MockViewPart) fPage.showView(MockViewPart.ID2);

		IPartService slaveService = view.getSite()
				.getService(IPartService.class);

		assertTrue(service != slaveService);
		assertNotNull(slaveService);
		assertNotNull(service);
		assertTrue(slaveService instanceof SlavePartService);

		slaveService.addPartListener(partListener);
		fPage.activate(view);

		assertTrue(history.verifyOrder(new String[] { "partDeactivated",
				"partActivated" }));

		slaveService.removePartListener(partListener);
		clearEventState();
		fPage.activate(view2);
		assertTrue(history.isEmpty());

		slaveService.addPartListener(partListener);
		clearEventState();
		fPage.hideView(view2);
		assertTrue(history.verifyOrder(new String[] { "partDeactivated",
				"partActivated", "partClosed" }));

		fPage.hideView(view);
		clearEventState();
		fPage.showView(MockViewPart.ID3);
		assertTrue(history.isEmpty());
    }

    /**
     * Tests the addPartListener method on IWorkbenchWindow's part service.
     */
    public void testAddPartListenerToWindow() throws Throwable {
		IPartService service = fWindow
				.getService(IPartService.class);
		service.addPartListener(partListener);
		service.addPartListener(partListener2);

        clearEventState();
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        assertTrue(history.verifyOrder(new String[] { "partOpened",
                "partActivated" }));
        assertEquals(view, eventPart);
        assertTrue(history2.verifyOrder(new String[] { "partOpened",
                "partVisible", "partActivated" }));
        assertEquals(getRef(view), eventPartRef);

        clearEventState();
        fPage.hideView(view);
        assertTrue(history.verifyOrder(new String[] { "partDeactivated",
                "partClosed" }));
        assertEquals(view, eventPart);
        assertTrue(history2.verifyOrder(new String[] { "partDeactivated",
                "partHidden", "partClosed" }));
        assertEquals(getRef(view), eventPartRef);

        service.removePartListener(partListener);
        service.removePartListener(partListener2);
    }

    /**
     * Tests the removePartListener method on IWorkbenchPage's part service.
     */
    public void testRemovePartListenerFromPage() throws Throwable {

        fPage.addPartListener(partListener);
        fPage.addPartListener(partListener2);
        fPage.removePartListener(partListener);
        fPage.removePartListener(partListener2);

        clearEventState();
        fPage.showView(MockViewPart.ID);
        assertTrue(history.isEmpty());
        assertTrue(history2.isEmpty());
    }

    /**
     * Tests the removePartListener method on IWorkbenchWindow's part service.
     */
    public void testRemovePartListenerFromWindow() throws Throwable {

		IPartService service = fWindow
				.getService(IPartService.class);
		service.addPartListener(partListener);
		service.addPartListener(partListener2);
		service.removePartListener(partListener);
		service.removePartListener(partListener2);

		clearEventState();
		fPage.showView(MockViewPart.ID);
		assertTrue(history.isEmpty());
		assertTrue(history2.isEmpty());
	}

    /**
	 * Tests the partHidden method by closing a view when it is not shared with
	 * another perspective. Includes regression test for: Bug 60039 [ViewMgmt]
	 * (regression) IWorkbenchPage#findView returns non-null value after part
	 * has been closed
	 */
    public void testPartHiddenWhenClosedAndUnshared() throws Throwable {
        IPartListener2 listener = new TestPartListener2() {
            @Override
			public void partHidden(IWorkbenchPartReference ref) {
                super.partHidden(ref);
                assertEquals(MockViewPart.ID, ref.getId());
                assertNull(fPage.findView(MockViewPart.ID));
            }
        };
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        fPage.addPartListener(listener);
        clearEventState();
        fPage.hideView(view);
        assertTrue(history2.contains("partHidden"));
        assertEquals(getRef(view), eventPartRef);
        fPage.removePartListener(listener);
    }

    /**
     * Tests the partHidden method by closing a view when it is shared with another perspective.
     * Includes regression test for:
     *   Bug 60039 [ViewMgmt] (regression) IWorkbenchPage#findView returns non-null value after part has been closed
     */
    public void XXXtestPartHiddenWhenClosedAndShared() throws Throwable {
        IPartListener2 listener = new TestPartListener2() {
            @Override
			public void partHidden(IWorkbenchPartReference ref) {
                super.partHidden(ref);
                assertEquals(MockViewPart.ID, ref.getId());
                assertNull(fPage.findView(MockViewPart.ID));
            }
        };
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        IPerspectiveDescriptor emptyPerspDesc2 = fWindow.getWorkbench()
                .getPerspectiveRegistry().findPerspectiveWithId(
                        EmptyPerspective.PERSP_ID2);
        fPage.setPerspective(emptyPerspDesc2);
        MockViewPart view2 = (MockViewPart) fPage.showView(MockViewPart.ID);
        assertTrue(view == view2);
        fPage.addPartListener(listener);
        clearEventState();
        fPage.hideView(view);
        assertTrue(history2.contains("partHidden"));
        assertEquals(getRef(view), eventPartRef);
        fPage.removePartListener(listener);
    }

    /**
     * Tests the partHidden method by activating another view in the same folder.
     */
    public void testPartHiddenWhenObscured() throws Throwable {
        final boolean[] eventReceived = { false };
        IPartListener2 listener = new TestPartListener2() {
            @Override
			public void partHidden(IWorkbenchPartReference ref) {
                super.partHidden(ref);
                assertEquals(MockViewPart.ID, ref.getId());
                assertNotNull(fPage.findView(MockViewPart.ID));
                eventReceived[0] = true;
            }
        };
        MockViewPart view2 = (MockViewPart) fPage.showView(MockViewPart.ID2);
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        assertEquals(view, fPage.getActivePart());
        fPage.addPartListener(listener);
        clearEventState();
        fPage.activate(view2);
        fPage.removePartListener(listener);
        assertTrue(eventReceived[0]);
    }

    /**
     * Tests the partVisible method by showing a view when it is not
     * open in any other perspectives.
     */
    public void testPartVisibleWhenOpenedUnshared() throws Throwable {
        final boolean[] eventReceived = { false };
        IPartListener2 listener = new TestPartListener2() {
            @Override
			public void partVisible(IWorkbenchPartReference ref) {
                super.partVisible(ref);
                assertEquals(MockViewPart.ID, ref.getId());
                assertNotNull(fPage.findView(MockViewPart.ID));
                eventReceived[0] = true;
            }
        };
        fPage.addPartListener(listener);
        clearEventState();
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        fPage.removePartListener(listener);
        assertEquals(view, fPage.getActivePart());
        assertTrue(eventReceived[0]);
    }

    /**
     * Tests the partVisible method by showing a view when it is already
     * open in another perspective.
     */
    public void testPartVisibleWhenOpenedShared() throws Throwable {
        final boolean[] eventReceived = { false };
        IPartListener2 listener = new TestPartListener2() {
            @Override
			public void partVisible(IWorkbenchPartReference ref) {
                super.partVisible(ref);
                assertEquals(MockViewPart.ID, ref.getId());
                assertNotNull(fPage.findView(MockViewPart.ID));
                eventReceived[0] = true;
            }
        };
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        IPerspectiveDescriptor emptyPerspDesc2 = fWindow.getWorkbench()
                .getPerspectiveRegistry().findPerspectiveWithId(
                        EmptyPerspective.PERSP_ID2);
        fPage.setPerspective(emptyPerspDesc2);
        fPage.addPartListener(listener);
        clearEventState();
        MockViewPart view2 = (MockViewPart) fPage.showView(MockViewPart.ID);
        assertTrue(view == view2);
        assertEquals(view2, fPage.getActivePart());
        assertTrue(eventReceived[0]);
        fPage.removePartListener(listener);
    }

    /**
     * Tests that both a part hidden and a part closed event are sent when
     * a part is closed
     *
     * @throws Throwable
     */
    public void testPartHiddenBeforeClosing() throws Throwable {

        final boolean[] eventReceived = {false, false};
        IPartListener2 listener = new TestPartListener2() {
            @Override
			public void partHidden(IWorkbenchPartReference ref) {
                super.partHidden(ref);
                assertEquals(MockViewPart.ID, ref.getId());
                assertNull(fPage.findView(MockViewPart.ID));
                eventReceived[0] = true;
                assertFalse(eventReceived[1]);
            }
            @Override
			public void partClosed(IWorkbenchPartReference ref) {
                super.partClosed(ref);
                assertEquals(MockViewPart.ID, ref.getId());
                assertNull(fPage.findView(MockViewPart.ID));
                eventReceived[1] = true;
                assertTrue(eventReceived[0]);

            }
        };
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        assertEquals(view, fPage.getActivePart());
        fPage.addPartListener(listener);
        clearEventState();
        fPage.hideView(view);
        fPage.removePartListener(listener);
        history.verifyOrder(new String[] {"partHidden", "partClosed"});
        assertTrue(eventReceived[0]);
        assertTrue(eventReceived[1]);
    }

    /**
     * Tests the partVisible method by activating a view obscured by
     * another view in the same folder.
     */
    public void testPartVisibleWhenObscured() throws Throwable {
        final boolean[] eventReceived = { false };
        IPartListener2 listener = new TestPartListener2() {
            @Override
			public void partVisible(IWorkbenchPartReference ref) {
                super.partVisible(ref);
                assertEquals(MockViewPart.ID, ref.getId());
                assertNotNull(fPage.findView(MockViewPart.ID));
                eventReceived[0] = true;
            }
        };
        MockViewPart view = (MockViewPart) fPage.showView(MockViewPart.ID);
        MockViewPart view2 = (MockViewPart) fPage.showView(MockViewPart.ID2);
        assertEquals(view2, fPage.getActivePart());
        fPage.addPartListener(listener);
        clearEventState();
        fPage.activate(view);
        fPage.removePartListener(listener);
        assertTrue(eventReceived[0]);
    }


    /**
     * Tests that when a partOpened is received for an editor being opened,
     * the editor is available via findEditor, getEditors, and getEditorReferences.
     *
     * @since 3.1
     */
    public void testEditorFoundWhenOpened() throws Throwable {
    	final String editorId = MockEditorPart.ID1;
		IProject proj = FileUtil.createProject("IPartServiceTest");
		IFile file = FileUtil.createFile("testEditorFoundWhenOpened.txt", proj);
		final IEditorInput editorInput = new FileEditorInput(file);

        final boolean[] eventReceived = { false, false };
        IPartListener listener = new TestPartListener() {
            @Override
			public void partOpened(IWorkbenchPart part) {
                super.partOpened(part);
                assertEquals(editorId, part.getSite().getId());
                assertNotNull(fPage.findEditor(editorInput));
                IEditorPart[] editors = fPage.getEditors();
                assertEquals(1, editors.length);
                assertEquals(editorId, editors[0].getSite().getId());
                eventReceived[0] = true;
            }
        };
        IPartListener2 listener2 = new TestPartListener2() {
            @Override
			public void partOpened(IWorkbenchPartReference ref) {
                super.partOpened(ref);
                assertEquals(editorId, ref.getId());
                IEditorReference[] refs = fPage.getEditorReferences();
                assertEquals(1, refs.length);
                assertEquals(editorId, refs[0].getId());
                eventReceived[1] = true;
            }
        };
        fPage.addPartListener(listener);
        fPage.addPartListener(listener2);
		fPage.openEditor(editorInput, editorId);
        fPage.removePartListener(listener);
        fPage.removePartListener(listener2);
        assertTrue(eventReceived[0]);
        assertTrue(eventReceived[1]);
    }

}
