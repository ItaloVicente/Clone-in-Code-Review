		return "DecoratableResourceAdapter[" + getName() + (isTracked() ? ", tracked" : "") + (isIgnored() ? ", ignored" : "") + (isDirty() ? ", dirty" : "") + (hasConflicts() ? ",conflicts" : "") + ", staged=" + staged + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$//$NON-NLS-7$//$NON-NLS-8$//$NON-NLS-9$//$NON-NLS-10$//$NON-NLS-11$
	}

	private void extractResourceProperties() {
		String repoRelativePath = makeRepoRelative(resource);

		Set<String> ignoredFiles = indexDiffData.getIgnoredNotInIndex();
		ignored = ignoredFiles.contains(repoRelativePath)
				|| containsPrefixPath(ignoredFiles, repoRelativePath);
		Set<String> untracked = indexDiffData.getUntracked();
		tracked = !untracked.contains(repoRelativePath) && !ignored;

		Set<String> added = indexDiffData.getAdded();
		Set<String> removed = indexDiffData.getRemoved();
		Set<String> changed = indexDiffData.getChanged();
			staged = Staged.ADDED;
			staged = Staged.REMOVED;
			staged = Staged.MODIFIED;
		else
			staged = Staged.NOT_STAGED;

		Set<String> conflicting = indexDiffData.getConflicting();
		conflicts = conflicting.contains(repoRelativePath);

		Set<String> modified = indexDiffData.getModified();
		dirty = modified.contains(repoRelativePath);
	}

	private void extractContainerProperties() {
		String repoRelative = makeRepoRelative(resource);
		if (repoRelative == null)
			return;

		if (ResourceUtil.isSymbolicLink(repository, repoRelativePath)) {
			extractResourceProperties();
			return;
		}

		Set<String> ignoredFiles = indexDiffData.getIgnoredNotInIndex();
		Set<String> untrackedFolders = indexDiffData.getUntrackedFolders();
		ignored = containsPrefixPath(ignoredFiles, repoRelativePath)
				|| !hasContainerAnyFiles(resource);

		if (ignored)
			tracked = false;
		else
			tracked = !containsPrefixPath(untrackedFolders, repoRelativePath);

		Set<String> changed = new HashSet<String>(indexDiffData.getChanged());
		changed.addAll(indexDiffData.getAdded());
		changed.addAll(indexDiffData.getRemoved());
		if (containsPrefix(changed, repoRelativePath))
			staged = Staged.MODIFIED;
		else
			staged = Staged.NOT_STAGED;

		Set<String> conflicting = indexDiffData.getConflicting();
		conflicts = containsPrefix(conflicting, repoRelativePath);

		Set<String> modified = indexDiffData.getModified();
		Set<String> untracked = indexDiffData.getUntracked();
		Set<String> missing = indexDiffData.getMissing();
		dirty = containsPrefix(modified, repoRelativePath)
				|| containsPrefix(untracked, repoRelativePath)
				|| containsPrefix(missing, repoRelativePath);
	}

	private static boolean hasContainerAnyFiles(IResource resource) {
		if (resource instanceof IContainer) {
			IContainer container = (IContainer) resource;
			try {
				return anyFile(container.members());
			} catch (CoreException e) {
				return true;
			}
		}
	}

	private static boolean anyFile(IResource[] members) {
		for (IResource member : members) {
			if (member.getType() == IResource.FILE)
				return true;
			else if (member.getType() == IResource.FOLDER)
				if (hasContainerAnyFiles(member))
					return true;
		}
		return false;
	}

	@Nullable
	private String makeRepoRelative(IResource res) {
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
				entryPath = entry;
			else
			if (path.startsWith(entryPath))
				return true;
		}
		return false;
