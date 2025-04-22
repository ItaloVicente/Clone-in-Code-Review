		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
		String jobname = NLS.bind(UIText.BranchAction_checkingOut, repoName,
				target);

		bop = new BranchOperation(repository, target);
