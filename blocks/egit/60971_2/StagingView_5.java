package org.eclipse.egit.ui.internal.resources;

import static org.eclipse.jgit.lib.Repository.stripWorkDir;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffCacheEntry;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffData;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.internal.resources.IResourceState.Staged;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Repository;

public class ResourceStateFactory {

	private static ResourceStateFactory INSTANCE = new ResourceStateFactory();

	public static ResourceStateFactory getInstance() {
		return INSTANCE;
	}

	@Nullable
	public IndexDiffData getIndexDiffDataOrNull(@Nullable IResource resource) {
		if (resource == null || resource.getType() == IResource.ROOT) {
			return null;
		}

		if (!resource.exists() && !resource.isPhantom()) {
			return null;
		}

		final RepositoryMapping mapping = RepositoryMapping
				.getMapping(resource);
		if (mapping == null) {
			return null;
		}

		Repository repo = mapping.getRepository();
		if (repo == null) {
			return null;
		}

		if (repo.isBare()) {
			return new IndexDiffData();
		}

		if (mapping.getRepoRelativePath(resource) == null) {
			return null;
		}

		IndexDiffCacheEntry diffCacheEntry = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache().getIndexDiffCacheEntry(repo);
		if (diffCacheEntry == null) {
			return null;
		}
		return diffCacheEntry.getIndexDiff();

	}

	public IResourceState get(@NonNull IndexDiffData indexDiffData,
			@NonNull IResource resource) {
		ResourceState result = new ResourceState();
		switch (resource.getType()) {
		case IResource.FILE:
			extractResourceProperties(indexDiffData, resource, result);
			break;
		case IResource.PROJECT:
		case IResource.FOLDER:
			extractContainerProperties(indexDiffData, resource, result);
			break;
		default:
		}
		return result;
	}

	private void extractResourceProperties(@NonNull IndexDiffData indexDiffData,
			@NonNull IResource resource, @NonNull ResourceState state) {
		final RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
		if (mapping == null) {
			return;
		}
		Repository repository = mapping.getRepository();
		String repoRelativePath = makeRepositoryRelative(repository, resource);
		if (repoRelativePath == null) {
			return;
		}
		Set<String> ignoredFiles = indexDiffData.getIgnoredNotInIndex();
		boolean ignored = ignoredFiles.contains(repoRelativePath)
				|| containsPrefixPath(ignoredFiles, repoRelativePath);
		state.setIgnored(ignored);
		Set<String> untracked = indexDiffData.getUntracked();
		state.setTracked(!ignored && !untracked.contains(repoRelativePath));

		Set<String> added = indexDiffData.getAdded();
		Set<String> removed = indexDiffData.getRemoved();
		Set<String> changed = indexDiffData.getChanged();
		if (added.contains(repoRelativePath)) {
			state.setStaged(Staged.ADDED);
		} else if (removed.contains(repoRelativePath)) {
			state.setStaged(Staged.REMOVED);
		} else if (changed.contains(repoRelativePath)) {
			state.setStaged(Staged.MODIFIED);
		} else {
			state.setStaged(Staged.NOT_STAGED);
		}

		Set<String> conflicting = indexDiffData.getConflicting();
		state.setConflicts(conflicting.contains(repoRelativePath));

		Set<String> modified = indexDiffData.getModified();
		state.setDirty(modified.contains(repoRelativePath));
	}

	private void extractContainerProperties(
			@NonNull IndexDiffData indexDiffData, @NonNull IResource resource,
			@NonNull ResourceState state) {
		final RepositoryMapping mapping = RepositoryMapping
				.getMapping(resource);
		if (mapping == null) {
			return;
		}
		Repository repository = mapping.getRepository();
		if (repository == null) {
			return;
		}
		String repoRelative = makeRepositoryRelative(repository, resource);
		if (repoRelative == null) {
			return;
		}
		String repoRelativePath = repoRelative + "/"; //$NON-NLS-1$

		if (ResourceUtil.isSymbolicLink(repository, repoRelativePath)) {
			extractResourceProperties(indexDiffData, resource, state);
			return;
		}

		Set<String> ignoredFiles = indexDiffData.getIgnoredNotInIndex();
		Set<String> untrackedFolders = indexDiffData.getUntrackedFolders();
		boolean ignored = containsPrefixPath(ignoredFiles, repoRelativePath)
				|| !hasContainerAnyFiles(resource);
		state.setIgnored(ignored);
		state.setTracked(!ignored
				&& !containsPrefixPath(untrackedFolders, repoRelativePath));

		Set<String> changed = new HashSet<String>(indexDiffData.getChanged());
		changed.addAll(indexDiffData.getAdded());
		changed.addAll(indexDiffData.getRemoved());
		if (containsPrefix(changed, repoRelativePath)) {
			state.setStaged(Staged.MODIFIED);
		} else {
			state.setStaged(Staged.NOT_STAGED);
		}
		Set<String> conflicting = indexDiffData.getConflicting();
		state.setConflicts(containsPrefix(conflicting, repoRelativePath));

		Set<String> modified = indexDiffData.getModified();
		Set<String> untracked = indexDiffData.getUntracked();
		Set<String> missing = indexDiffData.getMissing();
		state.setDirty(containsPrefix(modified, repoRelativePath)
				|| containsPrefix(untracked, repoRelativePath)
				|| containsPrefix(missing, repoRelativePath));
	}

	@Nullable
	private String makeRepositoryRelative(Repository repository,
			IResource res) {
		IPath location = res.getLocation();
		if (location == null) {
			return null;
		}
		if (repository.isBare()) {
			return null;
		}
		File workTree = repository.getWorkTree();
		return stripWorkDir(workTree, location.toFile());
	}

	private boolean containsPrefix(Set<String> collection, String prefix) {
		if (prefix.length() == 1 && !collection.isEmpty())
			return true;

		for (String path : collection)
			if (path.startsWith(prefix))
				return true;
		return false;
	}

	private boolean containsPrefixPath(Set<String> collection, String path) {
		for (String entry : collection) {
			String entryPath;
			if (entry.endsWith("/")) //$NON-NLS-1$
				entryPath = entry;
			else
				entryPath = entry + "/"; //$NON-NLS-1$
			if (path.startsWith(entryPath))
				return true;
		}
		return false;
	}

	private boolean hasContainerAnyFiles(IResource resource) {
		if (resource instanceof IContainer) {
			IContainer container = (IContainer) resource;
			try {
				return anyFile(container.members());
			} catch (CoreException e) {
				return true;
			}
		}
		throw new IllegalArgumentException("Expected a container resource."); //$NON-NLS-1$
	}

	private boolean anyFile(IResource[] members) {
		for (IResource member : members) {
			if (member.getType() == IResource.FILE)
				return true;
			else if (member.getType() == IResource.FOLDER)
				if (hasContainerAnyFiles(member))
					return true;
		}
		return false;
	}

}
