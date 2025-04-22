		String jobname;
		String repoName = Activator.getDefault().getRepositoryUtil().getRepositoryName(repository);
		if (refName != null) {
			bop = new BranchOperation(repository, refName);
			jobname = NLS.bind(UIText.BranchAction_checkingOut, repoName, refName);
		} else {
			bop = new BranchOperation(repository, commitId);
			jobname = NLS
					.bind(UIText.BranchAction_checkingOut, repoName, commitId.name());
		}
