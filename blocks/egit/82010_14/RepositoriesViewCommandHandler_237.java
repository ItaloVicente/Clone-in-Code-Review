package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.dialogs.BranchRenameDialog;
import org.eclipse.egit.ui.internal.repository.tree.RefNode;
import org.eclipse.swt.widgets.Shell;

public class RenameBranchCommand extends
		RepositoriesViewCommandHandler<RefNode> {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final List<RefNode> nodes = getSelectedNodes(event);
		RefNode refNode = nodes.get(0);

		Shell shell = getShell(event);
		new BranchRenameDialog(shell, refNode.getRepository(), refNode.getObject()).open();
		return null;
	}
}
