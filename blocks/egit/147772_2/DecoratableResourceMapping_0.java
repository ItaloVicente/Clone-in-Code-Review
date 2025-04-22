package org.eclipse.egit.ui.internal.decorators;

import org.eclipse.core.resources.mapping.ResourceMapping;

public abstract class DecoratableResourceGroup extends DecoratableResource {

	protected boolean someShared = false;

	protected DecoratableResourceGroup(ResourceMapping mapping) {
		super(null); // No resource
	}

	public boolean hasSharedResources() {
		return someShared;
	}

}
