			if (branchName == null) {
				pushRefSpecs.addAll(config.getPushRefSpecs());
			} else {
				Config repoConfig = repository.getConfig();
				String remoteBranchName = branchName;
				BranchConfig branchConfig = new BranchConfig(repoConfig,
						Repository.shortenRefName(branchName));
				String trackingBranchName = branchConfig.getMerge();
				if (!branchConfig.isRemoteLocal() && trackingBranchName != null
						&& trackingBranchName.startsWith(Constants.R_HEADS)) {
					remoteBranchName = trackingBranchName;
				}
				pushRefSpecs
						.add(new RefSpec(branchName + ':' + remoteBranchName));
			}
