		final boolean allSelected;
		final Collection<Ref> selectedBranches;
		if (validSource.isSourceRepoEmpty()) {
			allSelected = true;
			selectedBranches = Collections.emptyList();
		} else {
			allSelected = validSource.isAllSelected();
			selectedBranches = validSource.getSelectedBranches();
		}
