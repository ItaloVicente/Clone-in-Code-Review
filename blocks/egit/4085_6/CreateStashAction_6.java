package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.egit.core.op.ApplyStashOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.internal.job.JobUtil;
import org.eclipse.jgit.lib.Repository;

public class ApplyStashQuickActionHandler extends RepositoryActionHandler {
	public Repository repo;

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IResource[] resources = getSelectedResources(event);
		repo = getRepository(true, event);

		if (repo == null)
			return null;
		if (resources.length == 0)
			return null;

		ApplyStashOperation op = new ApplyStashOperation(resources);

		JobUtil.scheduleUserJob(op, "Apply Stash", //$NON-NLS-1$
				JobFamilies.LIST_STASH);

		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
					IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(
					new org.eclipse.core.runtime.Status(
							IStatus.INFO, Activator.getPluginId(), IStatus.ERROR, e.getMessage(), e
					)
			);
		}

		return null;
	}
}
