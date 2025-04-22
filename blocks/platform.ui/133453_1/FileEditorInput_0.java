package org.eclipse.ui.part;

import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.PlatformObject;

class AdaptablePlatformObject extends PlatformObject {

	@Override
	public <T> T getAdapter(Class<T> adapterType) {
		try {
			return super.getAdapter(adapterType);
		} catch (InvalidRegistryObjectException e) {
			return null;
		}
	}

}
