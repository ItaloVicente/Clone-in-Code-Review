
package org.eclipse.ui.tests.keys;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.CommandException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.tests.harness.util.AutomationUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug53489Test extends UITestCase {

    public Bug53489Test(String name) {
        super(name);
    }

    public void testDoubleDelete() throws CommandException,
            CoreException, IOException {
        IWorkbenchWindow window = openTestWindow();
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProject testProject = workspace.getRoot().getProject(
                "DoubleDeleteestProject"); //$NON-NLS-1$
        testProject.create(null);
        testProject.open(null);
        IFile textFile = testProject.getFile("A.txt"); //$NON-NLS-1$
        String originalContents = "A blurb"; //$NON-NLS-1$
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                originalContents.getBytes());
        textFile.create(inputStream, true, null);
        IDE.openEditor(window.getActivePage(), textFile,
                true);

        Display display = Display.getCurrent();
        while (display.readAndDispatch()) {
			;
		}

        AutomationUtil.performKeyCodeEvent(display, SWT.KeyDown, SWT.DEL);
        AutomationUtil.performKeyCodeEvent(display, SWT.KeyUp, SWT.DEL);
        AutomationUtil.performKeyCodeEvent(display, SWT.KeyDown, SWT.CTRL);
        AutomationUtil.performCharacterEvent(display, SWT.KeyDown,'S');
        AutomationUtil.performCharacterEvent(display, SWT.KeyUp,'S');
        AutomationUtil.performKeyCodeEvent(display, SWT.KeyUp, SWT.CTRL);

        while (display.readAndDispatch()) {
			;
		}

        LineNumberReader reader = new LineNumberReader(new InputStreamReader(
                textFile.getContents()));
        String currentContents = reader.readLine();
        assertTrue("'DEL' deleted more than one key.", (originalContents //$NON-NLS-1$
                .length() == (currentContents.length() + 1)));
    }
}
