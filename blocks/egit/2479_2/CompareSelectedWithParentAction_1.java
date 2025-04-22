package org.eclipse.egit.ui.internal.actions;

import java.io.IOException;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ITypedElement;
import org.eclipse.egit.core.GitFileRevisionReference;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.CompareUtils;
import org.eclipse.egit.ui.internal.GitCompareFileRevisionEditorInput;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class CompareSelectedWithHeadAction implements IObjectActionDelegate {

	private GitFileRevisionReference fileRevision;

	public void run(IAction action) {
		if (fileRevision == null)
			return;

		final Repository repository = fileRevision.getRepository();

		ITypedElement selectedFileRevision;
		selectedFileRevision = CompareUtils.getFileRevisionTypedElement(fileRevision.getPath(),
				fileRevision.getRevCommit(), repository);

		ITypedElement headFileRevision;
		try {
			Ref head = repository.getRef(Constants.HEAD);
			RevWalk rw = new RevWalk(repository);
			RevCommit commit = rw.parseCommit(head.getObjectId());

			headFileRevision = CompareUtils.getFileRevisionTypedElement(fileRevision.getPath(), commit, repository);
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
			return;
		}

		final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
				selectedFileRevision, headFileRevision, null);
		CompareUI.openCompareEditor(in);
		return;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object element = ss.getFirstElement();
			if (element instanceof GitFileRevisionReference) {
				this.fileRevision = (GitFileRevisionReference) element;
				action.setEnabled(true);
				return;
			}
		}
		fileRevision = null;
		action.setEnabled(false);
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
