	private Ref findBranchToCheckout(FetchResult result) {
		Ref foundBranch = null;
		final Ref idHEAD = result.getAdvertisedRef(Constants.HEAD);
		for (final Ref r : result.getAdvertisedRefs()) {
			final String n = r.getName();
			if (!n.startsWith(Constants.R_HEADS))
				continue;
			if (idHEAD == null)
				continue;
			if (r.getObjectId().equals(idHEAD.getObjectId())) {
				foundBranch = r;
				break;
			}
		}
		return foundBranch;
	}

	private void addMergeConfig(Repository repo
		String branchName = Repository.shortenRefName(head.getName());
		repo.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION
				branchName
		repo.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION
				branchName
		repo.getConfig().save();
	}

