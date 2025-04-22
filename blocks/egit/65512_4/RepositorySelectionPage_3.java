package org.eclipse.egit.ui.internal.clone;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.op.CloneOperation.PostCloneTask;
import org.eclipse.egit.ui.internal.KnownHosts;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.PlatformUI;

public class RememberHostTask implements PostCloneTask {

	private final @NonNull String hostName;

	public RememberHostTask(@NonNull String hostName) {
		this.hostName = hostName;
	}

	@Override
	public void execute(Repository repository, IProgressMonitor monitor)
			throws CoreException {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				KnownHosts.addKnownHost(hostName);
			}
		});

	}

}
