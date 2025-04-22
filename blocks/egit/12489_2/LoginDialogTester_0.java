package org.eclipse.egit.internal.mylyn.ui.commit;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.ICommitMessageProvider;
import org.eclipse.mylyn.context.core.ContextCore;
import org.eclipse.mylyn.context.core.IInteractionContext;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.ui.TasksUi;
import org.eclipse.mylyn.team.ui.TeamUiUtil;


public class MylynCommitMessageProvider implements ICommitMessageProvider {

	public String getMessage(IResource[] resources) {
		String message = ""; //$NON-NLS-1$
		if (resources == null)
			return message;
		ITask task = getCurrentTask();
		if (task == null)
			return message;
		boolean checkTaskRepository = true;
		message = TeamUiUtil.getComment(checkTaskRepository, task, resources);
		return message;
	}

	protected ITask getCurrentTask() {
		return TasksUi.getTaskActivityManager().getActiveTask();
	}

	protected IInteractionContext getActiveContext() {
		return ContextCore.getContextManager().getActiveContext();
	}
}
