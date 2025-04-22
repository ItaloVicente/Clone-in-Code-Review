package org.eclipse.egit.gitflow.ui.internal.menu;

import java.io.IOException;

import org.eclipse.egit.core.internal.Utils;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.ui.Activator;
import org.eclipse.egit.gitflow.ui.internal.UIText;
import org.eclipse.egit.gitflow.ui.internal.actions.ReleaseStartFromCommitHandler;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class DynamicHistoryMenu extends ContributionItem {
	@Override
	public void fill(Menu menu, int index) {
		GitFlowRepository gfRepo = getRepository();
		try {
			if (gfRepo == null || !gfRepo.isDevelop()) {
				return;
			}
		} catch (IOException e) {
			Activator.getDefault().getLog().log(Activator.error(e.getMessage()));
			return;
		}

		RevCommit selectedCommit = getSelectedCommit();
		if (selectedCommit == null) {
			return;
		}
		String startCommitSha1 = selectedCommit.getName();
		Shell activeShell = getActiveShell();

		ReleaseStartFromCommitHandler listener = new ReleaseStartFromCommitHandler(
				gfRepo, startCommitSha1, activeShell);
		MenuItem menuItem = new MenuItem(menu, SWT.PUSH, index);
		menuItem.setText(NLS.bind(
				UIText.DynamicHistoryMenu_startGitflowReleaseFrom,
				abbreviate(selectedCommit)));
		menuItem.addSelectionListener(listener);
	}

	private String abbreviate(RevCommit selectedCommit) {
		return selectedCommit.getId().abbreviate(7).name();
	}

	private RevCommit getSelectedCommit() {
		ISelection selection = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart()
				.getSite().getSelectionProvider().getSelection();
		if (!(selection instanceof IStructuredSelection)) {
			return null;
		}
		IStructuredSelection structSelection = (IStructuredSelection) selection;
		Object firstElement = structSelection.getFirstElement();
		if (!(firstElement instanceof RevCommit)) {
			return null;
		}
		return (RevCommit) firstElement;
	}

	private Shell getActiveShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	private GitFlowRepository getRepository() {
		IWorkbenchPart activePart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart();
		Repository repository = Utils.getAdapter(activePart, Repository.class);
		if (repository == null) {
			return null;
		}
		return new GitFlowRepository(repository);
	}
}
