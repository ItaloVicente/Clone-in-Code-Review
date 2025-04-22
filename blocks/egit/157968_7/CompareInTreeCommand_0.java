package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.CompareUtils;
import org.eclipse.egit.ui.internal.merge.GitCompareEditorInput;
import org.eclipse.egit.ui.internal.repository.tree.AdditionalRefNode;
import org.eclipse.egit.ui.internal.repository.tree.RefNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.egit.ui.internal.repository.tree.StashedCommitNode;
import org.eclipse.egit.ui.internal.repository.tree.TagNode;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

public class CompareCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<RepositoryTreeNode> nodes = getSelectedNodes();
		try {
			RevCommit a = getCommit(nodes.get(0));
			RevCommit b = getCommit(nodes.get(1));
			if (a != null && b != null) {
				Repository repo = (nodes.get(0).getRepository());
				IWorkbenchPage workbenchPage = HandlerUtil
						.getActiveWorkbenchWindowChecked(event).getActivePage();
				if (a.getCommitTime() <= b.getCommitTime()) {
					compare(workbenchPage, repo, b.getName(), a.getName());
				} else {
					compare(workbenchPage, repo, a.getName(), b.getName());
				}
		}
		} catch (IOException e) {
			throw new ExecutionException(e.getLocalizedMessage(), e);
		}
		return null;
	}

	protected void compare(IWorkbenchPage workbenchPage, Repository repo,
			String compareCommit, String baseCommit) throws ExecutionException {
		GitCompareEditorInput compareInput = new GitCompareEditorInput(
				compareCommit, baseCommit, repo);
		CompareUtils.openInCompare(workbenchPage, compareInput);
	}

	private RevCommit getCommit(RepositoryTreeNode node) throws IOException {
		if (node instanceof TagNode) {
			return getCommit(node.getRepository(),
					((TagNode) node).getObject());
		} else if (node instanceof RefNode
				|| node instanceof AdditionalRefNode) {
			return getCommit(node.getRepository(), (Ref) node.getObject());
		} else if(node instanceof StashedCommitNode) {
			return (RevCommit) node.getObject();
		}
		return null;
	}

	private RevCommit getCommit(Repository repository, Ref ref)
			throws IOException {
		if (repository != null && ref != null && ref.getObjectId() != null) {
			return repository.parseCommit(ref.getObjectId());
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
