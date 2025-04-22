package org.eclipse.egit.ui.internal.commit.command;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.egit.ui.internal.repository.CreateBranchWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateBranchHandler extends CommitCommandHandler {

	public static final String ID = "org.eclipse.egit.ui.commit.CreateBranch"; //$NON-NLS-1$

	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<RepositoryCommit> commits = getCommits(event);
		if (commits.size() == 1) {
			RepositoryCommit commit = commits.get(0);
			WizardDialog dlg = new WizardDialog(
					HandlerUtil.getActiveShellChecked(event),
					new CreateBranchWizard(commit.getRepository(), commit
							.getRevCommit().name()));
			dlg.setHelpAvailable(false);
			dlg.open();
		}
		return null;
	}

}
