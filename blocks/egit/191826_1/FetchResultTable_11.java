			try {
				String branch = repo.getBranch();
				if (branch != null) {
					remoteBranchOfCurrentBranch = repo.getConfig().getString(
							ConfigConstants.CONFIG_BRANCH_SECTION, branch,
							ConfigConstants.CONFIG_KEY_MERGE);
				}
			} catch (IOException e) {
				remoteBranchOfCurrentBranch = null;
			}
		});
