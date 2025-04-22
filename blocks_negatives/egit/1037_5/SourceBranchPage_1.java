
		sourcePage.addSelectionListener(new SelectionChangeListener() {
			public void selectionChanged() {
				if (!sourcePage.selectionEquals(validatedRepoSelection))
					setPageComplete(false);
				else
					checkPage();
			}
		});
