package org.eclipse.egit.core.op;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.op.CloneOperation.PostCloneTask;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;

public class ConfigureFetchAfterCloneTask implements PostCloneTask {

	private String fetchRefSpec;

	private final String remoteName;

	public ConfigureFetchAfterCloneTask(String remoteName, String fetchRefSpec) {
		this.remoteName = remoteName;
		this.fetchRefSpec = fetchRefSpec;
	}

	public void execute(Repository repository, IProgressMonitor monitor)
			throws CoreException {
		try {
			RemoteConfig configToUse = new RemoteConfig(
					repository.getConfig(), remoteName);
			if (fetchRefSpec != null)
				configToUse.addFetchRefSpec(new RefSpec(fetchRefSpec));
			configToUse.update(repository.getConfig());
			repository.getConfig().save();
			Git git = new Git(repository);
			git.fetch().call();
		} catch (Exception e) {
			throw new CoreException(Activator.error(e.getMessage(), e));
		}

	}

}
