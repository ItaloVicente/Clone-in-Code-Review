package org.eclipse.egit.ui.internal.repository.tree.command;

import java.net.URISyntaxException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.fetch.FetchWizard;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryNode;
import org.eclipse.jface.wizard.WizardDialog;

public class FetchCommand extends
		RepositoriesViewCommandHandler<RepositoryNode> {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		RepositoryNode node = getSelectedNodes(event).get(0);

		try {
			WizardDialog dlg = new WizardDialog(getShell(event),
					new FetchWizard(node.getRepository()));
			dlg.setHelpAvailable(false);
			dlg.open();
		} catch (URISyntaxException e1) {
			Activator.handleError(e1.getMessage(), e1, true);
		}
		return null;
	}
}
