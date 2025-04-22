package org.eclipse.egit.ui.internal.commands.shared;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.push.PushToGerritWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.handlers.HandlerUtil;

public class PushHeadToGerritCommand extends AbstractSharedCommandHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository(event);
		if (repository == null)
			return null;

		PushToGerritWizard wiz = new PushToGerritWizard(repository);
		WizardDialog dlg = new WizardDialog(HandlerUtil
				.getActiveShellChecked(event), wiz);
		dlg.setHelpAvailable(false);
		dlg.open();
		return null;
	}
}
