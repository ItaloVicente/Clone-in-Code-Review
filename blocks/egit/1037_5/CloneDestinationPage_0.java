	public void setSelection(RepositorySelection repositorySelection, List<Ref> availableRefs, List<Ref> branches, Ref head){
		this.availableRefs.clear();
		this.availableRefs.addAll(availableRefs);
		checkPreviousPagesSelections(repositorySelection, branches, head);
		revalidate(repositorySelection,branches, head);
	}

	private void checkPreviousPagesSelections(
			RepositorySelection repositorySelection, List<Ref> branches,
			Ref head) {
		if (!repositorySelection.equals(validatedRepoSelection)
				|| !branches.equals(validatedSelectedBranches)
				|| !head.equals(validatedHEAD))
