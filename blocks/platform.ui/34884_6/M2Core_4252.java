package org.eclipse.ui.tests.navigator.m12.model;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.tests.navigator.m12.model.M1Core;
import org.eclipse.ui.tests.navigator.m12.model.ResourceWrapper;
public class M1Resource extends ResourceWrapper {
	public M1Resource(IResource resource) {
		super(resource);
	}
	protected ResourceWrapper getModelObject(IResource resource) {
		return M1Core.getModelObject(resource);
	}
	public String getModelId() {
		return "M1";
	}
}
