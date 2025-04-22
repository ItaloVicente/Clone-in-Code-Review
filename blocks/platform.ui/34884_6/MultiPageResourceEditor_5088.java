package org.eclipse.ui.tests.multipageeditor;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandManager;
import org.eclipse.ui.commands.IWorkbenchCommandSupport;
import org.eclipse.ui.keys.KeySequence;
import org.eclipse.ui.keys.ParseException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class MultiPageKeyBindingTest extends UITestCase {

    public MultiPageKeyBindingTest(String name) {
        super(name);
    }

    public void testSwitch() throws CoreException, ParseException {
        final String extension = "multi"; //$NON-NLS-1$
        final String fileName = "A." + extension; //$NON-NLS-1$

        IWorkbenchWindow window = openTestWindow();

        IWorkbenchPage page = window.getActivePage();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProject testProject = workspace.getRoot().getProject(
                "MultiPageKeyBindingTest Project"); //$NON-NLS-1$
        testProject.create(null);
        testProject.open(null);
        IFile multiFile = testProject.getFile(fileName);
        multiFile.create(new ByteArrayInputStream(new byte[0]), true, null);

        IEditorInput editorInput = new FileEditorInput(multiFile);
        IEditorPart editorPart = page.openEditor(editorInput,
                "org.eclipse.ui.tests.multipageeditor.TestMultiPageEditor"); //$NON-NLS-1$
        TestMultiPageEditor multiPageEditorPart = (TestMultiPageEditor) editorPart;

        window.getShell().forceActive();
        Display display = Display.getCurrent();
        while (display.readAndDispatch())
            ;
        multiPageEditorPart.setPage(1);

        IWorkbenchCommandSupport commandSupport = window.getWorkbench()
                .getCommandSupport();
        ICommandManager commandManager = commandSupport.getCommandManager();
        KeySequence expectedKeyBinding = KeySequence
                .getInstance("Ctrl+Shift+5"); //$NON-NLS-1$
        String commandId = commandManager.getPerfectMatch(expectedKeyBinding);
        assertEquals("org.eclipse.ui.tests.TestCommandId", commandId); //$NON-NLS-1$
    }
}
