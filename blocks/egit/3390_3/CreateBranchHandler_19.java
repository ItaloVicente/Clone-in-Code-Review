package org.eclipse.egit.ui.internal.commit.command;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class CommitCommandHandler extends AbstractHandler {

	protected List<RepositoryCommit> getCommits(ExecutionEvent event) {
		List<RepositoryCommit> commits = new LinkedList<RepositoryCommit>();
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection == null || selection.isEmpty())
			selection = HandlerUtil.getActiveMenuSelection(event);
		if (selection == null || selection.isEmpty()) {
			IEditorPart editor = HandlerUtil.getActiveEditor(event);
			if (editor != null)
				selection = new StructuredSelection(editor);
		}
		if (selection instanceof IStructuredSelection && !selection.isEmpty())
			for (Object selected : ((IStructuredSelection) selection).toArray())
				if (selected instanceof RepositoryCommit)
					commits.add((RepositoryCommit) selected);
				else if (selected instanceof IAdaptable) {
					selected = ((IAdaptable) selected)
							.getAdapter(RepositoryCommit.class);
					if (selected != null)
						commits.add((RepositoryCommit) selected);
				}
		return commits;
	}
}
