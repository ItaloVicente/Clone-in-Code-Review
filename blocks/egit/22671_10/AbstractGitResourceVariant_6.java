package org.eclipse.egit.core.internal.merge;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.internal.storage.TreeParserResourceVariant;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.team.core.variants.IResourceVariantTree;

public class TreeWalkResourceVariantTreeProvider implements
		GitResourceVariantTreeProvider {
	private final IResourceVariantTree baseTree;

	private final IResourceVariantTree oursTree;

	private final IResourceVariantTree theirsTree;

	private final Set<IResource> roots;

	private final Set<IResource> knownResources;

	public TreeWalkResourceVariantTreeProvider(Repository repository,
			TreeWalk treeWalk, int baseTreeIndex, int oursTreeIndex,
			int theirsTreeIndex) throws IOException {
		final AbstractTreeIterator[] initialTrees = new AbstractTreeIterator[treeWalk
				.getTreeCount()];
		for (int i = 0; i < treeWalk.getTreeCount(); i++)
			initialTrees[i] = treeWalk.getTree(i, AbstractTreeIterator.class);

		final GitResourceVariantCache baseCache = new GitResourceVariantCache();
		final GitResourceVariantCache theirsCache = new GitResourceVariantCache();
		final GitResourceVariantCache oursCache = new GitResourceVariantCache();

		while (treeWalk.next()) {
			final int modeBase = treeWalk.getRawMode(baseTreeIndex);
			final int modeTheirs = treeWalk.getRawMode(theirsTreeIndex);
			final int modeOurs = treeWalk.getRawMode(oursTreeIndex);
			if (modeBase == 0 && modeOurs == 0 && modeTheirs == 0)
				continue;

			final CanonicalTreeParser base = treeWalk.getTree(baseTreeIndex,
					CanonicalTreeParser.class);
			final CanonicalTreeParser theirs = treeWalk.getTree(
					theirsTreeIndex, CanonicalTreeParser.class);
			final CanonicalTreeParser ours = treeWalk.getTree(oursTreeIndex,
					CanonicalTreeParser.class);

			final IPath path = new Path(treeWalk.getPathString());
			final IResource resource = ResourceUtil
					.getResourceHandleForLocation(path);
			if (resource == null || resource.getProject().isAccessible()) {
				if (modeBase != 0)
					baseCache.setVariant(resource,
							TreeParserResourceVariant.create(repository, base));
				if (modeTheirs != 0)
					theirsCache.setVariant(resource,
							TreeParserResourceVariant.create(repository, theirs));
				if (modeOurs != 0)
					oursCache.setVariant(resource,
							TreeParserResourceVariant.create(repository, ours));
			}

			if (treeWalk.isSubtree())
				treeWalk.enterSubtree();
		}

		treeWalk.reset();
		for (int i = 0; i < initialTrees.length; i++) {
			initialTrees[i].reset();
			treeWalk.addTree(initialTrees[i]);
		}

		baseTree = new GitCachedResourceVariantTree(baseCache);
		theirsTree = new GitCachedResourceVariantTree(theirsCache);
		oursTree = new GitCachedResourceVariantTree(oursCache);

		roots = new LinkedHashSet<IResource>();
		roots.addAll(baseCache.getRoots());
		roots.addAll(oursCache.getRoots());
		roots.addAll(theirsCache.getRoots());

		knownResources = new LinkedHashSet<IResource>();
		knownResources.addAll(baseCache.getKnownResources());
		knownResources.addAll(oursCache.getKnownResources());
		knownResources.addAll(theirsCache.getKnownResources());
	}

	public IResourceVariantTree getBaseTree() {
		return baseTree;
	}

	public IResourceVariantTree getRemoteTree() {
		return theirsTree;
	}

	public IResourceVariantTree getSourceTree() {
		return oursTree;
	}

	public Set<IResource> getKnownResources() {
		return knownResources;
	}

	public Set<IResource> getRoots() {
		return roots;
	}
}
