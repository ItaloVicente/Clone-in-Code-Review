	static final int T_HEAD = 0;

	static final int T_INDEX = 1;

	static final int T_WORKSPACE = 2;

	private static final Map<Repository, DirCache> repoToDirCache = new WeakHashMap<Repository, DirCache>();

	/**
	 * Creates a list of decoratable resources for the given list of resources
	 *
	 * @param resources
	 *            the list of resources to be decorated
	 * @return the list of decoratable resources
	 * @throws IOException
	 */
	public static IDecoratableResource[] createDecoratableResources(
			final IResource[] resources) throws IOException {
		if (resources == null)
			return null;

		int i = 0;
		while (resources[i] == null) {
			i++;
			if (i >= resources.length)
				return null;
		}

		final IDecoratableResource[] decoratableResources = new IDecoratableResource[resources.length];

		for (i = 0; i < resources.length; i++) {
			final IResource resource = resources[i];
			if (resource != null && resource.getProject().isOpen()) {
				try {
					decoratableResources[i] = new DecoratableResourceAdapter(
							resource);
				} catch (IOException e) {
				}
			}
		}

		return decoratableResources;
	}

	/**
	 * Creates a temporary decoratable resource for the given project
	 *
	 * This temporary decoratable resource only contains the name of the
	 * repository and the current branch.
	 *
	 * @param project
	 *            the project to be decorated
	 * @return the decoratable resource
	 * @throws IOException
	 */
	static IDecoratableResource createTemporaryDecoratableResource(
			final IProject project) throws IOException {
		final DecoratableResource decoratableResource = new DecoratableResource(
				project);
		final Repository repository = RepositoryMapping.getMapping(project)
				.getRepository();
		decoratableResource.repositoryName = getRepositoryName(repository);
		decoratableResource.branch = getShortBranch(repository);
		decoratableResource.tracked = true;
		return decoratableResource;
	}

	static DirCache getDirCache(Repository repository) throws IOException {
		synchronized(repoToDirCache) {
			DirCache dirCache = repoToDirCache.get(repository);
			if (dirCache != null && !dirCache.isOutdated())
				return dirCache;
			dirCache = repository.readDirCache();
			repoToDirCache.put(repository, dirCache);
			return dirCache;
		}
	}

	static DecoratableResource decorateResource(
			final DecoratableResource decoratableResource,
			final TreeWalk treeWalk) throws IOException {
		final WorkingTreeIterator workingTreeIterator = treeWalk.getTree(
				T_WORKSPACE, WorkingTreeIterator.class);
		if (workingTreeIterator == null)
			return null;
		if (!(workingTreeIterator instanceof ContainerTreeIterator))
			return null;
		final ContainerTreeIterator workspaceIterator = (ContainerTreeIterator) workingTreeIterator;
		final ResourceEntry resourceEntry = workspaceIterator
				.getResourceEntry();

		if (resourceEntry == null)
			return null;

		if (workspaceIterator.isEntryIgnored())
			decoratableResource.ignored = true;

		final int mHead = treeWalk.getRawMode(T_HEAD);
		final int mIndex = treeWalk.getRawMode(T_INDEX);

		if (mHead == FileMode.MISSING.getBits()
				&& mIndex == FileMode.MISSING.getBits())
			return decoratableResource;
		else
			decoratableResource.ignored = false;

		decoratableResource.tracked = true;

		if (mHead == FileMode.MISSING.getBits()) {
			decoratableResource.staged = Staged.ADDED;
		} else if (mIndex == FileMode.MISSING.getBits()) {
			decoratableResource.staged = Staged.REMOVED;
		} else if (mHead != mIndex
				|| (mIndex != FileMode.TREE.getBits() && !treeWalk.idEqual(
						T_HEAD, T_INDEX))) {
			decoratableResource.staged = Staged.MODIFIED;
		} else {
			decoratableResource.staged = Staged.NOT_STAGED;
		}

		final DirCacheIterator indexIterator = treeWalk.getTree(T_INDEX,
				DirCacheIterator.class);
		final DirCacheEntry indexEntry = indexIterator != null ? indexIterator
				.getDirCacheEntry() : null;

		if (indexEntry == null)
			return decoratableResource;

		if (indexEntry.getStage() > 0)
			decoratableResource.conflicts = true;

		if (indexEntry.isAssumeValid()) {
			decoratableResource.dirty = false;
			decoratableResource.assumeValid = true;
		} else {
			if (workspaceIterator.isModified(indexEntry, true))
				decoratableResource.dirty = true;
		}
		return decoratableResource;
	}

