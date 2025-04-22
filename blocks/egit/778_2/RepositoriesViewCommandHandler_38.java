package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.repository.RepositoriesView;
import org.eclipse.egit.ui.internal.repository.tree.RemoteNode;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.lib.RepositoryConfig;
import org.eclipse.osgi.util.NLS;

public class RemoveRemoteCommand extends
		RepositoriesViewCommandHandler<RemoteNode> {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		final RemoteNode node = getSelectedNodes(event).get(0);
		final String configName = node.getObject();

		boolean ok = MessageDialog.openConfirm(getView(event).getSite()
				.getShell(), UIText.RepositoriesView_ConfirmDeleteRemoteHeader,
				NLS.bind(UIText.RepositoriesView_ConfirmDeleteRemoteMessage,
						configName));
		if (ok) {
			RepositoryConfig config = node.getRepository().getConfig();
			config.unsetSection(RepositoriesView.REMOTE, configName);
			try {
				config.save();
			} catch (IOException e1) {
				Activator.handleError(UIText.RepositoriesView_ErrorHeader, e1,
						true);
			}
		}

		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

}
