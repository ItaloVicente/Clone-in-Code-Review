package org.eclipse.egit.ui.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;

public class ToggleCommand extends AbstractHandler {

	public static final String BRANCH_HIERARCHY_ID = "org.eclipse.egit.ui.RepositoriesToggleBranchHierarchy"; //$NON-NLS-1$

	public static final String COMMIT_MESSAGE_DECORATION_ID = "org.eclipse.egit.ui.RepositoriesToggleBranchCommit"; //$NON-NLS-1$

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		HandlerUtil.toggleCommandState(event.getCommand());
		return null;
	}

}
