package org.eclipse.egit.core.op;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.egit.core.EclipseGitProgressTransformer;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.SubmoduleAddCommand;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.TeamException;

public class SubmoduleAddOperation implements IEGitOperation {

	private final Repository repo;

	private final String path;

	private final String uri;

	public SubmoduleAddOperation(final Repository repo, final String path,
			final String uri) {
		this.repo = repo;
		this.path = path;
		this.uri = uri;
	}

	public void execute(IProgressMonitor monitor) throws CoreException {
		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			public void run(IProgressMonitor pm) throws CoreException {
				final SubmoduleAddCommand add = Git.wrap(repo).submoduleAdd();
				add.setProgressMonitor(new EclipseGitProgressTransformer(pm));
				add.setPath(path);
				add.setURI(uri);
				try {
					if (add.call() != null)
						repo.notifyIndexChanged();
				} catch (JGitInternalException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
				}
			}
		};
		ResourcesPlugin.getWorkspace().run(action,
				monitor != null ? monitor : new NullProgressMonitor());
	}

	public ISchedulingRule getSchedulingRule() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
}
