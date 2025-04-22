package org.eclipse.ui.tests.zoom;

import org.eclipse.ui.IWorkbenchPart;

public class ZoomedEditorCloseTest extends CloseTest {

    public ZoomedEditorCloseTest(String name) {
        super(name);
    }

    @Override
	public IWorkbenchPart getStackedPart1() {
        return editor1;
    }

    @Override
	public IWorkbenchPart getStackedPart2() {
        return editor2;
    }

    @Override
	public IWorkbenchPart getUnstackedPart() {
        return editor3;
    }
    
    public void testCloseZoomedUnstackedEditorAfterActivatingView() {
    	System.out.println("Bogus test: we don't unsoom in this case");
    }
    
    public void testCloseUnzoomedStackedEditorAfterActivatingView() {
        page.activate(editor3);
        page.activate(unstackedView);
        page.activate(editor1);
        close(editor1);
        
        assertZoomed(null);
        assertActive(editor3);
    }
}
