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
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.osgi.util.NLS;

public class CreateLocalBranchOperation implements IEGitOperation {
	private final String name;

	private final Repository repository;

	private final Ref ref;

	private final RevCommit commit;

	public CreateLocalBranchOperation(Repository repository, String name,
			Ref ref) {
		this.name = name;
		this.repository = repository;
		this.ref = ref;
		this.commit = null;
	}

	public CreateLocalBranchOperation(Repository repository, String name,
			RevCommit commit) {
		this.name = name;
		this.repository = repository;
		this.ref = null;
		this.commit = commit;
	}

	public void execute(IProgressMonitor m) throws CoreException {
		IProgressMonitor monitor;
		if (m == null)
			monitor = new NullProgressMonitor();
		else
			monitor = m;

		IWorkspaceRunnable action = new IWorkspaceRunnable() {
			public void run(IProgressMonitor actMonitor) throws CoreException {
				String taskName = NLS
						.bind(
								CoreText.CreateLocalBranchOperation_CreatingBranchMessage,
								name);
				actMonitor.beginTask(taskName, 1);
				Git git = new Git(repository);
				try {
					if (ref != null)
						git.branchCreate().setName(name).setStartPoint(ref.getName())
								.setUpstreamMode(SetupUpstreamMode.TRACK)
								.call();
					else
						git.branchCreate().setName(name).setStartPoint(commit)
								.setUpstreamMode(SetupUpstreamMode.NOTRACK)
								.call();
				} catch (Exception e) {
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
