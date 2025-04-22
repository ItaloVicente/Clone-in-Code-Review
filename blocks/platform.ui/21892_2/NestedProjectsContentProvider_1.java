package org.eclipse.ui.internal.navigator.resources.nestedProjects;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

public class NestedProjectManager {

	private static Set<IFolder> shownAsProject;

	public static void registerProjectShownInFolder(IFolder folder, IProject project) {
		if (shownAsProject == null) {
			shownAsProject = new HashSet<IFolder>();
		}
		shownAsProject.add(folder);
	}
	
	public static void unregisterProjectShownInFolder(IFolder targetFolder) {
		if (shownAsProject != null) {
			shownAsProject.remove(targetFolder);
		}
	}


	public static boolean isShownAsProject(IFolder folder) {
		return shownAsProject != null && shownAsProject.contains(folder);
	}


}
