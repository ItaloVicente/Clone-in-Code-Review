package org.eclipse.ui.tests.markers;

import org.eclipse.core.resources.*;
import org.eclipse.core.resources.mapping.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.tests.TestPlugin;

public class TestResourceMapping extends ResourceMapping {

	IResource element;
	private TestResourceMapping parent;

	public TestResourceMapping(IResource resource) {
		element = resource;
	}

	@Override
	public Object getModelObject() {
		return element;
	}

	@Override
	public String getModelProviderId() {
		return ModelProvider.RESOURCE_MODEL_PROVIDER_ID;
	}

	@Override
	public IProject[] getProjects() {
		if (element.getType() == IResource.ROOT) {
			return ((IWorkspaceRoot) element).getProjects();
		}
		return new IProject[] { element.getProject() };
	}

	@Override
	public ResourceTraversal[] getTraversals(ResourceMappingContext context,
			IProgressMonitor monitor) {
		ResourceTraversal traversal = new ResourceTraversal(
				new IResource[] { element }, IResource.DEPTH_INFINITE,
				IResource.NONE);
		return new ResourceTraversal[] { traversal };
	}

	public String getName() {
		return element.getName();
	}

	public TestResourceMapping[] getChildren() {
		if (element.getType() == IResource.FILE) {
			return new TestResourceMapping[0];
		}
		IResource[] children;
		try {
			children = ((IContainer) element).members();
		} catch (CoreException e) {
			TestPlugin.getDefault().getLog().log(e.getStatus());
			return new TestResourceMapping[0];
		}
		TestResourceMapping[] result = new TestResourceMapping[children.length];

		for (int i = 0; i < children.length; i++) {
			result[i] = new TestResourceMapping(children[i]);
			result[i].setParent(this);
		}

		return result;

	}

	public void setParent(TestResourceMapping mapping) {
		parent = mapping;

	}

	public TestResourceMapping getParent() {
		return parent;
	}

}
