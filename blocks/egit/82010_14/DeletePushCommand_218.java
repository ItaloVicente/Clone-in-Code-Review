package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.ui.internal.operations.DeletePathsOperationUI;
import org.eclipse.egit.ui.internal.repository.RepositoriesView;
import org.eclipse.egit.ui.internal.repository.tree.FileNode;

public class DeleteFileCommand extends RepositoriesViewCommandHandler<FileNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Collection<IPath> paths = getSelectedFileAndFolderPaths(event);

		RepositoriesView view = getView(event);
		DeletePathsOperationUI operation = new DeletePathsOperationUI(paths, view.getSite());
		operation.run();

		view.refresh();
		return null;
	}

	@Override
	public boolean isEnabled() {
		return isWorkingDirSelection();
	}
}
