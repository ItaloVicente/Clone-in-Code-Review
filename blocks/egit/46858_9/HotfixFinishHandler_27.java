package org.eclipse.egit.gitflow.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.egit.core.internal.Utils;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.handlers.HandlerUtil;

public class GitFlowHandlerUtil {
	public static GitFlowRepository getRepository(ExecutionEvent event) {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getCurrentSelection(event);
		PlatformObject firstElement = (PlatformObject) selection
				.getFirstElement();
		Repository repository = Utils.getAdapter(firstElement,
				Repository.class);
		if (repository == null) {
			return null;
		}
		return new GitFlowRepository(repository);
	}

}
