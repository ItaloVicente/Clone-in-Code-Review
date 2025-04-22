package org.eclipse.ui.wizards.datatransfer;

import java.io.File;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.IWizard;

public interface ProjectConfigurator {

	public Set<File> findConfigurableLocations(File root, IProgressMonitor monitor);

	public boolean shouldBeAnEclipseProject(IContainer container, IProgressMonitor monitor);

	public Set<IFolder> getDirectoriesToIgnore(IProject project, IProgressMonitor monitor);

	public boolean canConfigure(IProject project, Set<IPath> ignoredPaths, IProgressMonitor monitor);

	@Deprecated
	public IWizard getConfigurationWizard();

	public void configure(IProject project, Set<IPath> excludedDirectories, IProgressMonitor monitor);

}
