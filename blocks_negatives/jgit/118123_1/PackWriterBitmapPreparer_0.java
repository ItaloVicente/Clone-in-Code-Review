			boolean isActiveBranch = true;
			if (totalWants > excessiveBranchCount
					&& !isRecentCommit(entry.getCommit())) {
				isActiveBranch = false;
			}

