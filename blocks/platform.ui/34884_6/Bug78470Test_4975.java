
package org.eclipse.ui.tests.internal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug41931Test extends UITestCase {

    public Bug41931Test(String testName) {
        super(testName);
    }

    public void testBringToTop() throws CoreException {
        IWorkbenchWindow window = openTestWindow();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();

        IProject testProject = workspace.getRoot().getProject("Bug41931"); //$NON-NLS-1$
        testProject.create(null);
        testProject.open(null);

        InputStream contents = new ByteArrayInputStream(new byte[0]);
        IFile fileA = testProject.getFile("a.txt"); //$NON-NLS-1$
        fileA.create(contents, true, null);
        IFile fileB = testProject.getFile("b.txt"); //$NON-NLS-1$
        fileB.create(contents, true, null);
        IFile fileC = testProject.getFile("c.txt"); //$NON-NLS-1$
        fileC.create(contents, true, null);

        WorkbenchPage page = (WorkbenchPage) window.getActivePage();
        IEditorPart editorA = IDE.openEditor(page, fileA, true);
        IEditorPart editorB = IDE.openEditor(page, fileB, true);
        IEditorPart editorC = IDE.openEditor(page, fileC, true);

        IEditorPart[] expectedResults = { editorA, editorB, editorC };
        IWorkbenchPartReference[] actualResults = page.getSortedParts();
        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(
                    "Pre-test order is not correct.", expectedResults[i].getTitle(), actualResults[i].getPart(false).getTitle()); //$NON-NLS-1$
        }

        page.bringToTop(editorB);

        expectedResults = new IEditorPart[] { editorA, editorC, editorB };
        actualResults = page.getSortedParts();
        for (int i = 0; i < expectedResults.length; i++) {
            assertEquals(
                    "bringToTop() does not change sorted part order.", expectedResults[i].getTitle(), actualResults[i].getPart(false).getTitle()); //$NON-NLS-1$
        }
    }
}
