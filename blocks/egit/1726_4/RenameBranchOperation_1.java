package org.eclipse.egit.core.op;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.CoreText;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CannotDeleteCurrentBranchException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NotMergedException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.osgi.util.NLS;

public class DeleteBranchOperation implements IEGitOperation {
	public final static int OK = 0;

	public final static int REJECTED_CURRENT = 1;

	public final static int REJECTED_UNMERGED = 2;

	public final static int NOT_TRIED = -1;

	private int status = NOT_TRIED;

	private final Repository repository;

	private final Ref branch;

	private final boolean force;

	public DeleteBranchOperation(Repository repository, Ref branch,
			boolean force) {
		this.repository = repository;
		this.branch = branch;
		this.force = force;
	}

	public int getStatus() {
		return status;
	}

	public void execute(IProgressMonitor m) throws CoreException {
		IProgressMonitor monitor;
		if (m == null)
			monitor = new NullProgressMonitor();
		else
			monitor = m;

		IWorkspaceRunnable action = new IWorkspaceRunnable() {
			public void run(IProgressMonitor actMonitor) throws CoreException {
				String taskName = NLS.bind(
						CoreText.DeleteBranchOperation_TaskName, branch
								.getName());
				actMonitor.beginTask(taskName, 1);
				try {
					new Git(repository).branchDelete().setBranchNames(
							branch.getName()).setForce(force).call();
					status = OK;
				} catch (NotMergedException e) {
					status = REJECTED_UNMERGED;
				} catch (CannotDeleteCurrentBranchException e) {
					status = REJECTED_CURRENT;
				} catch (JGitInternalException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				}
				actMonitor.worked(1);
				actMonitor.done();
			}
		};
		ResourcesPlugin.getWorkspace().run(action, monitor);
	}

	public ISchedulingRule getSchedulingRule() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
}
