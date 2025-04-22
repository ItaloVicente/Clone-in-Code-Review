package org.eclipse.egit.ui.internal.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffCacheEntry;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffData;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.ui.internal.resources.IResourceState.StagingState;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;

public class ResourceStateFactory {

	@NonNull
	public static final IResourceState UNKNOWN_STATE = new ResourceState();

	@NonNull
	private static final ResourceStateFactory INSTANCE = new ResourceStateFactory();

	@NonNull
	public static ResourceStateFactory getInstance() {
		return INSTANCE;
	}

	@Nullable
	public IndexDiffData getIndexDiffDataOrNull(@Nullable IResource resource) {
		if (resource == null || resource.getType() == IResource.ROOT
				|| !ResourceUtil.isSharedWithGit(resource)) {
			return null;
		}
		Repository repository = ResourceUtil.getRepository(resource);
		return getIndexDiffDataOrNull(repository);
	}

	@Nullable
	public IndexDiffData getIndexDiffDataOrNull(@Nullable File file) {
		if (file == null) {
			return null;
		}
		File absoluteFile = file.getAbsoluteFile();
		IPath path = new org.eclipse.core.runtime.Path(absoluteFile.getPath());
		Repository repository = ResourceUtil.getRepository(path);
		return getIndexDiffDataOrNull(repository);
	}

	@Nullable
	private IndexDiffData getIndexDiffDataOrNull(
			@Nullable Repository repository) {
		if (repository == null) {
			return null;
		} else if (repository.isBare()) {
			return new IndexDiffData();
		}
		IndexDiffCacheEntry diffCacheEntry = Activator.getDefault()
				.getIndexDiffCache().getIndexDiffCacheEntry(repository);
		if (diffCacheEntry == null) {
			return null;
		}
		return diffCacheEntry.getIndexDiff();
	}

	@NonNull
	public IResourceState get(@Nullable IResource resource) {
		IndexDiffData indexDiffData = getIndexDiffDataOrNull(resource);
		if (indexDiffData == null || resource == null) {
			return UNKNOWN_STATE;
		}
		return get(indexDiffData, resource);
	}

	@NonNull
	public IResourceState get(@Nullable File file) {
		IndexDiffData indexDiffData = getIndexDiffDataOrNull(file);
		if (indexDiffData == null || file == null) {
			return UNKNOWN_STATE;
		}
		return get(indexDiffData, file);
	}

	@NonNull
	public IResourceState get(@NonNull IndexDiffData indexDiffData,
			@NonNull IResource resource) {
		IPath path = resource.getLocation();
		if (path != null) {
			return get(indexDiffData, new ResourceItem(resource));
		}
		return UNKNOWN_STATE;
	}

	@NonNull
	public IResourceState get(@NonNull IndexDiffData indexDiffData,
			@NonNull File file) {
		return get(indexDiffData, new FileItem(file));
	}

	@NonNull
	private IResourceState get(@NonNull IndexDiffData indexDiffData,
			@NonNull FileSystemItem file) {
		IPath path = file.getAbsolutePath();
		if (path == null) {
			return UNKNOWN_STATE;
		}
		Repository repository = file.getRepository();
		if (repository == null || repository.isBare()) {
			return UNKNOWN_STATE;
		}
		File workTree = repository.getWorkTree();
		String repoRelativePath = path.makeRelativeTo(
				new org.eclipse.core.runtime.Path(workTree.getAbsolutePath()))
				.toString();
		if (repoRelativePath.equals(path.toString())) {
			return UNKNOWN_STATE;
		}
		ResourceState result = new ResourceState();
		if (file.isContainer()) {
			if (!repoRelativePath.endsWith("/")) { //$NON-NLS-1$
				repoRelativePath += '/';
			}
			if (ResourceUtil.isSymbolicLink(repository, repoRelativePath)) {
				extractFileProperties(indexDiffData, repoRelativePath, result);
			} else {
				extractContainerProperties(indexDiffData, repoRelativePath,
						file, result);
			}
		} else {
			extractFileProperties(indexDiffData, repoRelativePath, result);
		}
		return result;
	}

	private void extractFileProperties(@NonNull IndexDiffData indexDiffData,
			@NonNull String repoRelativePath, @NonNull ResourceState state) {
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
			state.setStagingState(StagingState.ADDED);
		} else if (removed.contains(repoRelativePath)) {
			state.setStagingState(StagingState.REMOVED);
		} else if (changed.contains(repoRelativePath)) {
			state.setStagingState(StagingState.MODIFIED);
		} else {
			state.setStagingState(StagingState.NOT_STAGED);
		}

