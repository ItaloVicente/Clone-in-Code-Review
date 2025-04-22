package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.ui.internal.history.CommitSelectionDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;

public class CompareWithCommitCommand extends CompareWithCommand {

	@Override
	protected String getRef(ExecutionEvent event, @NonNull Repository repository,
			Collection<IPath> paths) {
		CommitSelectionDialog dlg = new CommitSelectionDialog(getShell(event),
				repository, null);
		if (dlg.open() != Window.OK) {
			return null;
		}
		return dlg.getCommitId().getName();
	}
}
