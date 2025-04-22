			if (selectCurrentRef)
				if (refToMark != null)
					markRef(refToMark);
				else {
					String fullBranch = repo.getFullBranch();
					markRef(fullBranch);
				}
			if (expandLocalBranchesNode)
				branchTree.expandToLevel(localBranches, 1);
			if (expandRemoteBranchesNode)
				branchTree.expandToLevel(remoteBranches, 1);
