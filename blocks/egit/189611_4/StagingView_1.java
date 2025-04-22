	private void updateUnstageViewer() {
		((StagingViewContentProvider) unstagedViewer.getContentProvider())
				.setShowUntracked(showUntrackedAction.isChecked());
		updateSectionText();
		unstagedViewer.refresh();
		UIUtils.expandAll(unstagedViewer);
	}

