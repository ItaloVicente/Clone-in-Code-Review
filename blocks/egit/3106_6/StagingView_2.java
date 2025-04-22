package org.eclipse.egit.ui.internal.staging;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

public class StagedNodeAdapterFactory implements IAdapterFactory {

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		return ((StagedNode)adaptableObject).getAdapter(IResource.class);
	}

	public Class[] getAdapterList() {
		return new Class[] { IResource.class };
	}

}
