package org.eclipse.egit.ui.internal.clone;


import java.io.File;

import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.egit.ui.UIText;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.wizards.datatransfer.IImportStructureProvider;

class ProjectRecord {
	File projectSystemFile;

	String projectName;

	Object parent;

	int level;

	IProjectDescription description;

	ProjectRecord(File file) {
		projectSystemFile = file;
		setProjectName();
	}

	ProjectRecord(Object parent, int level) {
		this.parent = parent;
		this.level = level;
		setProjectName();
	}

	private void setProjectName() {
		try {
			if (projectName == null) {
				IPath path = new Path(projectSystemFile.getPath());
				if (isDefaultLocation(path)) {
					projectName = path.segment(path.segmentCount() - 2);
					description = ResourcesPlugin.getWorkspace()
							.newProjectDescription(projectName);
				} else {
					description = ResourcesPlugin.getWorkspace()
							.loadProjectDescription(path);
					projectName = description.getName();
				}

			}
		} catch (CoreException e) {
		}
	}

	private boolean isDefaultLocation(IPath path) {
		if (path.segmentCount() < 2)
			return false;
		return path.removeLastSegments(2).toFile().equals(
				Platform.getLocation().toFile());
	}

	public String getProjectName() {
		return projectName;
	}

	public String getProjectLabel(IImportStructureProvider structureProvider) {
		if (description == null)
			return projectName;

		String path = projectSystemFile == null ? structureProvider
				.getLabel(parent) : projectSystemFile.getParent();

		return NLS.bind(UIText.WizardProjectsImportPage_projectLabel,
				projectName, path);
	}

	@Override
	public String toString() {
		return projectName;
	}
}
