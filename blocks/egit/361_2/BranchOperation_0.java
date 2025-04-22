package org.eclipse.egit.core.internal.util;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.egit.core.CoreText;
import org.eclipse.jgit.lib.Repository;

public class ProjectUtil {
	public static void refreshProjects(Repository repository,
			IProgressMonitor monitor) throws CoreException {
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		try {
			monitor.beginTask(CoreText.ProjectUtil_refreshingProjects,
					projects.length);
			final File parentFile = repository.getWorkDir();
			for (IProject p : projects) {
				if (monitor.isCanceled())
					break;
				final File file = p.getLocation().toFile();
				if (file.getAbsolutePath().startsWith(
						parentFile.getAbsolutePath())) {
					p.refreshLocal(IResource.DEPTH_INFINITE,
							new SubProgressMonitor(monitor, 1));
					monitor.worked(1);
				}
			}
		} finally {
			monitor.done();
		}
	}
}
