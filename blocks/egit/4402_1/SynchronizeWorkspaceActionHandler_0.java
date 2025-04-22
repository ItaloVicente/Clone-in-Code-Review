	private String getDstRef(Repository repo, boolean launchFetch) {
		if (launchFetch) {
			String realName = getRealBranchName(repo);
			String name = BRANCH_NAME_PATTERN.matcher(realName).replaceAll(""); //$NON-NLS-1$
			String remote = repo.getConfig().getString(CONFIG_BRANCH_SECTION,
					name, CONFIG_KEY_REMOTE);
			String merge = repo.getConfig().getString(CONFIG_BRANCH_SECTION,
					name, CONFIG_KEY_MERGE);

			String mergeBranchName = merge.substring(
					merge.lastIndexOf("/"), merge.length()); //$NON-NLS-1$
			return R_REMOTES + remote + mergeBranchName;
		} else
			return HEAD;
	}

	private String getRealBranchName(Repository repo) {
		Ref ref;

		try {
			ref = repo.getRef(HEAD);
		} catch (IOException e) {
			ref = null;
		}

		if (ref != null && ref.isSymbolic())
			return ref.getTarget().getName();
		else
			return HEAD;
	}

