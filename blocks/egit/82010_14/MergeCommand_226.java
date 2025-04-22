package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RegistryToggleState;

public class LinkWithSelectionCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		Command command = event.getCommand();
		HandlerUtil.toggleCommandState(command);
		@SuppressWarnings("boxing")
		boolean test = (Boolean) command.getState(RegistryToggleState.STATE_ID).getValue();
		getView(event).setReactOnSelection(test);
		return null;
	}
}
