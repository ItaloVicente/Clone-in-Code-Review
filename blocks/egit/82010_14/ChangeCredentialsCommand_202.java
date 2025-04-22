package org.eclipse.egit.ui.internal.repository.tree.command;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;

public class AddToIndexCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<? extends RepositoryTreeNode> selectedNodes = getSelectedNodes(event);
		if (selectedNodes.isEmpty() || selectedNodes.get(0).getRepository() == null)
			return null;

		Repository repository = selectedNodes.get(0).getRepository();
		IPath workTreePath = new Path(repository.getWorkTree().getAbsolutePath());

		try (Git git = new Git(repository)) {
			AddCommand addCommand = git.add();

			Collection<IPath> paths = getSelectedFileAndFolderPaths(event);
			for (IPath path : paths) {
				String repoRelativepath;
				if (path.equals(workTreePath))
					repoRelativepath = "."; //$NON-NLS-1$
				else
					repoRelativepath = path
							.removeFirstSegments(
									path.matchingFirstSegments(workTreePath))
							.setDevice(null).toString();
				addCommand.addFilepattern(repoRelativepath);
			}
			addCommand.call();
		} catch (GitAPIException e) {
			Activator.logError(UIText.AddToIndexCommand_addingFilesFailed,
					e);
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return isWorkingDirSelection();
	}

}
