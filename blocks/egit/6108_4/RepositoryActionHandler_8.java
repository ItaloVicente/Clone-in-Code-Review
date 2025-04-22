
	protected List<PreviousCommit> findPreviousCommits() throws IOException {
		List<PreviousCommit> result = new ArrayList<PreviousCommit>();
		Repository repository = getRepository();
		IResource resource = getSelectedResources()[0];
		String path = RepositoryMapping.getMapping(resource.getProject())
				.getRepoRelativePath(resource);
		final AtomicReference<String> previousPath = new AtomicReference<String>();
		RevWalk rw = new RevWalk(repository);
		try {
			if (path.length() > 0) {
				FollowFilter filter = FollowFilter.create(path);
				filter.setRenameCallback(new RenameCallback() {
					public void renamed(DiffEntry entry) {
						if (previousPath.get() == null)
							previousPath.set(entry.getOldPath());
					}
				});
				rw.setTreeFilter(filter);
			}

			RevCommit headCommit = rw.parseCommit(repository.getRef(
					Constants.HEAD).getObjectId());
			rw.markStart(headCommit);
			headCommit = rw.next();

			if (headCommit == null)
				return result;
			List<RevCommit> directParents = Arrays.asList(headCommit
					.getParents());
			if (previousPath.get() == null)
				previousPath.set(path);

			RevCommit previousCommit = rw.next();
			while (previousCommit != null && result.size() < directParents.size()) {
				if (directParents.contains(previousCommit))
					result.add(new PreviousCommit(previousCommit, previousPath
							.get()));
				previousCommit = rw.next();
			}
		} finally {
			rw.dispose();
		}
		return result;
	}

	protected static final class PreviousCommit {
		final RevCommit commit;
		final String path;
		PreviousCommit(final RevCommit commit, final String path) {
			this.commit = commit;
			this.path = path;
		}
	}

