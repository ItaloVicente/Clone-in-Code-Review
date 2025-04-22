package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.CompareUtils;
import org.eclipse.egit.ui.internal.merge.GitCompareEditorInput;
import org.eclipse.egit.ui.internal.repository.tree.RefNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.egit.ui.internal.repository.tree.TagNode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

public class CompareCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<RepositoryTreeNode> nodes = getSelectedNodes();
		String commit1 = getCommit(nodes.get(0));
		String commit2 = getCommit(nodes.get(1));
		if (commit1 != null && commit2 != null) {
			Repository repo = (nodes.get(0).getRepository());
			compare(event, repo, commit1, commit2);
		}
		return null;
	}

	void compare(ExecutionEvent event, Repository repo,
			String commit1, String commit2) throws ExecutionException {
		IWorkbenchPage workbenchPage = HandlerUtil
				.getActiveWorkbenchWindowChecked(event).getActivePage();
		GitCompareEditorInput compareInput = new GitCompareEditorInput(commit1,
				commit2, repo);
		CompareUtils.openInCompare(workbenchPage, compareInput);
	}

	private String getCommit(RepositoryTreeNode node) {
		if (node instanceof TagNode) {
			return ((TagNode) node).getCommitId();
		} else if (node instanceof RefNode) {
			ObjectId id = ((RefNode) node).getObject().getObjectId();
			if (id != null) {
				return id.getName();
			}
		}
		return null;
	}


	@Override
	public boolean isEnabled() {
		List<RepositoryTreeNode> nodes = getSelectedNodes();
		if (nodes.size() == 2) {
			return nodes.stream().map(RepositoryTreeNode::getRepository)
					.distinct().count() == 1;
		}
		return false;
	}

}
