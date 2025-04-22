		} else if (Arrays.stream(repositories).allMatch(
				r -> (r.getRepositoryState() == RepositoryState.SAFE)
						&& hasBranches(r))) {
				createBulkNewBranchMenuItem(menu, repositories);
		}else {
			showCreateBranchItem=false;
		}
		if (showCreateBranchItem && Arrays.stream(repositories).anyMatch(this::hasBranches)) {
			createSeparator(menu);
