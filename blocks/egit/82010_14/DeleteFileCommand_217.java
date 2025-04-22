package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.repository.tree.FetchNode;
import org.eclipse.egit.ui.internal.repository.tree.RemoteNode;
import org.eclipse.jgit.lib.StoredConfig;

public class DeleteFetchCommand extends
		RepositoriesViewCommandHandler<FetchNode> {

	private static final String REMOTE = "remote"; //$NON-NLS-1$

	private static final String FETCH = "fetch"; //$NON-NLS-1$

	private static final String PUSH = "push"; //$NON-NLS-1$

	private static final String URL = "url"; //$NON-NLS-1$

	private static final String PUSHURL = "pushurl"; //$NON-NLS-1$

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FetchNode node = getSelectedNodes(event).get(0);
		RemoteNode remote = (RemoteNode) node.getParent();

		StoredConfig config = node.getRepository().getConfig();
		String fetchUrl = config.getString(REMOTE, remote.getObject(), URL);
		config.unset(REMOTE, remote.getObject(), FETCH);
		config.unset(REMOTE, remote.getObject(), URL);
		if (fetchUrl != null) {
			boolean hasPush = config.getStringList(REMOTE, remote.getObject(),
					PUSH).length > 0;
			if (hasPush) {
				String[] pushurls = config.getStringList(REMOTE, remote
						.getObject(), PUSHURL);
				if (pushurls.length == 0)
					config.setString(REMOTE, remote.getObject(), PUSHURL,
							fetchUrl);
			}
		}

		try {
			config.save();
		} catch (IOException e1) {
			Activator.handleError(e1.getMessage(), e1, true);
		}

		return null;
	}
}
