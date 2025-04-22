package org.eclipse.egit.ui.view.synchronize;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ModelProvider;
import org.eclipse.core.resources.mapping.RemoteResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class MockLogicalModelProvider extends ModelProvider {
	public static final String ID = "org.eclipse.egit.ui.test.mockLogicalModelProvider";

	public static final String MOCK_LOGICAL_FILE_EXTENSION = "mocklogical";

	@Override
	public ResourceMapping[] getMappings(IResource resource,
			ResourceMappingContext context, IProgressMonitor monitor)
			throws CoreException {
		if (resource instanceof IFile && MOCK_LOGICAL_FILE_EXTENSION
				.equals(resource.getFileExtension())) {
			return new ResourceMapping[] { new MockLogicalResourceMapping(
					(IFile) resource, ID), };
		}
		return super.getMappings(resource, context, monitor);
	}
}
