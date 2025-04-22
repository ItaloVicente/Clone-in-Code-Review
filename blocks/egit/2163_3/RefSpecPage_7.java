package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.push.PushConfiguredRemoteAction;
import org.eclipse.egit.ui.internal.push.SimpleConfigurePushWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteConfig;

public class SimplePushActionHandler extends RepositoryActionHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Repository repository = getRepository(true, event);
		if (repository == null)
			return null;

		RemoteConfig config = SimpleConfigurePushWizard
				.getConfiguredRemote(repository);
		SimpleConfigurePushWizard wizard = SimpleConfigurePushWizard.getWizard(
				repository, config);
		if (config == null || wizard != null) {
			new WizardDialog(getShell(event), wizard).open();
		} else {
			PushConfiguredRemoteAction op = new PushConfiguredRemoteAction(
					repository, config, Activator.getDefault()
							.getPreferenceStore().getInt(
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
