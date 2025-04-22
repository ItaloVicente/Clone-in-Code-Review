
package org.eclipse.ui.tests.api;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.harness.util.FileUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class IEditorMatchingStrategyTest extends UITestCase {

    private static final String MATCHING_EDITOR_ID = "org.eclipse.ui.tests.api.MockMatchingEditorPart1";

    public IEditorMatchingStrategyTest(String testName) {
        super(testName);
    }

    public void testOpenMatchingEditor() throws Exception {
        IProject proj = FileUtil.createProject("IEditorMatchingStrategyTest");
        IFile file1 = FileUtil.createFile("plugin.xml", proj);
        IFile file2 = FileUtil.createFile("MANIFEST.MF", proj);
        IFile file3 = FileUtil.createFile("build.properties", proj);
        IFile file4 = FileUtil.createFile("something.txt", proj);
        
        IWorkbenchPage page = openTestWindow().getActivePage();
        IEditorPart editor1 = page.openEditor(new FileEditorInput(file1), MATCHING_EDITOR_ID);
        IEditorPart editor1b = page.openEditor(new FileEditorInput(file1), MATCHING_EDITOR_ID);
        IEditorPart editor2 = page.openEditor(new FileEditorInput(file2), MATCHING_EDITOR_ID);
        IEditorPart editor3 = page.openEditor(new FileEditorInput(file3), MATCHING_EDITOR_ID);
        IEditorPart editor4 = page.openEditor(new FileEditorInput(file4), MATCHING_EDITOR_ID);
        assertSame(editor1, editor1b);
        assertSame(editor1, editor2);
        assertSame(editor1, editor3);
        assertNotSame(editor1, editor4);
    }

    public void testFindMatchingEditor() throws Exception {
        IProject proj = FileUtil.createProject("IEditorMatchingStrategyTest");
        IFile file1 = FileUtil.createFile("plugin.xml", proj);
        IFile file2 = FileUtil.createFile("MANIFEST.MF", proj);
        IFile file3 = FileUtil.createFile("build.properties", proj);
        IFile file4 = FileUtil.createFile("something.txt", proj);
        
        IWorkbenchPage page = openTestWindow().getActivePage();
        IEditorPart editor = page.openEditor(new FileEditorInput(file1), MATCHING_EDITOR_ID);
        assertEquals(editor, page.findEditor(new FileEditorInput(file1)));
        assertEquals(editor, page.findEditor(new FileEditorInput(file2)));
        assertEquals(editor, page.findEditor(new FileEditorInput(file3)));
        assertEquals(null, page.findEditor(new FileEditorInput(file4)));
    }
    
}

