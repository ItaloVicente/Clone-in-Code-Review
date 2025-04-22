package org.eclipse.egit.core.op;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.StashDropCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.events.RefsChangedEvent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.TeamException;

public class StashDropOperation implements IEGitOperation {

	private final int index;

	private final Repository repo;

	public StashDropOperation(final Repository repo, final int index) {
		if (index < 0)
			throw new IllegalArgumentException();
		this.index = index;
		this.repo = repo;
	}

	public void execute(IProgressMonitor monitor) throws CoreException {
		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			public void run(IProgressMonitor pm) throws CoreException {
				pm.beginTask("", 1); //$NON-NLS-1$
				StashDropCommand command = Git.wrap(repo).stashDrop();
				command.setStashRef(index);
				try {
					command.call();
					repo.fireEvent(new RefsChangedEvent());
				} catch (JGitInternalException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
				} catch (GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
				} finally {
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
