package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.gerrit.ConfigureGerritWizard;
import org.eclipse.egit.ui.internal.repository.tree.RemoteNode;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class ConfigureGerritRemoteCommand extends
		RepositoriesViewCommandHandler<RemoteNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final RemoteNode node = getSelectedNodes(event).get(0);
		Repository repository = node.getRepository();
		final String remoteName = node.getObject();

		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();
		ConfigureGerritWizard configureGerritWizard = new ConfigureGerritWizard(
				repository, remoteName);
		WizardDialog dlg = new WizardDialog(shell, configureGerritWizard);
		dlg.setHelpAvailable(false);
		dlg.open();

		return null;
	}
}
