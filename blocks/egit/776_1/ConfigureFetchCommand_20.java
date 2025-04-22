package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.clone.GitCloneWizard;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jface.wizard.WizardDialog;

public class CloneCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		WizardDialog dlg = new WizardDialog(
				getView(event).getSite().getShell(), new GitCloneWizard());
		dlg.open();
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
