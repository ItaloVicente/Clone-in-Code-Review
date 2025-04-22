package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.GitFileRevisionReference;
import org.eclipse.egit.core.internal.storage.CommitFileRevision;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.EgitUiEditorUtils;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class OpenFileAtRevisionAction implements IObjectActionDelegate {

	private CommitFileRevision commitFileRevision = null;

	public void run(IAction action) {
		if (commitFileRevision == null)
			return;

		IWorkbenchWindow window = getWorkbenchWindow();
		if (window == null)
			return;
		IWorkbenchPage page = window.getActivePage();
		if (page == null)
			return;

		try {
			EgitUiEditorUtils.openEditor(page, commitFileRevision, new NullProgressMonitor());
		} catch (Exception e) {
			Activator.handleError(e.getMessage(), e, true);
			return;
		}
	}

	private IWorkbenchWindow getWorkbenchWindow() {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		return window;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object element = ss.getFirstElement();
			if (element instanceof GitFileRevisionReference) {
				prepareCommitFileRevision((GitFileRevisionReference) element);

				if (commitFileRevision != null) {
					action.setEnabled(true);
					return;
				}
			}
		}
		commitFileRevision = null;
		action.setEnabled(false);
	}

	private void prepareCommitFileRevision(GitFileRevisionReference selection) {
		Repository repository = selection.getRepository();

		try {
			this.commitFileRevision = new CommitFileRevision(repository, selection.getRevCommit(),
					selection.getPath());

			commitFileRevision.getStorage(new NullProgressMonitor());
		} catch (CoreException e) {
			this.commitFileRevision = null;
		}
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
