package org.eclipse.egilt.mylyn.internal;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.ICommitMessageProvider;
import org.eclipse.mylyn.context.core.ContextCore;
import org.eclipse.mylyn.context.core.IInteractionContext;
import org.eclipse.mylyn.internal.team.ui.ContextChangeSet;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.ui.TasksUi;

@SuppressWarnings("restriction")
public class MylynCommitMessageProvider implements ICommitMessageProvider {

	public String getMessage(IResource[] resources) {
		if (resources == null) {
			return null;
		}
		ITask task = getCurrentTask();
		if (task == null) {
			return null;
		}
		boolean checkTaskRepository = true;
		String comment = ContextChangeSet.getComment(checkTaskRepository, task,
				resources);
		return comment;
	}

	protected ITask getCurrentTask() {
		return TasksUi.getTaskActivityManager().getActiveTask();
	}

	protected IInteractionContext getActiveContext() {
		return ContextCore.getContextManager().getActiveContext();
	}
}
