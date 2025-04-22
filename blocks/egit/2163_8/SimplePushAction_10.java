package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.fetch.FetchConfiguredRemoteAction;
import org.eclipse.egit.ui.internal.fetch.SimpleConfigureFetchDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteConfig;

public class SimpleFetchActionHandler extends RepositoryActionHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Repository repository = getRepository(true, event);
		if (repository == null)
			return null;
		RemoteConfig config = SimpleConfigureFetchDialog
				.getConfiguredRemote(repository);
		if (config == null) {
			MessageDialog
					.openInformation(
							getShell(event),
							UIText.SimpleFetchActionHandler_NothingToFetchDialogTitle,
							UIText.SimpleFetchActionHandler_NothingToFetchDialogMessage);
			return null;
		}

		FetchConfiguredRemoteAction op = new FetchConfiguredRemoteAction(
				repository, config, Activator.getDefault().getPreferenceStore()
						.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT));
		op.start();
		return null;
	}

	@Override
	public boolean isEnabled() {
		return getRepository() != null
				&& SimpleConfigureFetchDialog
						.getConfiguredRemote(getRepository()) != null;
	}
}
