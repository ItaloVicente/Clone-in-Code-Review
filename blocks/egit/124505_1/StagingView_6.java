package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryNode;
import org.eclipse.jgit.util.LfsFactory;
import org.eclipse.jgit.util.LfsFactory.LfsInstallCommand;

public class InstallLfsLocalCommand extends
		RepositoriesViewCommandHandler<RepositoryNode> {

	public static final String ID = "org.eclipse.egit.ui.team.InstallLfsLocal"; //$NON-NLS-1$

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (!LfsFactory.getInstance().isAvailable()) {
			return null;
		}

		final List<RepositoryNode> selectedNodes;
		try {
			selectedNodes = getSelectedNodes(event);
			if (selectedNodes.isEmpty())
				return null;
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, true);
			return null;
		}

		for (RepositoryNode n : selectedNodes) {
			try {
				LfsInstallCommand cmd = LfsFactory.getInstance()
						.getInstallCommand();
				if (cmd != null) {
					cmd.setRepository(n.getRepository()).call();
				}
			} catch (Exception e) {
				Activator.handleError(
						UIText.ConfigurationChecker_installLfsCannotInstall, e,
						false);
			}
		}

		return null;
	}

}
