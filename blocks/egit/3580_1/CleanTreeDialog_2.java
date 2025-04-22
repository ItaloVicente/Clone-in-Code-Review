package org.eclipse.egit.ui.internal.actions;

import java.io.IOException;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.op.CleanOperation;
import org.eclipse.egit.ui.JobFamilies;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.dialogs.CleanTreeDialog;
import org.eclipse.egit.ui.internal.job.JobUtil;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.egit.ui.Activator;

public class CleanActionHandler extends RepositoryActionHandler {
	public Repository repo;

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IResource[] resources = getSelectedResources(event);
		repo = getRepository(true, event);
		if (repo == null)
			return null;

		if (resources.length == 0)
			return null;

		CleanOperation op = new CleanOperation(resources);
		Set<String> fileList = op.dryRun();

		String currentBranchName;
		try {
			currentBranchName = repo.getBranch();
		} catch (IOException e) {
			Activator
					.handleError(UIText.TagAction_cannotGetBranchName, e, true);
			return null;
		}

		CleanTreeDialog dialog = new CleanTreeDialog(getShell(event), currentBranchName, repo, fileList);

		if (dialog.open() != IDialogConstants.OK_ID)
			return null;

		JobUtil.scheduleUserJob(op, "Clean", //$NON-NLS-1$
				JobFamilies.CLEAN);
		return null;
	}
}
