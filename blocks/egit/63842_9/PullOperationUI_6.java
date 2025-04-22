package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.pull.PullWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;

public class PullWithOptionsActionHandler extends RepositoryActionHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repo = getRepository();
		WizardDialog dialog = new WizardDialog(getShell(event),
				new PullWizard(repo));
		return dialog.open();

	}

	@Override
	public boolean isEnabled() {
		return getRepository() != null;
	}
}
