package org.eclipse.ui.tests.zoom;

import org.eclipse.ui.IWorkbenchPart;

public abstract class ActivateTest extends ZoomTestCase {
    public ActivateTest(String name) {
        super(name);
    }
    
    public abstract IWorkbenchPart getStackedPart1();
    public abstract IWorkbenchPart getStackedPart2();
    public abstract IWorkbenchPart getUnstackedPart();
    
    public void testZoomAndActivate() {
        IWorkbenchPart stacked1 = getStackedPart1();
        
        zoom(stacked1);
        page.activate(stacked1);
        
        assertZoomed(stacked1);
        assertActive(stacked1);
    }
    
    public void testActivateSameStack() {
        IWorkbenchPart stacked1 = getStackedPart1();
        IWorkbenchPart stacked2 = getStackedPart2();
        
        zoom(stacked1);
        
        page.activate(stacked2);
        
        assertZoomed(stacked2);
        assertActive(stacked2);        
    }
    
    public void testActivateOtherStack() {
    	System.out.println("Bogus Test: " + getName());
    }
    
    public void testResetPerspective() {
        IWorkbenchPart zoomedPart = getStackedPart1();
        
        zoom(zoomedPart);
        
        page.resetPerspective();
        
        assertZoomed(null);
        assertActive(zoomedPart);
    }

}
