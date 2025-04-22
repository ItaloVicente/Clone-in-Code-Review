package org.eclipse.egit.internal.mylyn.ui.commit;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.ICommitMessageProvider;
import org.eclipse.mylyn.internal.team.ui.FocusedTeamUiPlugin;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskActivityManager;
import org.eclipse.mylyn.tasks.ui.TasksUi;


@SuppressWarnings("restriction")
public class MylynCommitMessageProvider implements ICommitMessageProvider {

	public String getMessage(IResource[] resources) {
		String message = "";
		ITaskActivityManager tam = TasksUi.getTaskActivityManager();
		ITask task = tam.getActiveTask();
		if(task == null)
			return message;
		String template = FocusedTeamUiPlugin.getDefault().getPreferenceStore().getString(FocusedTeamUiPlugin.COMMIT_TEMPLATE);
		FocusedTeamUiPlugin.getDefault().getCommitTemplateManager().generateComment(task, template);
		message = FocusedTeamUiPlugin.getDefault().getCommitTemplateManager().generateComment(task, template);
		return message;
	}

}
