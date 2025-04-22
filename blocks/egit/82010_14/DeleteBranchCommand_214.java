package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.NewRepositoryWizard;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jface.wizard.WizardDialog;

public class CreateRepositoryCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		WizardDialog dlg = new WizardDialog(getShell(event),
				new NewRepositoryWizard(false));
		dlg.setHelpAvailable(false);
		dlg.open();
		return null;
	}
}
