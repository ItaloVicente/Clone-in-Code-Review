package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.push.PushConfiguredRemoteAction;
import org.eclipse.egit.ui.internal.repository.tree.PushNode;
import org.eclipse.egit.ui.internal.repository.tree.RemoteNode;

public class PushConfiguredRemoteCommand extends
		RepositoriesViewCommandHandler<PushNode> {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PushNode node = getSelectedNodes(event).get(0);
		RemoteNode remote = (RemoteNode) node.getParent();

		new PushConfiguredRemoteAction(node.getRepository(), remote.getObject())
				.run(getView(event).getSite().getShell(), false);

		return null;
	}
}
