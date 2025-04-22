package org.eclipse.ui.tests.zoom;

import junit.framework.Assert;


public class OpenEditorTest extends ZoomTestCase {
    public OpenEditorTest(String name) {
        super(name);
    }

    public void testOpenExistingEditorInZoomedStack() {
        zoom(editor1);
        openEditor(file2, false);
        Assert.assertTrue(isZoomed(editor2));
        Assert.assertTrue(page.getActivePart() == editor2);
    }

    public void testOpenNewEditorWhileViewZoomed() {
        close(editor1);
        
        zoom(stackedView1);
        openEditor(file1, false);
        
        assertZoomed(stackedView1);
        assertActive(stackedView1);
    }
    
    public void testOpenNewEditorInZoomedStack() {
        close(editor2);
        
        zoom(editor1);
        openEditor(file2, false);
        Assert.assertTrue(isZoomed(editor2));
        Assert.assertTrue(page.getActivePart() == editor2);
    }
    
    public void testOpenExistingEditorWhileViewZoomed() {
        zoom(stackedView1);
        openEditor(file1, false);
        
        assertZoomed(stackedView1);
        assertActive(stackedView1);
    }

    public void testOpenAndActivateExistingEditorWhileViewZoomed() {
        zoom(stackedView1);
        openEditor(file1, true);

        assertZoomed(null);
        assertActive(editor1);
    }

    public void testOpenAndActivateNewEditorWhileViewZoomed() {
        close(editor1);
        
        zoom(stackedView1);
        openEditor(file1, true);
       
        assertZoomed(null);
        assertActive(editor1);
    }

    public void testOpenAndActivateExistingEditorInZoomedStack() {
        zoom(editor1);
        openEditor(file2, true);

        assertZoomed(editor2);
        assertActive(editor2);
    }

    public void testOpenAndActivateNewEditorInZoomedStack() {
        close(editor2);
        
        zoom(editor1);
        openEditor(file2, true);

        assertZoomed(editor2);
        assertActive(editor2);
    }
    
    public void testOpenExistingEditorInOtherStack() {
        zoom(editor3);
        openEditor(file2, false);
        
        assertZoomed(editor3);
        assertActive(editor3);
    }

    public void testOpenAndActivateExistingEditorInOtherStack() {
        zoom(editor3);
        openEditor(file2, true);

        assertZoomed(null);
        assertActive(editor2);
    }
    
}
