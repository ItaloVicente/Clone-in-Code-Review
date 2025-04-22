package org.eclipse.egit.ui.internal.repository.tree.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

public class DeleteCommand extends RemoveCommand implements IHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		super.removeRepository(event, true);
		return null;
	}
}
