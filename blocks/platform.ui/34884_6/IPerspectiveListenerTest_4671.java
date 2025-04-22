package org.eclipse.ui.tests.api;

import junit.framework.TestCase;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PlatformUI;

public class IPerspectiveDescriptorTest extends TestCase {

    private IPerspectiveDescriptor[] fPerspectives;

    public IPerspectiveDescriptorTest(String testName) {
        super(testName);
    }

    @Override
	public void setUp() {
        fPerspectives = PlatformUI
                .getWorkbench().getPerspectiveRegistry().getPerspectives();
    }

    public void testGetId() {
        for (int i = 0; i < fPerspectives.length; i++) {
            String id = fPerspectives[i].getId();
            assertNotNull(id);
            assertTrue(id.length() > 0);
        }
    }

    public void testGetLabel() {
        for (int i = 0; i < fPerspectives.length; i++) {
            String label = fPerspectives[i].getLabel();
            assertNotNull(label);
            assertTrue(label.length() > 0);
        }
    }

    public void testGetImageDescriptor() {
        for (int i = 0; i < fPerspectives.length; i++) {
            ImageDescriptor image = fPerspectives[i].getImageDescriptor();
            assertNotNull(image);
        }
    }
    
}

