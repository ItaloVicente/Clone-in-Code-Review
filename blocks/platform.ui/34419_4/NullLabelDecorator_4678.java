package org.eclipse.ui.tests.adaptable;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class MarkerImageProviderTest extends UITestCase {

    public MarkerImageProviderTest(String testName) {
        super(testName);
    }

    public void testStatic() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IMarker marker = null;
        try {
            marker = workspace.getRoot().createMarker(
                    "org.eclipse.ui.tests.testmarker"); //$NON-NLS-1$
        } catch (CoreException e) {
            fail(e.getMessage());
        }
        IWorkbenchAdapter adapter = (IWorkbenchAdapter) marker
                .getAdapter(IWorkbenchAdapter.class);
        ImageDescriptor imageDesc = adapter.getImageDescriptor(marker);
        assertNotNull(imageDesc);
        assertTrue(imageDesc.toString().indexOf("anything") != -1); //$NON-NLS-1$
    }

    public void testDynamic() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IMarker marker = null;
        try {
            marker = workspace.getRoot().createMarker(
                    "org.eclipse.ui.tests.testmarker2"); //$NON-NLS-1$
        } catch (CoreException e) {
            fail(e.getMessage());
        }
        IWorkbenchAdapter adapter = (IWorkbenchAdapter) marker
                .getAdapter(IWorkbenchAdapter.class);
        ImageDescriptor imageDesc = adapter.getImageDescriptor(marker);
        assertNotNull(imageDesc);
        assertTrue(imageDesc.toString().indexOf("anything") != -1); //$NON-NLS-1$
    }

}
