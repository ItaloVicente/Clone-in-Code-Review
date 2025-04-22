package org.eclipse.egit.ui.internal.branch;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.containers.ProjectSourceContainer;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jgit.annotations.Nullable;

public final class LaunchFinder {

	private LaunchFinder() {
	}

	@Nullable
	public static ILaunchConfiguration findLaunch(Set<IProject> projects,
			IProgressMonitor monitor) {
		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		ILaunch[] launches = launchManager.getLaunches();
		SubMonitor progress = SubMonitor.convert(monitor,
				UIText.BranchOperationUI_SearchLaunchConfiguration,
				launches.length);
		for (ILaunch launch : launches) {
			if (progress.isCanceled()) {
				break;
			}
			if (launch.isTerminated()) {
				progress.worked(1);
				continue;
			}
			ISourceLocator locator = launch.getSourceLocator();
			if (locator instanceof ISourceLookupDirector) {
				ISourceLookupDirector director = (ISourceLookupDirector) locator;
				ISourceContainer[] containers = director.getSourceContainers();
				if (isAnyProjectInSourceContainers(containers, projects,
						progress.newChild(1))) {
					return launch.getLaunchConfiguration();
				}
			} else {
				progress.worked(1);
			}
		}
		return null;
	}

	private static boolean isAnyProjectInSourceContainers(
			ISourceContainer[] containers, Set<IProject> projects,
			IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor, containers.length);
		for (ISourceContainer container : containers) {
			if (progress.isCanceled()) {
				break;
			}
			if (container instanceof ProjectSourceContainer) {
				ProjectSourceContainer projectContainer = (ProjectSourceContainer) container;
				if (projects.contains(projectContainer.getProject())) {
					progress.worked(1);
					return true;
				}
			}
			try {
				boolean found = isAnyProjectInSourceContainers(
						container.getSourceContainers(), projects,
						progress.newChild(1));
				if (found) {
					return true;
				}
			} catch (CoreException e) {
			}
		}
		return false;
	}

}
