		RepositoryCommit commit = getCommit();
		Repository repository = commit.getRepository();
		Map<String, Ref> refsMap = getAllBranchRefs(repository);
		if (refsMap.isEmpty()) {
			return Collections.emptyList();
		}
		if (refsMap.size() < BRANCH_LIMIT_FOR_SYNC_LOAD) {
			return findBranchesReachableFromCommit(commit, refsMap);
		} else {
			Job branchRefreshJob = new CommitEditorPageJob(commit) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					List<Ref> branches = findBranchesReachableFromCommit(commit,
							refsMap);
					updateUI(monitor, () -> fillBranches(branches));
					return Status.OK_STATUS;
				}
			};
			branchRefreshJob.schedule();
			return Collections.emptyList();
		}
	}

	private List<Ref> findBranchesReachableFromCommit(RepositoryCommit commit,
			Map<String, Ref> refsMap) {
		try (RevWalk revWalk = new RevWalk(commit.getRepository())) {
			return RevWalkUtils.findBranchesReachableFrom(commit.getRevCommit(),
					revWalk, refsMap.values());
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, false);
			return Collections.emptyList();
		}
	}

	private Map<String, Ref> getAllBranchRefs(Repository repository) {
		Map<String, Ref> refsMap = new HashMap<>();
		try {
