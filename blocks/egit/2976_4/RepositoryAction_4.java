package org.eclipse.egit.ui.internal.actions;

import java.util.Arrays;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.op.AssumeUnchangedOperation;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.job.JobUtil;

public class NoAssumeUnchangedActionHandler extends RepositoryActionHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IResource[] resources = getSelectedResources(event);
		if (resources.length == 0)
			return null;
		AssumeUnchangedOperation op = new AssumeUnchangedOperation(Arrays
				.asList(resources), false);
		JobUtil.scheduleUserJob(op, UIText.AssumeUnchanged_assumeUnchanged,
				JobFamilies.ASSUME_NOASSUME_UNCHANGED);
		return null;
	}
}
