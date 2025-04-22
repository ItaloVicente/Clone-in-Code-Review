package org.eclipse.egit.ui.internal.operations;

import org.eclipse.team.core.subscribers.SubscriberScopeManager;
import org.eclipse.ui.IWorkbenchPart;

public class GitScopeOperationFactory {

	private static GitScopeOperationFactory instance;

	public GitScopeOperation createGitScopeOperation(
			IWorkbenchPart part, SubscriberScopeManager manager) {
		GitScopeOperation buildScopeOperation = new GitScopeOperation(part,
				manager);
		return buildScopeOperation;
	}

	public static synchronized GitScopeOperationFactory getFactory() {
		if(instance == null) {
			instance = new GitScopeOperationFactory();
		}
		return instance;
	}

	public static synchronized void setFactory(GitScopeOperationFactory newInstance) {
		instance = newInstance;
	}
}
