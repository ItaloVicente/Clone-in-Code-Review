package org.eclipse.ui.tests.api;

import junit.framework.TestCase;

import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.harness.util.ArrayUtil;

public class IEditorDescriptorTest extends TestCase {
    IEditorDescriptor[] fEditors;

    public IEditorDescriptorTest(String testName) {
        super(testName);
    }

    @Override
	public void setUp() {
        IFileEditorMapping mapping = (IFileEditorMapping) ArrayUtil
                .pickRandom(PlatformUI.getWorkbench().getEditorRegistry()
                        .getFileEditorMappings());
        fEditors = mapping.getEditors();
    }

    public void testGetId() throws Throwable {
        for (IEditorDescriptor fEditor : fEditors) {
			assertNotNull(fEditor.getId());
		}
    }

    public void testGetImageDescriptor() throws Throwable {
        for (IEditorDescriptor fEditor : fEditors) {
			assertNotNull(fEditor.getImageDescriptor());
		}
    }

    public void testGetLabel() throws Throwable {
        for (IEditorDescriptor fEditor : fEditors) {
			assertNotNull(fEditor.getLabel());
		}
    }
}
