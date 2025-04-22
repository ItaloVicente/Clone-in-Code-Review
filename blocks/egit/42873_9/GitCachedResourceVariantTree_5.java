package org.eclipse.egit.core.internal.merge;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.internal.storage.IndexResourceVariant;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.variants.IResourceVariantTree;

public class DirCacheResourceVariantTreeProvider implements
		GitResourceVariantTreeProvider {
	private final IResourceVariantTree baseTree;

	private final IResourceVariantTree sourceTree;

	private final IResourceVariantTree remoteTree;

	private final Set<IResource> roots;

	private final Set<IResource> knownResources;

	public DirCacheResourceVariantTreeProvider(Repository repository)
			throws IOException {
		final DirCache cache = repository.readDirCache();
		final GitResourceVariantCache baseCache = new GitResourceVariantCache();
		final GitResourceVariantCache sourceCache = new GitResourceVariantCache();
		final GitResourceVariantCache remoteCache = new GitResourceVariantCache();

		for (int i = 0; i < cache.getEntryCount(); i++) {
			final DirCacheEntry entry = cache.getEntry(i);
			final IPath path = new Path(entry.getPathString());
			final IResource resource = ResourceUtil
					.getResourceHandleForLocation(path);
			if (resource == null || resource.getProject() == null
					|| !resource.getProject().isAccessible()) {
				continue;
			}
			switch (entry.getStage()) {
			case DirCacheEntry.STAGE_0:
				break;
			case DirCacheEntry.STAGE_1:
				baseCache.setVariant(resource,
						IndexResourceVariant.create(repository, entry));
				break;
			case DirCacheEntry.STAGE_2:
				sourceCache.setVariant(resource,
						IndexResourceVariant.create(repository, entry));
				break;
			case DirCacheEntry.STAGE_3:
				remoteCache.setVariant(resource,
						IndexResourceVariant.create(repository, entry));
				break;
			default:
				throw new IllegalStateException(
						"Invalid stage: " + entry.getStage()); //$NON-NLS-1$
			}
		}

		baseTree = new GitCachedResourceVariantTree(baseCache);
		sourceTree = new GitCachedResourceVariantTree(sourceCache);
		remoteTree = new GitCachedResourceVariantTree(remoteCache);

		roots = new LinkedHashSet<IResource>();
		roots.addAll(baseCache.getRoots());
		roots.addAll(sourceCache.getRoots());
		roots.addAll(remoteCache.getRoots());

		knownResources = new LinkedHashSet<IResource>();
		knownResources.addAll(baseCache.getKnownResources());
		knownResources.addAll(sourceCache.getKnownResources());
		knownResources.addAll(remoteCache.getKnownResources());
	}

	public IResourceVariantTree getBaseTree() {
		return baseTree;
	}

	public IResourceVariantTree getRemoteTree() {
		return remoteTree;
	}

	public IResourceVariantTree getSourceTree() {
		return sourceTree;
	}

	public Set<IResource> getKnownResources() {
		return knownResources;
	}

	public Set<IResource> getRoots() {
		return roots;
	}
}
