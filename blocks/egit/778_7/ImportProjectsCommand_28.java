package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.fetch.FetchConfiguredRemoteAction;
import org.eclipse.egit.ui.internal.repository.tree.FetchNode;
import org.eclipse.egit.ui.internal.repository.tree.RemoteNode;

public class FetchConfiguredRemoteCommand extends
		RepositoriesViewCommandHandler<FetchNode> {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FetchNode node = getSelectedNodes(event).get(0);
		RemoteNode remote = (RemoteNode) node.getParent();

		new FetchConfiguredRemoteAction(node.getRepository(), remote
				.getObject()).run(getView(event).getSite().getShell());

		return null;
	}
}
