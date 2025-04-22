package org.eclipse.egit.ui.internal.actions;

import java.util.Arrays;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.core.op.DisconnectProviderOperation;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.decorators.GitLightweightDecorator;
import org.eclipse.egit.ui.internal.job.JobUtil;

public class DisconnectActionHandler extends RepositoryActionHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IProject[] projects = getProjectsForSelectedResources();
		if (projects.length == 0)
			return null;
		JobUtil.scheduleUserJob(new DisconnectProviderOperation(Arrays
				.asList(projects)), UIText.Disconnect_disconnect,
				JobFamilies.DISCONNECT, new JobChangeAdapter() {
					@Override
					public void done(IJobChangeEvent actEvent) {
						GitLightweightDecorator.refresh();
					}
				});
		return null;
	}
}
