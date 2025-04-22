package org.eclipse.egit.gitflow.ui.internal.actions;

import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

public final class ReleaseStartFromCommitHandler extends SelectionAdapter {
	private ReleaseStartHandler releaseStartHandler;
	private GitFlowRepository gfRepo;
	private String startCommitSha1;
	private Shell activeShell;

	public ReleaseStartFromCommitHandler(GitFlowRepository gfRepo, String startCommitSha1, Shell activeShell) {
		this.gfRepo = gfRepo;
		this.startCommitSha1 = startCommitSha1;
		this.activeShell = activeShell;
		releaseStartHandler = new ReleaseStartHandler();
	}

	public void widgetSelected(SelectionEvent e) {
		releaseStartHandler.doExecute(gfRepo, startCommitSha1, activeShell);
	}
}
