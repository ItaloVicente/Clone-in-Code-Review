package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroup;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroupNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroups;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;

public class HideRepositoryGroupCommand
		extends RepositoriesViewCommandHandler<RepositoryTreeNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<RepositoryGroup> groupsToMarkHideable = new ArrayList<>();
		List<RepositoryTreeNode> elements = getSelectedNodes();
		for (Object element : elements) {
			if (element instanceof RepositoryGroupNode) {
				groupsToMarkHideable
						.add(((RepositoryGroupNode) element).getGroup());
			}
		}
		if (!groupsToMarkHideable.isEmpty()) {
			RepositoryGroups groups = new RepositoryGroups();
			groups.setHideable(groupsToMarkHideable, true);
			getView(event).refresh();
		}
		return null;
	}
}
