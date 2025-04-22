package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.repository.tree.PushNode;
import org.eclipse.egit.ui.internal.repository.tree.RemoteNode;
import org.eclipse.jgit.lib.RepositoryConfig;

public class DeletePushCommand extends RepositoriesViewCommandHandler<PushNode> {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PushNode node = getSelectedNodes(event).get(0);
		RemoteNode remote = (RemoteNode) node.getParent();
		RepositoryConfig config = node.getRepository().getConfig();
		config.unset("remote", remote.getObject(), "pushurl"); //$NON-NLS-1$ //$NON-NLS-2$
		config.unset("remote", remote.getObject(), "push"); //$NON-NLS-1$ //$NON-NLS-2$
		try {
			config.save();
		} catch (IOException e1) {
			Activator.handleError(e1.getMessage(), e1, true);
		}

		return null;
	}
}
