package org.eclipse.ui.tests.zoom;

import junit.framework.Assert;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;

public class ShowViewTest extends ZoomTestCase {
    public ShowViewTest(String name) {
        super(name);
    }

    
    public void testCreateViewAndBringToTop() {
        zoom(stackedView1);
        IViewPart newPart = showRegularView(ZoomPerspectiveFactory.STACK1_PLACEHOLDER1, 
                IWorkbenchPage.VIEW_CREATE);
        
        page.bringToTop(newPart);
        
        Assert.assertTrue(page.getActivePart() == newPart);
        Assert.assertTrue(isZoomed(newPart));
    }

    public void testCreateViewAndBringToTopInOtherStack() {
        zoom(unstackedView);
        IViewPart newPart = showRegularView(ZoomPerspectiveFactory.STACK1_PLACEHOLDER1, IWorkbenchPage.VIEW_CREATE);
        page.bringToTop(newPart);
        Assert.assertTrue(page.getActivePart() == unstackedView);
        
        Assert.assertTrue(isZoomed(unstackedView));
        
        MUIElement partParent = getPartParent(unstackedView);
        assertTrue(partParent instanceof MPartStack);
        
        MPartStack stack = (MPartStack) partParent;
        Assert.assertTrue(stack.getSelectedElement() == getPartModel(unstackedView));
    }
    
    public void testCreateViewAndMakeVisibleInOtherStack() {
        zoom(unstackedView);
        IViewPart newPart = showRegularView(ZoomPerspectiveFactory.STACK1_PLACEHOLDER1, IWorkbenchPage.VIEW_VISIBLE);
        Assert.assertTrue(page.getActivePart() == unstackedView);
        
        Assert.assertTrue(isZoomed(unstackedView));
        
        MUIElement partParent = getPartParent(newPart);
        assertTrue(partParent instanceof MPartStack);
        
        MPartStack stack = (MPartStack) partParent;
        Assert.assertTrue(stack.getSelectedElement() == getPartModel(newPart));
    }
    public void testCreateViewAndMakeVisibleWhileEditorZoomed() {
        zoom(editor1);
        IViewPart newPart = showRegularView(ZoomPerspectiveFactory.STACK1_PLACEHOLDER1, IWorkbenchPage.VIEW_VISIBLE);
        Assert.assertTrue(isZoomed());
        Assert.assertTrue(page.getActivePart() == editor1);
        
        MUIElement partParent = getPartParent(newPart);
        assertTrue(partParent instanceof MPartStack);
        
        MPartStack stack = (MPartStack) partParent;
        Assert.assertTrue(stack.getSelectedElement() == getPartModel(newPart));
    }
        
    public void testCreateViewAndActivateInZoomedStack() {
        zoom(stackedView1);
        IViewPart newPart = showRegularView(ZoomPerspectiveFactory.STACK1_PLACEHOLDER1, IWorkbenchPage.VIEW_ACTIVATE);
        
        assertZoomed(newPart);
        assertActive(newPart);
    }
    
    public void testCreateViewInZoomedStack() {
        zoom(stackedView1);
        showRegularView(ZoomPerspectiveFactory.STACK1_PLACEHOLDER1, 
                IWorkbenchPage.VIEW_CREATE);
        
        assertZoomed(stackedView1);
        assertActive(stackedView1);
    }

    public void testCreateViewAndActivateInOtherStack() {
        zoom(unstackedView);
        IViewPart newPart = showRegularView(ZoomPerspectiveFactory.STACK1_PLACEHOLDER1, IWorkbenchPage.VIEW_ACTIVATE);
        
        assertZoomed(null);
        assertActive(newPart);
    }
    
    public void testCreateViewInOtherStack() {
        zoom(unstackedView);
        showRegularView(ZoomPerspectiveFactory.STACK1_PLACEHOLDER1, IWorkbenchPage.VIEW_CREATE);

        assertZoomed(unstackedView);
        assertActive(unstackedView);
    }
    
    public void testCreateViewAndActivateWhileEditorZoomed() {
        zoom(editor1);
        IViewPart newPart = showRegularView(ZoomPerspectiveFactory.STACK1_PLACEHOLDER1, IWorkbenchPage.VIEW_ACTIVATE);
        
        assertZoomed(null);
        assertActive(newPart);
    }

    public void testCreateViewWhileEditorZoomed() {
        zoom(editor1);
        showRegularView(ZoomPerspectiveFactory.STACK1_PLACEHOLDER1, IWorkbenchPage.VIEW_CREATE);
        
        assertZoomed(editor1);
        assertActive(editor1);
    }
    
}
