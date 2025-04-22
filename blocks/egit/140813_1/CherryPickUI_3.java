package org.eclipse.egit.ui.internal.branch;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jgit.lib.Repository;

class NoDebugUIPlugin implements IDebugUIPlugin {
	@Override
	public String getRunningLaunchConfigurationName(
			Collection<Repository> repositories, IProgressMonitor monitor) {
		return null;
	}
}
