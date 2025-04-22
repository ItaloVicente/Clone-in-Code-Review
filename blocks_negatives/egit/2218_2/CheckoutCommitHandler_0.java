		try {
			Map<String, Ref> localBranches = repo.getRefDatabase().getRefs(
					Constants.R_HEADS);
			for (Ref branch : localBranches.values()) {
				if (branch.getLeaf().getObjectId().equals(commit.getId())) {
					availableBranches.add(branch);
				}
			}
		} catch (IOException e) {
		}
