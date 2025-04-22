			if ((settings & SELECT_CURRENT_REF) != 0)
				if (refToMark != null)
					markRef(refToMark);
				else {
					String fullBranch = repo.getFullBranch();
					markRef(fullBranch);
				}
			if ((settings & EXPAND_LOCAL_BRANCHES_NODE) != 0)
				branchTree.expandToLevel(localBranches, 1);
			if ((settings & EXPAND_REMOTE_BRANCHES_NODE) != 0)
				branchTree.expandToLevel(remoteBranches, 1);
