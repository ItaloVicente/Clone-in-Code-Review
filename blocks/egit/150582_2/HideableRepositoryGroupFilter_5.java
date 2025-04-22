package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroup;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroupNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroups;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;

public class UnhideRepositoryGroupCommand
		extends RepositoriesViewCommandHandler<RepositoryTreeNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<RepositoryGroup> groupsToUnmarkHideable = new ArrayList<>();
		List<RepositoryTreeNode> elements = getSelectedNodes();
		for (Object element : elements) {
			if (element instanceof RepositoryGroupNode) {
				groupsToUnmarkHideable
						.add(((RepositoryGroupNode) element).getGroup());
			}
		}
		if (!groupsToUnmarkHideable.isEmpty()) {
			RepositoryGroups groups = new RepositoryGroups();
			groups.setHideable(groupsToUnmarkHideable, false);
			getView(event).refresh();
		}
		return null;
	}
}
