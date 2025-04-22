package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.op.CreateStashOperation;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.internal.job.JobUtil;
import org.eclipse.jgit.lib.Repository;

public class CreateStashActionHandler extends RepositoryActionHandler {
	public Repository repo;

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IResource[] resources = getSelectedResources(event);
		repo = getRepository(true, event);

		if (repo == null)
			return null;
		if (resources.length == 0)
			return null;

		CreateStashOperation op = new CreateStashOperation(resources);

		JobUtil.scheduleUserJob(op, "Create Stash", //$NON-NLS-1$
				JobFamilies.CREATE_STASH);

		return null;
	}
}
