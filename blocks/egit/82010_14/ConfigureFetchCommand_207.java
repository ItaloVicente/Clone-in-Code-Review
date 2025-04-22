package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.dialogs.BranchConfigurationDialog;
import org.eclipse.egit.ui.internal.repository.tree.RefNode;
import org.eclipse.jgit.lib.Repository;

public class ConfigureBranchCommand extends
		RepositoriesViewCommandHandler<RefNode> {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final List<RefNode> nodes = getSelectedNodes(event);
		if (nodes.size() == 1) {
			RefNode node = nodes.get(0);
			String branchName = Repository.shortenRefName(node.getObject()
					.getName());
			BranchConfigurationDialog dlg = new BranchConfigurationDialog(
					getShell(event), branchName, node.getRepository());
			dlg.open();
		}
		return null;
	}
}
