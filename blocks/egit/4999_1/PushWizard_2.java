package org.eclipse.egit.ui.internal.history.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.history.GitHistoryPage;
import org.eclipse.egit.ui.internal.push.SimplePushRefWizard;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.PlotCommit;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.ui.handlers.HandlerUtil;

public class PushCommitHandler extends AbstractHistoryCommandHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PlotCommit commit = (PlotCommit) getSelection(getPage()).getFirstElement();
		final Repository repo = getRepository(event);

		try {
			WizardDialog dlg = new WizardDialog(HandlerUtil
					.getActiveShellChecked(event), new SimplePushRefWizard(repo, commit.getId()));
			dlg.setHelpAvailable(true);
			dlg.open();
		} catch (Exception e) {
			Activator.handleError(e.getMessage(), e, true);
		}

		return null;
	}

	@Override
	public boolean isEnabled() {
		GitHistoryPage page = getPage();
		if (page == null)
			return false;
		IStructuredSelection sel = getSelection(page);
		return sel.size() == 1 && sel.getFirstElement() instanceof RevCommit;
	}
}
