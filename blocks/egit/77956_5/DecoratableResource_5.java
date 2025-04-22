package org.eclipse.egit.ui.internal.decorators;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class TestResourceMapping extends ResourceMapping {

	private IProject project;

	private IFile mainFile;

	private IFile[] mappedFiles;

	public TestResourceMapping(IProject project, IFile... mappedFiles) {
		this.project = project;
		this.mappedFiles = mappedFiles;
	}

	public TestResourceMapping(IFile mainFile, IFile... mappedFiles) {
		this.mainFile = mainFile;
		this.project = mainFile.getProject();
		this.mappedFiles = mappedFiles;
	}

	@Override
	public Object getModelObject() {
		if (mainFile != null) {
			return mainFile;
		}
		return null;
	}

	@Override
	public String getModelProviderId() {
		return null;
	}

	@Override
	public IProject[] getProjects() {
		return new IProject[] { project };
	}

	@Override
	public ResourceTraversal[] getTraversals(ResourceMappingContext context,
			IProgressMonitor monitor) throws CoreException {
		return new ResourceTraversal[] {
				new ResourceTraversal(mappedFiles,
						IResource.DEPTH_INFINITE, IResource.NONE) };
	}
}
