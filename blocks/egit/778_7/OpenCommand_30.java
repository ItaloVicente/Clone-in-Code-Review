package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ToolItem;

public class LinkWithSelectionCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		Event evt = (Event) event.getTrigger();
		ToolItem item = (ToolItem) evt.widget;
		boolean selected = item.getSelection();
		getView(event).setReactOnSelection(selected);
		return null;
	}
}
