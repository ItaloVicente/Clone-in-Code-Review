package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.push.SimpleConfigurePushDialog;
import org.eclipse.jgit.lib.Repository;

public class ConfigurePushActionHandler extends RepositoryActionHandler {

	public Object execute(final ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository(true, event);
		if (repository != null)
			SimpleConfigurePushDialog.getDialog(getShell(event), repository)
					.open();
		return null;
	}

	@Override
	public boolean isEnabled() {
		return getRepository() != null
				&& SimpleConfigurePushDialog
						.getConfiguredRemote(getRepository()) != null;
	}

}
