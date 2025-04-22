package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.commit.CommitEditor;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.egit.ui.internal.repository.tree.StashedCommitNode;
import org.eclipse.egit.ui.internal.repository.tree.FileNode;
import org.eclipse.egit.ui.internal.repository.tree.RefNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.egit.ui.internal.repository.tree.TagNode;

public class OpenCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final List<RepositoryTreeNode> nodes = getSelectedNodes(event);
		if (nodes.isEmpty())
			return null;

		final RepositoryTreeNode node = nodes.get(0);

		if (node instanceof RefNode || node instanceof TagNode)
			return new CheckoutCommand().execute(event);
		if (node instanceof FileNode)
			return new OpenInEditorCommand().execute(event);
		if (node instanceof StashedCommitNode) {
			RepositoryCommit repositoryCommit = new RepositoryCommit(
					node.getRepository(),
					((StashedCommitNode) node).getObject());
			repositoryCommit.setStash(true);
			CommitEditor.openQuiet(repositoryCommit);
		}

		return null;
	}
}
