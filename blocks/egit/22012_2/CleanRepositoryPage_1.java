	private void updatePageComplete() {
		boolean hasCheckedElements = cleanTable.getCheckedElements().length != 0;
		setPageComplete(hasCheckedElements);
		if (hasCheckedElements)
			setMessage(null, NONE);
		else
			setMessage(UIText.CleanRepositoryPage_SelectFilesToClean, INFORMATION);
	}

