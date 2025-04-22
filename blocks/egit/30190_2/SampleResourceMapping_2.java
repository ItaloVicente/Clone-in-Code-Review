package org.eclipse.egit.core.test.models;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ModelProvider;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class SampleModelProvider extends ModelProvider {
	public static final String SAMPLE_PROVIDER_ID = "org.eclipse.egit.core.tests.sampleModelProvider";

	public static final String SAMPLE_FILE_EXTENSION = "sample";

	@Override
	public ResourceMapping[] getMappings(IResource resource,
			ResourceMappingContext context, IProgressMonitor monitor)
			throws CoreException {
		if (resource instanceof IFile
				&& SAMPLE_FILE_EXTENSION.equals(resource.getFileExtension())) {
			return new ResourceMapping[] { new SampleResourceMapping(
					(IFile) resource, SAMPLE_PROVIDER_ID), };
		}
		return super.getMappings(resource, context, monitor);
	}
}
