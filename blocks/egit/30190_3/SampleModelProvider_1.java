package org.eclipse.egit.core.test.models;

import org.eclipse.core.resources.mapping.ModelProvider;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.team.core.mapping.IResourceMappingMerger;

public class SampleModelAdapterFactory implements IAdapterFactory {
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adaptableObject instanceof SampleModelProvider
				&& IResourceMappingMerger.class.isAssignableFrom(adapterType)) {
			return new SampleResourceMappingMerger(
					(ModelProvider) adaptableObject);
		}
		return null;
	}

	public Class[] getAdapterList() {
		return new Class[] { IResourceMappingMerger.class, };
	}
}
