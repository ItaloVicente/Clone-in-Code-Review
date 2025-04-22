package org.eclipse.egit.core.synchronize;

import org.eclipse.core.resources.IResource;
import org.eclipse.team.core.variants.IResourceVariant;

abstract class GitResourceVariant implements IResourceVariant {

	private IResource resource;

	GitResourceVariant(IResource resource) {
		this.resource = resource;
	}

	public IResource getResource() {
		return resource;
	}

	public String getName() {
		return resource.getName();
	}

	public byte[] asBytes() {
		return getContentIdentifier().getBytes();
	}

}
