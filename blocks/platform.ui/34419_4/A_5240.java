package org.eclipse.ui.tests.zoom;

import org.eclipse.ui.IWorkbenchPart;

public class ZoomedViewCloseTest extends CloseTest {

    public ZoomedViewCloseTest(String name) {
        super(name);
    }

    @Override
	public IWorkbenchPart getStackedPart1() {
        return stackedView1;
    }

    @Override
	public IWorkbenchPart getStackedPart2() {
        return stackedView2;
    }

    @Override
	public IWorkbenchPart getUnstackedPart() {
        return unstackedView;
    }

    public void testCloseActiveEditorWhileViewZoomed() {
        page.activate(editor1);
        zoom(stackedView1);
        close(editor1);

        assertZoomed(stackedView1);
        assertActive(stackedView1);
    }

    public void testCloseZoomedUnstackedViewAfterActivatingView() {
        IWorkbenchPart previousActive = stackedView1;
        IWorkbenchPart zoomedPart = getUnstackedPart();

        page.activate(previousActive);
        zoom(zoomedPart);
        close(zoomedPart);

        assertZoomed(null);
        assertActive(previousActive);
    }

    public void testCloseUnzoomedStackedViewAfterActivatingView() {
        IWorkbenchPart activePart = getStackedPart1();
        IWorkbenchPart unstackedPart = unstackedView;

        page.activate(unstackedPart);
        page.activate(activePart);
        close(activePart);

        assertZoomed(null);
        assertActive(unstackedPart);
    }
}
