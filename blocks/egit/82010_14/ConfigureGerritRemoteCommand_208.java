package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.fetch.SimpleConfigureFetchDialog;
import org.eclipse.egit.ui.internal.repository.tree.FetchNode;
import org.eclipse.egit.ui.internal.repository.tree.RemoteNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jface.dialogs.Dialog;

public class ConfigureFetchCommand extends
		RepositoriesViewCommandHandler<RemoteNode> {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		RepositoryTreeNode selectedNode = getFirstOrNull(getSelectedNodes(event));

		final String configName;
		if (selectedNode instanceof RemoteNode)
			configName = ((RemoteNode) selectedNode).getObject();
		else if (selectedNode instanceof FetchNode)
			configName = ((RemoteNode) selectedNode.getParent()).getObject();
		else
			return null;

		Dialog dlg = SimpleConfigureFetchDialog.getDialog(getShell(event),
				selectedNode.getRepository(), configName);
		dlg.open();
		return null;
	}
}
