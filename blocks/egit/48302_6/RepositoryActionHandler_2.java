			RevCommit headCommit = rw.parseCommit(repository.getRef(
					Constants.HEAD).getObjectId());
			rw.close();
			return headCommit;
		}
	}

	protected List<RevCommit> findPreviousCommits(
			Collection<IResource> resources) throws IOException {
		List<RevCommit> result = new ArrayList<RevCommit>();
		Repository repository = getRepository();
		RepositoryMapping mapping = RepositoryMapping.getMapping(resources
				.iterator().next()
				.getProject());
		if (mapping == null) {
			return result;
		}
		try (RevWalk rw = new RevWalk(repository)) {
			rw.sort(RevSort.COMMIT_TIME_DESC, true);
			rw.sort(RevSort.BOUNDARY, true);

			List<TreeFilter> filters = new ArrayList<TreeFilter>();
			DiffConfig diffConfig = repository.getConfig().get(DiffConfig.KEY);
			for (IResource resource : resources) {
				String path = mapping.getRepoRelativePath(resource);

				if (path != null && path.length() > 0) {
					filters.add(FollowFilter.create(path, diffConfig));
				}
			}

			if (filters.size() >= 2) {
				TreeFilter filter = OrTreeFilter.create(filters);
				rw.setTreeFilter(filter);
			} else if (filters.size() == 1) {
				rw.setTreeFilter(filters.get(0));
			}

