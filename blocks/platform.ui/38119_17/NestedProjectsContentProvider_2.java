package org.eclipse.ui.internal.navigator.resources.nested;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

public class NestedProjectManager {

	public static IProject getProject(IFolder folder) {
		if (folder == null) {
			return null;
		}
		IPath folderLocation = folder.getLocation();
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (project.getLocation().equals(folderLocation)) {
				return project;
			}
		}
		return null;
	}

	public static boolean isShownAsProject(IFolder folder) {
		return getProject(folder) != null;
	}

	public static boolean isShownAsNested(IProject project) {
		if (!project.exists()) {
			return false;
		}
		IPath queriedLocation = project.getLocation();
		for (IProject otherProject : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			IPath otherLocation = otherProject.getLocation();
			if (otherProject.isOpen() && queriedLocation.segmentCount() - otherLocation.segmentCount() > 0 && otherLocation.isPrefixOf(queriedLocation)) {
				return true;
			}
		}
		return false;
	}

	public static IContainer getMostDirectOpenContainer(IProject project) {
		IProject mostDirectParentProject = null;
		for (IProject other : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (!project.equals(other) && other.isOpen()) {
				IPath otherLocation = other.getLocation();
				if ((mostDirectParentProject == null || otherLocation.segmentCount() > mostDirectParentProject.getLocation().segmentCount())
					&& other.getLocation().isPrefixOf(project.getLocation())) {
					mostDirectParentProject = other;
				}
			}
		}
		if (mostDirectParentProject != null) {
			IPath parentContainerAbsolutePath = project.getLocation().removeLastSegments(1);
			if (parentContainerAbsolutePath.equals(mostDirectParentProject.getLocation())) {
				return mostDirectParentProject;
			} else {
				IPath parentFolderPathRelativeToProject = parentContainerAbsolutePath.removeFirstSegments(mostDirectParentProject.getLocation().segmentCount());
				return mostDirectParentProject.getFolder(parentFolderPathRelativeToProject);
			}
		}
		return null;
	}

}
