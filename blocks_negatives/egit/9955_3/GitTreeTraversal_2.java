	public GitTreeTraversal(Repository repo, RevCommit commit) {
		this(repo, commit, new Path(repo.getWorkTree().toString()));
	}

	private GitTreeTraversal(Repository repo, AnyObjectId baseId,
			AnyObjectId actualId, IPath path) {
		super(getResourcesImpl(repo, baseId, actualId, path),
				IResource.DEPTH_INFINITE, IResource.NONE);
	}

	private GitTreeTraversal(Repository repo, RevCommit commit, IPath path) {
		super(getResourcesImpl(repo, commit, path), IResource.DEPTH_INFINITE,
				IResource.NONE);
	}

	private static IResource[] getResourcesImpl(Repository repo,
			RevCommit commit, IPath path) {
		AnyObjectId baseId;
		RevCommit[] parents = commit.getParents();
		if (parents.length > 0)
			baseId = parents[0].getTree().getId();
		else
			baseId = zeroId();

		AnyObjectId remoteId = commit.getTree().getId();

		return getResourcesImpl(repo, baseId, remoteId, path);
	}

	private static IResource[] getResourcesImpl(Repository repo,
			AnyObjectId baseId, AnyObjectId remoteId, IPath path) {
		if (remoteId.equals(zeroId()))
			return new IResource[0];

		TreeWalk tw = new TreeWalk(repo);
		List<IResource> result = new ArrayList<IResource>();

		tw.reset();
		tw.setRecursive(false);
		tw.setFilter(TreeFilter.ANY_DIFF);
		try {
			if (fileTreeIterator == null || !repo.equals(lastRepo)) {
				lastRepo = repo;
				fileTreeIterator = new FileTreeIterator(repo);
			} else
				fileTreeIterator.reset();

			tw.addTree(fileTreeIterator);
			if (!baseId.equals(zeroId()))
				tw.addTree(baseId);

			int actualNth = tw.addTree(remoteId);

			while (tw.next()) {
				int objectType = tw.getFileMode(actualNth).getObjectType();
				String name = tw.getNameString();
				IPath childPath = path.append(name);

				IResource resource = null;
				if (objectType == Constants.OBJ_BLOB)
					resource = ROOT.getFileForLocation(childPath);
				else if (objectType == Constants.OBJ_TREE)
					resource = ROOT.getContainerForLocation(childPath);

				if (resource != null)
					result.add(resource);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}

		return result.toArray(new IResource[result.size()]);
	}

