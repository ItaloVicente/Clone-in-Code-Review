package org.eclipse.ui.tests.zoom;

import org.eclipse.ui.IWorkbenchPart;

public class ZoomedViewActivateTest extends ActivateTest {

    public ZoomedViewActivateTest(String name) {
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

    public void testActivateEditor() {
    	System.out.println("Bogus Test: " + getName());

    }
}
