package org.eclipse.egit.gitflow.ui.internal.actions;

import static org.eclipse.egit.gitflow.ui.Activator.error;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.ui.Activator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

public final class InitHandlerWrapper extends SelectionAdapter {
	private InitHandler initHandler;
	private GitFlowRepository gfRepo;
	private Shell activeShell;

	public InitHandlerWrapper(GitFlowRepository gfRepo, Shell activeShell) {
		this.gfRepo = gfRepo;
		this.activeShell = activeShell;
		initHandler = new InitHandler();
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		try {
			initHandler.doExecute(gfRepo, activeShell);
		} catch (ExecutionException e) {
			Activator.getDefault().getLog().log(error(e.getMessage(), e));
		}
	}
}
