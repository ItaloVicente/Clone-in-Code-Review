
package org.eclipse.ui.tests.keys;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.common.CommandException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.keys.BindingService;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public class Bug43321Test extends UITestCase {

	public Bug43321Test(String name) {
		super(name);
	}

	public void testNoCheckOnNonCheckbox() throws CommandException,
			CoreException, ParseException {
		IWorkbenchWindow window = openTestWindow();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject testProject = workspace.getRoot().getProject("TestProject"); //$NON-NLS-1$
		testProject.create(null);
		testProject.open(null);
		IFile textFile = testProject.getFile("A.txt"); //$NON-NLS-1$
		String contents = "A blurb"; //$NON-NLS-1$
		ByteArrayInputStream inputStream = new ByteArrayInputStream(contents
				.getBytes());
		textFile.create(inputStream, true, null);
		AbstractTextEditor editor = (AbstractTextEditor) IDE.openEditor(window
				.getActivePage(), textFile, true);
		editor.selectAndReveal(0, 1);

		List keyStrokes = new ArrayList();
		keyStrokes.add(KeyStroke.getInstance("CTRL+C")); //$NON-NLS-1$
		Event event = new Event();
		Workbench workbench = ((Workbench) window.getWorkbench());
		BindingService support = (BindingService) workbench
				.getAdapter(IBindingService.class);
		support.getKeyboard().press(keyStrokes, event);

		IAction action = editor.getEditorSite().getActionBars()
				.getGlobalActionHandler(ActionFactory.COPY.getId());
		assertTrue("Non-checkbox menu item is checked.", !action.isChecked()); //$NON-NLS-1$
	}
}
