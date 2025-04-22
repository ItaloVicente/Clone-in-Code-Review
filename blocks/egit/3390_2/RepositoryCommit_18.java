package org.eclipse.egit.ui.internal.commit;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.ActionDelegate;

public class OpenCommitAction extends ActionDelegate implements
		IWorkbenchWindowActionDelegate {

	private Shell shell;

	@Override
	public void run(IAction action) {
		CommitSelectionDialog dialog = new CommitSelectionDialog(shell, true);
		if (Window.OK == dialog.open())
			for (Object result : dialog.getResult())
				CommitEditor.openQuiet((RepositoryCommit) result);
	}

	public void init(IWorkbenchWindow window) {
		shell = window.getShell();
	}

}
