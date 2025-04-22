package org.eclipse.egit.ui.internal.actions;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.core.op.RebaseOperation;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.dialogs.RebaseTargetSelectionDialog;
import org.eclipse.egit.ui.internal.rebase.RebaseHelper;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;

public class RebaseActionHandler extends RepositoryActionHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Repository repo = getRepository(true, event);
		if (repo == null)
			return null;

		Ref ref;
		Shell shell = getShell(event);
		RebaseTargetSelectionDialog selectionDialog = new RebaseTargetSelectionDialog(
				shell, repo);
		if (selectionDialog.open() == IDialogConstants.OK_ID) {
			String refName = selectionDialog.getRefName();
			try {
				ref = repo.getRef(refName);
			} catch (IOException e) {
				throw new ExecutionException(e.getMessage(), e);
			}
		} else
			return null;

		String jobname = NLS.bind(
				UIText.RebaseCurrentRefCommand_RebasingCurrentJobName, ref
						.getName());
		RebaseHelper.runRebaseJob(repo, jobname, ref);

		return null;
	}

}
