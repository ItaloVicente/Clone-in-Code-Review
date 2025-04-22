package org.eclipse.egit.core.internal.merge;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.team.core.variants.IResourceVariant;

class GitResourceVariantCache {
	private final Map<IResource, IResourceVariant> cache = new LinkedHashMap<IResource, IResourceVariant>();

	private final Map<IResource, Set<IResource>> members = new LinkedHashMap<IResource, Set<IResource>>();

	private final Set<IResource> roots = new LinkedHashSet<IResource>();

	public void setVariant(IResource resource, IResourceVariant variant) {
		cache.put(resource, variant);

		IProject project = resource.getProject();
		roots.add(project);

		members.put(resource, new LinkedHashSet<IResource>());

		final IResource parent = resource.getParent();
		Set<IResource> parentMembers = members.get(parent);
		if (parentMembers == null) {
			parentMembers = new LinkedHashSet<IResource>();
			members.put(parent, parentMembers);
		}
		parentMembers.add(resource);
	}

	public IResourceVariant getVariant(IResource resource) {
		return cache.get(resource);
	}

	public Set<IResource> getRoots() {
		return Collections.unmodifiableSet(roots);
	}

	public Set<IResource> getKnownResources() {
		return Collections.unmodifiableSet(cache.keySet());
	}

	public IResource[] members(IResource resource) {
		final Set<IResource> children = members.get(resource);
		return children.toArray(new IResource[children.size()]);
	}
}
