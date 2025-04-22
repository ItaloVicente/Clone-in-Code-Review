package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroup;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroupNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroups;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jface.window.Window;

public class DeleteRepositoryGroupCommand
		extends RepositoriesViewCommandHandler<RepositoryTreeNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<RepositoryGroup> groupsToDelete = new ArrayList<>();
		List<RepositoryGroupNode> groupsNodes = new ArrayList<>();
		List<RepositoryTreeNode> elements = getSelectedNodes();
		for (Object element : elements) {
			if (element instanceof RepositoryGroupNode) {
				RepositoryGroupNode groupNode = (RepositoryGroupNode) element;
				groupsNodes.add(groupNode);
				groupsToDelete.add(groupNode.getGroup());
			}
		}
		if (!groupsToDelete.isEmpty()) {
			DeleteRepositoryGroupConfirmDialog confirmDelete = new DeleteRepositoryGroupConfirmDialog(
					getShell(event), groupsNodes);
			if (confirmDelete.open() == Window.OK) {
				RepositoryGroups groups = new RepositoryGroups();
				groups.delete(groupsToDelete);
				getView(event).refresh();
			}
		}
		return null;
	}
}
