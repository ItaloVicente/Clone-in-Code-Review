package org.eclipse.egit.core.op;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.CoreText;
import org.eclipse.egit.core.EclipseGitProgressTransformer;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;

public class PullOperation implements IEGitOperation {
	private final Repository repository;

	private PullResult pullResult;

	private final int timeout;

	public PullOperation(Repository repository, int timeout) {
		this.timeout = timeout;
		this.repository = repository;
	}

	public void execute(IProgressMonitor m) throws CoreException {
		if (pullResult != null)
			throw new CoreException(new Status(IStatus.ERROR, Activator
					.getPluginId(), CoreText.OperationAlreadyExecuted));
		IProgressMonitor monitor;
		if (m == null)
			monitor = new NullProgressMonitor();
		else
			monitor = m;
		IWorkspaceRunnable action = new IWorkspaceRunnable() {
			public void run(IProgressMonitor mymonitor) throws CoreException {
				PullCommand pull = new Git(repository).pull();
				try {
					pull.setProgressMonitor(new EclipseGitProgressTransformer(
							mymonitor));
					pull.setTimeout(timeout);
					pullResult = pull.call();
				} catch (GitAPIException e) {
					IStatus error = new Status(IStatus.ERROR, Activator
							.getPluginId(), e.getMessage(), e);
					throw new CoreException(error);
				}
			}
		};
		ResourcesPlugin.getWorkspace().run(action, monitor);
	}

	public PullResult getResult() {
		return this.pullResult;
	}

	public ISchedulingRule getSchedulingRule() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
}
