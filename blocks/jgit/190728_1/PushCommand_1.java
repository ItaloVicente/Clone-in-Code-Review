	private String determineRemote(Config config
			throws IOException {
		if (remoteName != null) {
			return remoteName;
		}
		Ref head = repo.exactRef(Constants.HEAD);
		String effectiveRemote = null;
		BranchConfig branchCfg = null;
		if (head != null && head.isSymbolic()) {
			String currentBranch = head.getLeaf().getName();
			branchCfg = new BranchConfig(config
					Repository.shortenRefName(currentBranch));
			effectiveRemote = branchCfg.getPushRemote();
		}
		if (effectiveRemote == null) {
			effectiveRemote = config.getString(
					ConfigConstants.CONFIG_REMOTE_SECTION
					ConfigConstants.CONFIG_KEY_PUSH_DEFAULT);
			if (effectiveRemote == null && branchCfg != null) {
				effectiveRemote = branchCfg.getRemote();
			}
		}
		if (effectiveRemote == null) {
			effectiveRemote = Constants.DEFAULT_REMOTE_NAME;
		}
		return effectiveRemote;
	}

