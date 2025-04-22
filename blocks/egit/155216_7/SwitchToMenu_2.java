		} else if (canBulkCreateNewBranch(repositories)) {
			createBulkNewBranchMenuItem(menu, repositories);
		} else {
			showCreateBranchItem = false;
		}
		if (showCreateBranchItem
				&& Arrays.stream(repositories).anyMatch(this::hasBranches)) {
			createSeparator(menu);
