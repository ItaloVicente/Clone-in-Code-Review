package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.push.PushConfiguredRemoteAction;
import org.eclipse.egit.ui.internal.push.SimpleConfigurePushDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jgit.lib.Repository;

public class SimplePushActionHandler extends RepositoryActionHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Repository repository = getRepository(true, event);
		if (repository == null)
			return null;

		if (SimpleConfigurePushDialog.shouldConfigure(repository)) {
			Dialog dlg = SimpleConfigurePushDialog.getDialog(getShell(event),
					repository);
			dlg.open();
		} else {
			PushConfiguredRemoteAction op = new PushConfiguredRemoteAction(
					repository, SimpleConfigurePushDialog
							.getConfiguredRemote(repository), Activator
							.getDefault().getPreferenceStore().getInt(
									UIPreferences.REMOTE_CONNECTION_TIMEOUT));
			op.start();
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return getRepository() != null;
	}
}
