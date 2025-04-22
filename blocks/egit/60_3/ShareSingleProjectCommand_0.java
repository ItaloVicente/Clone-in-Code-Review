package org.eclipse.egit.ui.internal.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.IParameterValues;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.team.core.RepositoryProvider;

public class ProjectNameParameterValues implements IParameterValues {

	public Map getParameterValues() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();
		Map<String, String> paramValues = new HashMap<String, String>();
		for (IProject project : projects) {
			final boolean notAlreadyShared = RepositoryProvider
					.getProvider(project) == null;
			if (project.isAccessible() && notAlreadyShared)
				paramValues.put(project.getName(), project.getName());
		}
		return paramValues;
	}

}
