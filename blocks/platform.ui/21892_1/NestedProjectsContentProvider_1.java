package org.eclipse.ui.internal.navigator.resources.nestedProjects;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

public class NestedProjectUtils {

	private static Map<IProject, IFolder> placeholders;

	public static void registerProjectShownInFolder(IFolder folder, IProject project) {
		if (placeholders == null) {
			placeholders = new HashMap<IProject, IFolder>();
		}
		placeholders.put(project, folder);
	}

	public static boolean isShownAsProject(IFolder folder) {
		return placeholders != null && placeholders.containsValue(folder);
	}

	public static boolean isShownAsNested(IProject element) {
		return placeholders != null && placeholders.containsValue(element);
	}

	public static IFolder getAssociatedFolder(IProject element) {
		if (placeholders != null) {
			return placeholders.get(element);
		}
		return null;
	}
	
}
