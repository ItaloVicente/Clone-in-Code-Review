package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class DeleteCommand extends RemoveCommand {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		super.removeRepository(event, true);
		return null;
	}
}
