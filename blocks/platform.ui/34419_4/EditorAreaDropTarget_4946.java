package org.eclipse.ui.tests.dnd;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.internal.dnd.TestDropLocation;
import org.eclipse.ui.tests.TestPlugin;
import org.eclipse.ui.tests.autotests.AutoTestSuite;

public class DragTestSuite extends AutoTestSuite {

    public static Test suite() {
        return new DragTestSuite();
    }

	private static final boolean isDetachingSupported;

	static {
		Shell shell = new Shell();
		Composite c = new Composite(shell, SWT.NONE);
		isDetachingSupported  = c.isReparentable();
		shell.dispose();
	}

    public DragTestSuite() {
        super(Platform.find(TestPlugin.getDefault().getBundle(), new Path("data/dragtests.xml")));

        String resNav = IPageLayout.ID_RES_NAV;
        String probView = IPageLayout.ID_PROBLEM_VIEW;

        TestDragSource[] viewDragSources = new TestDragSource[] {
                new ViewDragSource(resNav, false),
                new ViewDragSource(resNav, true),
                new ViewDragSource(probView, false),
                new ViewDragSource(probView, true) };

        TestDragSource[] editorDragSources = new TestDragSource[] {
                new EditorDragSource(0, false), new EditorDragSource(0, true),
                new EditorDragSource(2, false), new EditorDragSource(2, true) };


        TestDragSource[] maximizedViewDragSources = new TestDragSource[] {
                new ViewDragSource(resNav, false, true),
                new ViewDragSource(resNav, true, true),
                new ViewDragSource(probView, false, true),
                new ViewDragSource(probView, true, true) };

        for (TestDragSource source : maximizedViewDragSources) {
            addAllCombinations(source, getMaximizedViewDropTargets(source));
        }

        for (TestDragSource source : viewDragSources) {
            addAllCombinations(source, getViewDropTargets(source));
            addAllCombinations(source, getCommonDropTargets(source));

            addAllCombinationsDetached(source, getDetachedWindowDropTargets(source));
        }

        for (TestDragSource source : editorDragSources) {
            addAllCombinations(source, getEditorDropTargets(source));
            addAllCombinations(source, getCommonDropTargets(source));

            addAllCombinationsDetached(source, getDetachedWindowDropTargets(source));
        }
        addTest(new TestSuite(Bug87211Test.class));
    }

    private TestDropLocation[] getMaximizedViewDropTargets(IWorkbenchWindowProvider originatingWindow) {
        return new TestDropLocation[] {
                new EditorAreaDropTarget(originatingWindow, SWT.RIGHT) };
    }

    private TestDropLocation[] getCommonDropTargets(IWorkbenchWindowProvider dragSource) {
        TestDropLocation[] targets = {
            new WindowDropTarget(dragSource, SWT.TOP),
            new WindowDropTarget(dragSource, SWT.BOTTOM),
            new WindowDropTarget(dragSource, SWT.LEFT),
            new WindowDropTarget(dragSource, SWT.RIGHT) };

		return targets;
    }

    private TestDropLocation[] getViewDropTargets(IWorkbenchWindowProvider dragSource) {

        String resNav = IPageLayout.ID_RES_NAV;
        String probView = IPageLayout.ID_PROBLEM_VIEW;

        TestDropLocation[] targets = new TestDropLocation[] {
            new EditorAreaDropTarget(dragSource, SWT.LEFT),
            new EditorAreaDropTarget(dragSource, SWT.RIGHT),
            new EditorAreaDropTarget(dragSource, SWT.TOP),
            new EditorAreaDropTarget(dragSource, SWT.BOTTOM),

            new ViewDropTarget(dragSource, resNav, SWT.LEFT),
            new ViewDropTarget(dragSource, resNav, SWT.RIGHT),
            new ViewDropTarget(dragSource, resNav, SWT.BOTTOM),
            new ViewDropTarget(dragSource, resNav, SWT.CENTER),
            new ViewDropTarget(dragSource, resNav, SWT.TOP),

            new ViewDropTarget(dragSource, probView, SWT.LEFT),
            new ViewDropTarget(dragSource, probView, SWT.RIGHT),
            new ViewDropTarget(dragSource, probView, SWT.BOTTOM),
            new ViewDropTarget(dragSource, probView, SWT.CENTER),
            new ViewDropTarget(dragSource, probView, SWT.TOP),

            null, //new FastViewBarDropTarget(dragSource),

            new ViewTabDropTarget(dragSource, resNav),
            new ViewTabDropTarget(dragSource, probView),
            new ViewTitleDropTarget(dragSource, probView),
            };

		return targets;
    }

    private TestDropLocation[] getDetachedWindowDropTargets(IWorkbenchWindowProvider dragSource) {
        TestDropLocation[] targets = new TestDropLocation[] {
            new ViewDropTarget(dragSource, DragDropPerspectiveFactory.dropViewId1, SWT.CENTER),
            new ViewDropTarget(dragSource, DragDropPerspectiveFactory.dropViewId3, SWT.CENTER),
            new ViewTabDropTarget(dragSource, DragDropPerspectiveFactory.dropViewId1),
            new DetachedDropTarget()
        };

		return targets;
    }

    private TestDropLocation[] getEditorDropTargets(IWorkbenchWindowProvider originatingWindow) {
        String resNav = IPageLayout.ID_RES_NAV;
        return new TestDropLocation[] {
                new ViewDropTarget(originatingWindow, resNav, SWT.CENTER),

                new EditorDropTarget(originatingWindow, 2, SWT.LEFT),
                new EditorDropTarget(originatingWindow, 2, SWT.RIGHT),
                new EditorDropTarget(originatingWindow, 2, SWT.TOP),
                new EditorDropTarget(originatingWindow, 2, SWT.BOTTOM),
                new EditorDropTarget(originatingWindow, 2, SWT.CENTER),

                new EditorDropTarget(originatingWindow, 0, SWT.LEFT),
                new EditorDropTarget(originatingWindow, 0, SWT.RIGHT),
                new EditorDropTarget(originatingWindow, 0, SWT.BOTTOM),
                new EditorDropTarget(originatingWindow, 0, SWT.CENTER),
                new EditorTabDropTarget(originatingWindow, 0),
                new EditorTitleDropTarget(originatingWindow, 0),
                };
    }

    private void addAllCombinations(TestDragSource dragSource,
            TestDropLocation[] dropTargets) {

        for (TestDropLocation dropTarget : dropTargets) {
        	if (dropTarget == null) {
				continue;
			}

            DragTest newTest = new DragTest(dragSource, dropTarget, getLog());
            addTest(newTest);
        }
    }

    private void addAllCombinationsDetached(TestDragSource dragSource,
            TestDropLocation[] dropTargets) {

    	if (isDetachingSupported) {
	        for (TestDropLocation dropTarget : dropTargets) {
	            DragTest newTest = new DetachedWindowDragTest(dragSource, dropTarget, getLog());
	            addTest(newTest);
	        }
    	}
    }

}
