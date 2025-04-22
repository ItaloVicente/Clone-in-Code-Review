		if (this.ref != null) {
			String branchName = Repository.shortenRefName(ref.getName());
			boolean alreadyConfigured = repository.getConfig()
					.getSubsections(ConfigConstants.CONFIG_BRANCH_SECTION)
					.contains(branchName);
			UpstreamConfig config;
			if (alreadyConfigured) {
				boolean rebase = repository.getConfig().getBoolean(
						ConfigConstants.CONFIG_BRANCH_SECTION, branchName,
						ConfigConstants.CONFIG_KEY_REBASE, false);
				config = rebase ? UpstreamConfig.REBASE : UpstreamConfig.MERGE;
			} else {
				config = UpstreamConfig.getDefault(repository, Constants.R_REMOTES
						+ Constants.DEFAULT_REMOTE_NAME + "/" + branchName); //$NON-NLS-1$
			}
			this.upstreamConfig = config;