		Set<String> conflicting = indexDiffData.getConflicting();
		state.setConflicts(conflicting.contains(repoRelativePath));

		Set<String> modified = indexDiffData.getModified();
		state.setDirty(modified.contains(repoRelativePath));

		Set<String> missing = indexDiffData.getMissing();
		state.setMissing(missing.contains(repoRelativePath));

		Set<String> assumeUnchanged = indexDiffData.getAssumeUnchanged();
		state.setAssumeUnchanged(assumeUnchanged.contains(repoRelativePath));
	}

	private void extractContainerProperties(
			@NonNull IndexDiffData indexDiffData,
			@NonNull String repoRelativePath, @NonNull FileSystemItem directory,
			@NonNull ResourceState state) {
		Set<String> ignoredFiles = indexDiffData.getIgnoredNotInIndex();
		Set<String> untrackedFolders = indexDiffData.getUntrackedFolders();
		boolean ignored = containsPrefixPath(ignoredFiles, repoRelativePath)
				|| !directory.hasContainerAnyFiles();
		state.setIgnored(ignored);
		state.setTracked(!ignored
				&& !containsPrefixPath(untrackedFolders, repoRelativePath));

		Set<String> changed = new HashSet<>(indexDiffData.getChanged());
		changed.addAll(indexDiffData.getAdded());
		changed.addAll(indexDiffData.getRemoved());
		if (containsPrefix(changed, repoRelativePath)) {
			state.setStagingState(StagingState.MODIFIED);
		} else {
			state.setStagingState(StagingState.NOT_STAGED);
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

	private interface FileSystemItem {
		boolean hasContainerAnyFiles();

		boolean isContainer();

		@Nullable
		IPath getAbsolutePath();

		@Nullable
		Repository getRepository();
	}

	private static class FileItem implements FileSystemItem {

		@NonNull
		private final File file;

		public FileItem(@NonNull File file) {
			this.file = file;
		}

		@Override
		@NonNull
		public IPath getAbsolutePath() {
			return new org.eclipse.core.runtime.Path(file.getAbsolutePath());
		}

		@Override
		public Repository getRepository() {
			return ResourceUtil.getRepository(getAbsolutePath());
		}

		@Override
		public boolean isContainer() {
			return file.isDirectory();
		}

		@Override
		public boolean hasContainerAnyFiles() {
			if (!isContainer()) {
				throw new IllegalArgumentException("Container expected"); //$NON-NLS-1$
			}
			try {
				final boolean[] result = new boolean[] { false };
				final Path dotGit = Paths.get(Constants.DOT_GIT);
				Files.walkFileTree(file.toPath(), new FileVisitor<Path>() {
					@Override
					public FileVisitResult preVisitDirectory(Path dir,
							BasicFileAttributes attrs) throws IOException {
						if (dotGit.equals(dir.getFileName())) {
							return FileVisitResult.SKIP_SUBTREE;
						}
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(Path path,
							BasicFileAttributes attrs) throws IOException {
						if (!attrs.isDirectory()) {
							result[0] = true;
							return FileVisitResult.TERMINATE;
						}
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path path,
							IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir,
							IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}
				});
				return result[0];
			} catch (IOException e) {
				return true;
			}
		}
	}

	private static class ResourceItem implements FileSystemItem {

		@NonNull
		private final IResource resource;

		public ResourceItem(@NonNull IResource resource) {
			this.resource = resource;
		}

		@Override
		@Nullable
		public IPath getAbsolutePath() {
			return resource.getLocation();
		}

		@Override
		public Repository getRepository() {
			return ResourceUtil.getRepository(resource);
		}

		@Override
		public boolean isContainer() {
			return isContainer(resource);
		}

		@Override
		public boolean hasContainerAnyFiles() {
			return containsFiles(resource);
		}

		private boolean isContainer(IResource rsc) {
			int type = rsc.getType();
			return type == IResource.FOLDER || type == IResource.PROJECT
					|| type == IResource.ROOT;
		}

		private boolean containsFiles(IResource rsc) {
			if (rsc instanceof IContainer) {
				IContainer container = (IContainer) rsc;
				try {
					return anyFile(container.members());
				} catch (CoreException e) {
					return true;
				}
			}
			throw new IllegalArgumentException(
					"Expected a container resource."); //$NON-NLS-1$
		}

		private boolean anyFile(IResource[] members) {
			for (IResource member : members) {
				if (member.getType() == IResource.FILE) {
					return true;
				} else if (isContainer(member) && containsFiles(member)) {
					return true;
				}
			}
			return false;
		}
	}
}
