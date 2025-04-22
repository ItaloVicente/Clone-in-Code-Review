package org.eclipse.egit.core.op;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.team.core.TeamException;

public class StashCreateOperation implements IEGitOperation {

	private final Repository repository;

	private RevCommit commit;

	public StashCreateOperation(final Repository repository) {
		this.repository = repository;
	}

	public RevCommit getCommit() {
		return commit;
	}

	public void execute(IProgressMonitor monitor) throws CoreException {
		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			public void run(IProgressMonitor pm) throws CoreException {
				try {
					commit = Git.wrap(repository).stashCreate().call();
				} catch (JGitInternalException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
				} catch (GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
				} finally {
					if (commit != null)
						repository.notifyIndexChanged();
					pm.done();
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
