	private List<Ref> getBranchesOfCommit(GitHistoryPage page,
			final Repository repo, boolean hideCurrentBranch) throws IOException {
		final List<Ref> branchesOfCommit = new ArrayList<Ref>();
		IStructuredSelection selection = getSelection(page);
		if (selection.isEmpty())
			return branchesOfCommit;
		PlotCommit commit = (PlotCommit) selection.getFirstElement();
		String head = repo.getFullBranch();

		int refCount = commit.getRefCount();
		for (int i = 0; i < refCount; i++) {
			Ref ref = commit.getRef(i);
			String refName = ref.getName();
			if (hideCurrentBranch && head != null && refName.equals(head))
				continue;
			if (refName.startsWith(Constants.R_HEADS)
					|| refName.startsWith(Constants.R_REMOTES))
				branchesOfCommit.add(ref);
		}
		return branchesOfCommit;
	}

	private Repository getRepository(GitHistoryPage page) {
		if (page == null)
			return null;
		HistoryPageInput input = page.getInputInternal();
		if (input == null)
			return null;

		final Repository repository = input.getRepository();
		return repository;
	}

