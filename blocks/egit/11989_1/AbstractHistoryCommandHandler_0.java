
	protected List<Ref> getBranchesOfCommit(GitHistoryPage page,
			final Repository repo, boolean hideCurrentBranch)
			throws IOException {
		String head = repo.getFullBranch();
		return getBranchesOfCommit(page, head, hideCurrentBranch);
	}

	protected List<Ref> getBranchesOfCommit(GitHistoryPage page) {
		return getBranchesOfCommit(page, (String) null, false);
	}

	private List<Ref> getBranchesOfCommit(GitHistoryPage page, String head,
			boolean hideCurrentBranch) {
		final List<Ref> branchesOfCommit = new ArrayList<Ref>();
		IStructuredSelection selection = getSelection(page);
		if (selection.isEmpty())
			return branchesOfCommit;
		PlotCommit commit = (PlotCommit) selection.getFirstElement();

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

	protected Repository getRepository(GitHistoryPage page) {
		if (page == null)
			return null;
		HistoryPageInput input = page.getInputInternal();
		if (input == null)
			return null;

		final Repository repository = input.getRepository();
		return repository;
	}
