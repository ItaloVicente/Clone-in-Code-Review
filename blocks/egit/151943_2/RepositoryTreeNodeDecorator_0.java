				if (numberOfBranches <= 1) {
					String thisBranch = Repository.shortenRefName(
							DecoratorRepositoryStateCache.INSTANCE
									.getFullBranchName(repo));
					if (!thisBranch.equals(singleBranch)) {
						numberOfBranches++;
					}
					if (singleBranch == null) {
						singleBranch = thisBranch;
					}
				}
				if (markGroupDirty && numberOfBranches > 1) {
