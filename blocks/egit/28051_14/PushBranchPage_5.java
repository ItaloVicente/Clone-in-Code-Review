		boolean alreadyConfigured = false;
		String branchName = Constants.MASTER;
		if (ref != null) {
			branchName = Repository.shortenRefName(ref.getName());
			alreadyConfigured = repository.getConfig()
					.getSubsections(ConfigConstants.CONFIG_BRANCH_SECTION)
					.contains(branchName);
		}
