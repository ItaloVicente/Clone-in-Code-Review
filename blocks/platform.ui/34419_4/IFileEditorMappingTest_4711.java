package org.eclipse.ui.tests.api;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.tests.harness.util.FileUtil;

public class IEditorSiteTest extends IWorkbenchPartSiteTest {

    public IEditorSiteTest(String testName) {
        super(testName);
    }

    @Override
	protected String getTestPartName() throws Throwable {
        return MockEditorPart.NAME;
    }

    @Override
	protected String getTestPartId() throws Throwable {
        return MockEditorPart.ID1;
    }

    @Override
	protected IWorkbenchPart createTestPart(IWorkbenchPage page)
            throws Throwable {
        IProject proj = FileUtil.createProject("createTestPart");
        IFile file = FileUtil.createFile("test1.mock1", proj);
        return IDE.openEditor(page, file, true);
    }

    public void testGetActionBarContributor() throws Throwable {

        IEditorPart editor = (IEditorPart) createTestPart(fPage);
        IEditorSite site = editor.getEditorSite();
        assertNull(site.getActionBarContributor());

    }

}

