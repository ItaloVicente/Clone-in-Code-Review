package org.eclipse.egit.ui.internal.staging;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jgit.lib.Repository;

public class StagingEntryAdapterFactory implements IAdapterFactory {

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adaptableObject != null) {
			StagingEntry entry = (StagingEntry) adaptableObject;
			if (adapterType == IResource.class) {
				IResource resource = entry.getFile();
				if (resource != null && resource.isAccessible()) {
					return resource;
				}
			} else if (adapterType == IPath.class) {
				return entry.getLocation();
			} else if (adapterType == Repository.class) {
				return entry.getRepository();
			}
		}
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[] { IResource.class, IPath.class, Repository.class };
	}

}
