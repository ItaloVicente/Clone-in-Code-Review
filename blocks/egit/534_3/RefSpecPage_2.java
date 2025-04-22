	}

	public void setSelection(RepositorySelection selection) {
		if (!selection.equals(validatedRepoSelection)) {
			currentRepoSelection = selection;
			setPageComplete(false);
		} else
			checkPage();
		revalidate();
