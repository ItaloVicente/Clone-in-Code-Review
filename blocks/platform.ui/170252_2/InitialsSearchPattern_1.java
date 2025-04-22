package org.eclipse.ui.internal.ide.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.dialogs.SelectProjectDialog;
import org.eclipse.ui.part.ISetSelectionTarget;

public class SelectProjectHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Preconditions preconditions = new Preconditions();
		if (!preconditions.fulfilled) {
			throw new ExecutionException(preconditions.issue);
		}

		SelectProjectDialog dialog = new SelectProjectDialog(preconditions.shell,
				Arrays.asList(preconditions.workspaceRoot.getProjects()));
		if(dialog.open() == Window.OK && hasSelectableResult(dialog)) {
			select(dialog.getResult(), preconditions.activePage);
		}

		return null;
	}

	private boolean hasSelectableResult(SelectProjectDialog dialog) {
		Object[] result = dialog.getResult();
		return result != null && result.length > 0;
	}

	private void select(Object[] projects, IWorkbenchPage activePage) {
		List<IWorkbenchPart> parts = new ArrayList<>();
		for (IWorkbenchPartReference ref : activePage.getViewReferences()) {
			IWorkbenchPart part = ref.getPart(false);
			if (part != null) {
				parts.add(part);
			}
		}
		for (IWorkbenchPartReference ref : activePage.getEditorReferences()) {
			if (ref.getPart(false) != null) {
				parts.add(ref.getPart(false));
			}
		}

		LinkedList<ISetSelectionTarget> selectionTargets = new LinkedList<>();
		for (IWorkbenchPart part : parts) {
			ISetSelectionTarget selectionTarget = Adapters.adapt(part, ISetSelectionTarget.class);
			if (selectionTarget != null) {
				selectionTargets.add(selectionTarget);
			}
		}

		if (!selectionTargets.isEmpty()) {
			StructuredSelection selection = new StructuredSelection(projects);
			for (ISetSelectionTarget selectionTarget : selectionTargets) {
				selectionTarget.selectReveal(selection);
			}
		}
	}

	private class Preconditions {
		private String issue;
		private boolean fulfilled;

		private IWorkspaceRoot workspaceRoot;
		private IWorkbenchPage activePage;
		private Shell shell;

		private Preconditions() {
			IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if (workbenchWindow == null) {
				issue = "no active workbench window"; //$NON-NLS-1$
				return;
			}

			activePage = workbenchWindow.getActivePage();
			if (activePage == null) {
				issue = "no active workbench page"; //$NON-NLS-1$
				return;
			}

			shell = workbenchWindow.getShell();
			if (shell == null) {
				issue = "no shell found"; //$NON-NLS-1$
				return;
			}

			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			if (workspace == null) {
				issue = "no shell found"; //$NON-NLS-1$
				return;
			}

			workspaceRoot = workspace.getRoot();
			if (workspaceRoot == null) {
				issue = "no workspace root found"; //$NON-NLS-1$
				return;
			}

			fulfilled = true;
		}
	}
}
