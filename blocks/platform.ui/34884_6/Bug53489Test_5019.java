
package org.eclipse.ui.tests.keys;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.eclipse.core.commands.common.CommandException;
import org.eclipse.core.internal.events.BuildCommand;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.keys.BindingService;
import org.eclipse.ui.internal.keys.WorkbenchKeyboard;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug44460Test extends UITestCase {

	public Bug44460Test(String testName) {
		super(testName);
	}

	public void testCtrlShiftT() throws CommandException, CoreException {
		IWorkbenchWindow window = openTestWindow();

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject testProject = workspace.getRoot().getProject(
				"Bug 44460 Project"); //$NON-NLS-1$
		testProject.create(null);
		testProject.open(null);
		IProjectDescription projectDescription = testProject.getDescription();
		String[] natureIds = { "org.eclipse.jdt.core.javanature" }; //$NON-NLS-1$
		projectDescription.setNatureIds(natureIds);
		ICommand buildCommand = new BuildCommand();
		buildCommand.setBuilderName("org.eclipse.jdt.core.javabuilder"); //$NON-NLS-1$
		projectDescription.setBuildSpec(new ICommand[] { buildCommand });
		testProject.setDescription(projectDescription, null);
		IFile javaFile = testProject.getFile("A.java"); //$NON-NLS-1$
		String classContents = "public class Main { public static main(String[] args) { ; } }"; //$NON-NLS-1$
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				classContents.getBytes());
		javaFile.create(inputStream, true, null);
		IDE.openEditor(window.getActivePage(), javaFile, true);

		IPerspectiveRegistry registry = PlatformUI.getWorkbench()
				.getPerspectiveRegistry();
		IPerspectiveDescriptor perspectiveDescriptor = registry
				.findPerspectiveWithId("org.eclipse.team.ui.TeamSynchronizingPerspective"); //$NON-NLS-1$
		WorkbenchPage page = (WorkbenchPage) window.getActivePage();
		page.setPerspective(perspectiveDescriptor);

		Event ctrlShiftT = new Event();
		ctrlShiftT.stateMask = SWT.SHIFT | SWT.CTRL;
		ctrlShiftT.character = 'T';
		ctrlShiftT.keyCode = 't';
		List keyStrokes = WorkbenchKeyboard
				.generatePossibleKeyStrokes(ctrlShiftT);
		Workbench workbench = (Workbench) window.getWorkbench();
		BindingService support = (BindingService) workbench
				.getAdapter(IBindingService.class);
		support.getKeyboard().press(keyStrokes, null);

		Shell windowShell = window.getShell();
		Shell[] childShells = windowShell.getShells();
		assertTrue(
				"Type hierarchy dialog opened inappropriately on 'Ctrl+Shift+T'", (childShells.length == 2)); //$NON-NLS-1$
	}
}
