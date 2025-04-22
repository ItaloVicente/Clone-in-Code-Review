package org.eclipse.egit.core.op;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.op.CloneOperation.PostCloneTask;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;

public class SetChangeIdTask implements PostCloneTask {

	private final boolean createchangeid;

	public SetChangeIdTask(boolean createchangeid) {
		this.createchangeid = createchangeid;
	}

	public void execute(Repository repository, IProgressMonitor monitor)
			throws CoreException {
		try {
			repository.getConfig().setBoolean(ConfigConstants.CONFIG_GERRIT_SECTION,
					null, ConfigConstants.CONFIG_KEY_CREATECHANGEID, createchangeid);
			repository.getConfig().save();
		} catch (IOException e) {
			throw new CoreException(Activator.error(e.getMessage(), e));
		}
	}

}
