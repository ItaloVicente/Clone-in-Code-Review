package org.eclipse.egit.core.internal;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.egit.core.internal.storage.GitFileRevision;
import org.eclipse.jgit.lib.Repository;

public class AdapterFactory implements IAdapterFactory {

	@Override
	@SuppressWarnings("unchecked")
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == Repository.class) {
			if (adaptableObject instanceof GitFileRevision) {
				return ((GitFileRevision) adaptableObject).getRepository();
			}
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class[] getAdapterList() {
		return new Class[] { Repository.class };
	}
}
