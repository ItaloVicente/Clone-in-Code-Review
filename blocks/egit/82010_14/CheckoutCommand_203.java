package org.eclipse.egit.ui.internal.repository.tree.command;

import java.net.URISyntaxException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.credentials.LoginService;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jgit.transport.URIish;

public class ChangeCredentialsCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode<String>> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		RepositoryTreeNode<String> node = getSelectedNodes(event).get(0);
		URIish uri;
		try {
			uri = new URIish(node.getObject());
		} catch (URISyntaxException e) {
			Activator.handleError(e.getMessage(), e, true);
			return null;
		}

		LoginService.changeCredentials(getShell(event), uri);
		return null;
	}
}
