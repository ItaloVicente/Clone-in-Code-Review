package org.eclipse.egit.core.op;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.op.CloneOperation.PostCloneTask;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public class ConfigurePushAfterCloneTask implements PostCloneTask {

	private String pushRefSpec;

	private URIish pushURI;

	private final String remoteName;

	public ConfigurePushAfterCloneTask(String remoteName, String pushRefSpec, URIish pushURI) {
		this.remoteName = remoteName;
		this.pushRefSpec = pushRefSpec;
		this.pushURI = pushURI;
	}

	public void execute(Repository repository, IProgressMonitor monitor)
			throws CoreException {
		try {
			RemoteConfig configToUse = new RemoteConfig(
					repository.getConfig(), remoteName);
			if (pushRefSpec != null)
				configToUse.addPushRefSpec(new RefSpec(pushRefSpec));
			if (pushURI != null)
				configToUse.addPushURI(pushURI);
			configToUse.update(repository.getConfig());
			repository.getConfig().save();
		} catch (Exception e) {
			throw new CoreException(Activator.error(e.getMessage(), e));
		}

	}

}
