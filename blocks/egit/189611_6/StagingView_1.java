	private void updateUnstageViewer() {
		StagingViewContentProvider stagingViewContentProvider = (StagingViewContentProvider) unstagedViewer
				.getContentProvider();
		stagingViewContentProvider
				.setShowUntracked(showUntrackedAction.isChecked());
		updateSectionText();
		setRedraw(false);
		try {
			unstagedViewer.refresh();
			Object[] expandFolders = stagingViewContentProvider
					.getUntrackedFileFolders()
					.toArray(value -> new StagingFolderEntry[value]);
			if (showUntrackedAction.isChecked() && expandFolders.length > 0) {
				unstagedViewer.setExpandedElements(expandFolders);
			}
		} finally {
			setRedraw(true);
		}
	}

