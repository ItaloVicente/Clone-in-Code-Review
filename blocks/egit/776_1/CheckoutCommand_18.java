package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.repository.RepositorySearchDialog;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jface.window.Window;

public class AddCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {

	public Object execute(ExecutionEvent event) throws ExecutionException {

		RepositorySearchDialog sd = new RepositorySearchDialog(getView(event)
				.getSite().getShell(), util.getConfiguredRepositories());
		if (sd.open() == Window.OK) {

			for (String dir : sd.getDirectories()) {
				util.addConfiguredRepository(new File(dir));
			}
		}

		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

}
