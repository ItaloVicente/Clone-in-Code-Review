package org.eclipse.egit.ui.internal.actions;

import java.net.URISyntaxException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.fetch.FetchWizard;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.lib.Repository;

public class FetchActionHandler extends RepositoryActionHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Repository repository = getRepository(true, event);
		if (repository == null)
			return null;

		final FetchWizard fetchWizard;
		try {
			fetchWizard = new FetchWizard(repository);
		} catch (URISyntaxException x) {
			ErrorDialog.openError(getShell(event),
					UIText.FetchAction_wrongURITitle,
					UIText.FetchAction_wrongURIMessage, new Status(
							IStatus.ERROR, Activator.getPluginId(), x
									.getMessage(), x));
			return null;
		}
		new WizardDialog(getShell(event), fetchWizard).open();
		return null;
	}

	public boolean isEnabled() {
		try {
			return getRepository(false, null) != null;
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
			return false;
		}
	}
}
