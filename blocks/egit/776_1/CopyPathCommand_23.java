package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.NewRemoteWizard;
import org.eclipse.egit.ui.internal.repository.tree.RemotesNode;
import org.eclipse.jface.wizard.WizardDialog;

public class ConfigureRemoteCommand extends
		RepositoriesViewCommandHandler<RemotesNode> {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		RemotesNode node = getSelectedNodes(event).get(0);

		WizardDialog dlg = new WizardDialog(
				getView(event).getSite().getShell(), new NewRemoteWizard(node
						.getRepository()));
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
