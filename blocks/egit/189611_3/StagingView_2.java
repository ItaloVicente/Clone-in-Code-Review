	private void updateUnstageViewer() {
		unstagedViewer.refresh();
		((StagingViewContentProvider) unstagedViewer.getContentProvider())
				.setShowUntracked(showUntrackedAction.isChecked());
		updateSectionText();
	}

