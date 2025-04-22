			if (refToMark != null) {
				if (!markRef(refToMark))
					branchTree.expandToLevel(localBranches, 1);
			} else {
				String fullBranch = repo.getFullBranch();
				if (!markRef(fullBranch))
					branchTree.expandToLevel(localBranches, 1);
			}
