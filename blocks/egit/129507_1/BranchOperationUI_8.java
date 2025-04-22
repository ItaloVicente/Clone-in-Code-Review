	private static String getJobName(Repository[] repos, String target) {

		if (repos.length > 1) {
			return NLS.bind(UIText.BranchAction_checkingOutMultiple, target);
        }

		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repos[0]);
		return NLS.bind(UIText.BranchAction_checkingOut, repoName, target);
	}

