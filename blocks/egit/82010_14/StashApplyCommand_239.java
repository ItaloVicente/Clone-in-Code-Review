package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.actions.ResetActionHandler;
import org.eclipse.egit.ui.internal.actions.ResetMenu;
import org.eclipse.egit.ui.internal.repository.SelectResetTypePage;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.lib.Ref;

public class ResetCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode<?>> {

	public static final String ID = "org.eclipse.egit.ui.team.Reset"; //$NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final RepositoryTreeNode<?> node = getSelectedNodes(event).get(0);
		final String currentBranch;
		try {
			currentBranch = node.getRepository().getFullBranch();
		} catch (IOException e1) {
			throw new ExecutionException(e1.getMessage(), e1);
		}
		if (!(node.getObject() instanceof Ref)) {
			return new ResetActionHandler().execute(event);
		}

		final Ref targetBranch = (Ref) node.getObject();

		final String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(node.getRepository());

		Wizard wiz = new Wizard() {

			@Override
			public void addPages() {
				addPage(new SelectResetTypePage(repoName, node.getRepository(),
						currentBranch, targetBranch.getName()));
				setWindowTitle(UIText.ResetCommand_WizardTitle);
			}

			@Override
			public boolean performFinish() {
				final ResetType resetType = ((SelectResetTypePage) getPages()[0])
						.getResetType();
				ResetMenu.performReset(getShell(), node.getRepository(),
						targetBranch.getObjectId(), resetType);
				return true;
			}
		};
		WizardDialog dlg = new WizardDialog(getShell(event), wiz);
		dlg.setHelpAvailable(false);
		dlg.open();

		return null;
	}
}
