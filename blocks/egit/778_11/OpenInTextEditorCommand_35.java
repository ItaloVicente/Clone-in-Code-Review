package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.tree.FileNode;
import org.eclipse.egit.ui.internal.repository.tree.RefNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.egit.ui.internal.repository.tree.TagNode;

public class OpenCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final RepositoryTreeNode node = getSelectedNodes(event).get(0);

		if (node instanceof RefNode || node instanceof TagNode)
			return new CheckoutCommand().execute(event);
		if (node instanceof FileNode)
			return new OpenInTextEditorCommand().execute(event);

		return null;
	}
}
