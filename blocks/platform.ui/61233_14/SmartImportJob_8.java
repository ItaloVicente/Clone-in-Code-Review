package org.eclipse.ui.internal.wizards.datatransfer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.wizards.datatransfer.ProjectConfigurator;

public interface RecursiveImportListener {

	public void projectCreated(IProject project);

	public void projectConfigured(IProject project, ProjectConfigurator configurator);

	public void errorHappened(IPath location, Exception ex);

}
