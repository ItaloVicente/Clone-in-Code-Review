package org.eclipse.egit.core.op;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.SubmoduleSyncCommand;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.TeamException;

public class SubmoduleSyncOperation implements IEGitOperation {

	private final Repository repository;

	private final Collection<String> paths;

	public SubmoduleSyncOperation(final Repository repository) {
		this.repository = repository;
		paths = new ArrayList<String>();
	}

	public SubmoduleSyncOperation addPath(final String path) {
		paths.add(path);
		return this;
	}

	public void execute(final IProgressMonitor monitor) throws CoreException {
		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			public void run(IProgressMonitor pm) throws CoreException {
				pm.beginTask("", 1); //$NON-NLS-1$
				Map<String, String> updates = null;
				try {
					SubmoduleSyncCommand sync = Git.wrap(repository)
							.submoduleSync();
					for (String path : paths)
						sync.addPath(path);
					updates = sync.call();
				} catch (JGitInternalException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
				} finally {
					if (updates != null && !updates.isEmpty())
						repository.notifyIndexChanged();
					pm.done();
				}
			}
		};
		ResourcesPlugin.getWorkspace().run(action,
				monitor != null ? monitor : new NullProgressMonitor());
	}

	public ISchedulingRule getSchedulingRule() {
		return null;
	}
}
