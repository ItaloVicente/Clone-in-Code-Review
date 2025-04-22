package org.eclipse.egit.ui.internal.staging;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

public class ResourceNodeAdapterFactory implements IAdapterFactory {

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		return ((ResourceNode) adaptableObject).getResource();
	}

	public Class[] getAdapterList() {
		return new Class[] { IResource.class };
	}

}
