package org.eclipse.egit.ui.internal.branch;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Repository;

interface IDebugUIPlugin {
	@Nullable
	public String getRunningLaunchConfigurationName(
			final Collection<Repository> repositories,
			IProgressMonitor monitor);
}
