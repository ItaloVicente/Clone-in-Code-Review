package org.eclipse.ui.tests.navigator.m12.model;
import org.eclipse.core.resources.IResource;
public class M2Resource extends ResourceWrapper {
	public M2Resource(IResource resource) {
		super(resource);
	}
	@Override
	protected ResourceWrapper getModelObject(IResource resource) {
		return M2Core.getModelObject(resource);
	}
	@Override
	public String getModelId() {
		return "M2";
	}
}
