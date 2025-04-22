package org.eclipse.ui.tests.api;

import junit.framework.TestCase;

import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.harness.util.ArrayUtil;

public class IFileEditorMappingTest extends TestCase {
    private IFileEditorMapping[] fMappings;

    public IFileEditorMappingTest(String testName) {
        super(testName);
    }

    @Override
	public void setUp() {
        fMappings = PlatformUI.getWorkbench().getEditorRegistry()
                .getFileEditorMappings();
    }

    public void testGetName() throws Throwable {
        for (IFileEditorMapping fMapping : fMappings) {
			assertNotNull(fMapping.getName());
		}
    }

    public void testGetLabel() throws Throwable {
        String label;
        for (IFileEditorMapping fMapping : fMappings) {
            label = fMapping.getLabel();
            assertNotNull(label);
            assertEquals(label, fMapping.getName() + "."
                    + fMapping.getExtension());
        }
    }

    public void testGetExtension() throws Throwable {
        for (IFileEditorMapping fMapping : fMappings) {
			assertNotNull(fMapping.getExtension());
		}
    }

    public void testGetEditors() throws Throwable {
        IEditorDescriptor[] editors;

        for (IFileEditorMapping fMapping : fMappings) {
            editors = fMapping.getEditors();
            assertTrue(ArrayUtil.checkNotNull(editors) == true);
        }
    }

    public void testGetImageDescriptor() throws Throwable {
        for (IFileEditorMapping fMapping : fMappings) {
			assertNotNull(fMapping.getImageDescriptor());
		}
    }

    public void testGetDefaultEditor() throws Throwable {
         assertNotNull( fMappings[ i ].getDefaultEditor() );*/
    }
}
