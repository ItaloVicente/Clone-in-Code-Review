package org.eclipse.egit.core.internal.merge;

import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.variants.IResourceVariant;
import org.eclipse.team.core.variants.IResourceVariantTree;

class GitCachedResourceVariantTree implements IResourceVariantTree {
	private final GitResourceVariantCache cache;

	public GitCachedResourceVariantTree(GitResourceVariantCache cache) {
		this.cache = cache;
	}

	public IResource[] roots() {
		final Set<IResource> roots = cache.getRoots();
		return roots.toArray(new IResource[roots.size()]);
	}

	public IResource[] members(IResource resource) throws TeamException {
		return cache.members(resource);
	}

	public IResourceVariant getResourceVariant(IResource resource)
			throws TeamException {
		return cache.getVariant(resource);
	}

	public boolean hasResourceVariant(IResource resource) throws TeamException {
		return cache.getVariant(resource) != null;
	}

	public IResource[] refresh(IResource[] resources, int depth,
			IProgressMonitor monitor) throws TeamException {
		return new IResource[0];
	}

	public void flushVariants(IResource resource, int depth)
			throws TeamException {
	}
}
