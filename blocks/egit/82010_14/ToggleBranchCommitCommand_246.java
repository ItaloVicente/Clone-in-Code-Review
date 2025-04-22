package org.eclipse.egit.ui.internal.repository.tree.command;

import java.io.IOException;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.egit.ui.internal.synchronize.GitModelSynchronize;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.osgi.util.NLS;

public class SynchronizeCommand extends
		RepositoriesViewCommandHandler<RepositoryTreeNode> {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final RepositoryTreeNode node = getSelectedNodes(event).get(0);
		final String refName = getRefName(node);
		if (refName == null)
			return null;

		String secondRefName = Constants.HEAD;
		if (getSelectedNodes(event).size() == 2) {
			secondRefName = getRefName(getSelectedNodes(event).get(1));
			if (secondRefName == null)
				return null;
		}

		final String secondRefNameParam = secondRefName;

		final boolean includeLocal = getSelectedNodes(event).size() == 1;

		final Repository repo = node.getRepository();
		Job job = new WorkspaceJob(NLS.bind(UIText.SynchronizeCommand_jobName,
				repo.getDirectory())) {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				GitSynchronizeData data;
				try {
					data = new GitSynchronizeData(repo, secondRefNameParam,
							refName, includeLocal);

					Set<IProject> projects = data.getProjects();
					IResource[] resources = projects
							.toArray(new IResource[projects.size()]);

					GitModelSynchronize.launch(data, resources);
				} catch (IOException e) {
					Activator.logError(e.getMessage(), e);
				}

				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();

		return null;
	}

	private String getRefName(final RepositoryTreeNode node) {
		Object object = node.getObject();
		if (!(object instanceof Ref))
			return null;

		Ref ref = (Ref) object;
		return ref.getName();
	}

}
