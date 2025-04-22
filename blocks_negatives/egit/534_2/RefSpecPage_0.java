		repoPage.addSelectionListener(new SelectionChangeListener() {
			public void selectionChanged() {
				if (!repoPage.selectionEquals(validatedRepoSelection))
					setPageComplete(false);
				else
					checkPage();
			}
		});
