package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.ui.internal.dialogs.CompareTargetSelectionDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;

public class CompareWithRefCommand extends CompareWithCommand {

	@Override
	protected String getRef(ExecutionEvent event, @NonNull Repository repository,
			Collection<IPath> paths) {
		CompareTargetSelectionDialog dlg = new CompareTargetSelectionDialog(
				getShell(event), repository, null);
		if (dlg.open() != Window.OK) {
			return null;
		}
		return dlg.getRefName();
	}
}
