package org.eclipse.egit.ui.internal.rebase;

import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.jgit.api.RebaseCommand.InteractiveHandler;
import org.eclipse.jgit.lib.RebaseTodoLine;
import org.eclipse.ui.PlatformUI;

public enum RebaseInteractiveHandler implements InteractiveHandler {
	INSTANCE;

	@Override
	public String modifyCommitMessage(final String commitMessage) {
		final String[] result = new String[1];
		result[0] = commitMessage;
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				CommitMessageEditorDialog dialog = new CommitMessageEditorDialog(
						PlatformUI.getWorkbench()
						.getDisplay().getActiveShell(),
						commitMessage);
				if (dialog.open() == Window.OK) {
					result[0] = dialog.getCommitMessage();
				}
			}
		});
		return result[0];
	}

	@Override
	public void prepareSteps(List<RebaseTodoLine> steps) {
	}
}
