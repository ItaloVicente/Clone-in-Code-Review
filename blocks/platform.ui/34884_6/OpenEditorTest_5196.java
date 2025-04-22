package org.eclipse.ui.tests.zoom;

import org.eclipse.ui.IWorkbenchPart;

public abstract class CloseTest extends ZoomTestCase {
    public CloseTest(String name) {
        super(name);
    }
    
    public abstract IWorkbenchPart getStackedPart1();
    public abstract IWorkbenchPart getStackedPart2();
    public abstract IWorkbenchPart getUnstackedPart();
    
    public void testCloseInactiveFastView() {
        IWorkbenchPart zoomPart = getStackedPart1();
        
        zoom(zoomPart);
        close(fastView);
        
        assertZoomed(zoomPart);
        assertActive(zoomPart);
    }
    
    public void testCloseActiveFastView() {
        IWorkbenchPart zoomPart = getStackedPart1();
        
        zoom(zoomPart);
        page.activate(fastView);
        close(fastView);
        
        assertZoomed(zoomPart);
        assertActive(zoomPart);
    }
    
    public void testCloseZoomedStackedPartAfterActivatingView() {
        IWorkbenchPart zoomPart = getStackedPart1();
        IWorkbenchPart otherStackedPart = getStackedPart2();
        IWorkbenchPart unstackedPart = unstackedView;
        
        page.activate(unstackedPart);
        zoom(zoomPart);
        close(zoomPart);
                
        assertZoomed(otherStackedPart);
        assertActive(otherStackedPart);
    }

    public void testCloseZoomedStackedPartAfterActivatingEditor() {
        IWorkbenchPart zoomPart = getStackedPart1();
        IWorkbenchPart otherStackedPart = getStackedPart2();
        IWorkbenchPart unstackedPart = editor3;
        
        page.activate(unstackedPart);
        zoom(zoomPart);
        close(zoomPart);
        
        assertZoomed(otherStackedPart);
        assertActive(otherStackedPart);
    }

    public void testCloseUnzoomedStackedPartAfterActivatingEditor() {
        IWorkbenchPart activePart = getStackedPart1();
        IWorkbenchPart unstackedPart = editor3;
        
        page.activate(unstackedPart);
        page.activate(activePart);
        close(activePart);
        
        assertZoomed(null);
        assertActive(unstackedPart);
    }
        
    public void testCloseZoomedUnstackedPartAfterActivatingEditor() {
        IWorkbenchPart previousActive = editor1;
        IWorkbenchPart zoomedPart = getUnstackedPart();
        
        page.activate(previousActive);
        zoom(zoomedPart);
        close(zoomedPart);

        assertZoomed(null);
        assertActive(previousActive);
    }

    public void testCloseHiddenUnstackedEditor() {
        IWorkbenchPart zoomedPart = getStackedPart1();
        
        page.activate(editor1);
        zoom(zoomedPart);
        close(editor3);

        assertZoomed(zoomedPart);
        assertActive(zoomedPart);
    }
    
    public void testCloseHiddenUnstackedView() {
        IWorkbenchPart zoomedPart = getStackedPart1();
        
        zoom(zoomedPart);
        close(unstackedView);
        
        assertZoomed(zoomedPart);
        assertActive(zoomedPart);
    }
    
}
