	private boolean isValidRepo(final Repository repository) {
		return repository != null
				&& !repository.isBare()
				&& repository.getWorkTree().exists()
				&& org.eclipse.egit.core.Activator.getDefault()
						.getRepositoryUtil().contains(repository);
	}

	private void clearRepository() {
		saveCommitMessageComponentState();
		currentRepository = null;
		removeListeners();
		StagingViewUpdate update = new StagingViewUpdate(null, null, null);
		unstagedTableViewer.setInput(update);
		stagedTableViewer.setInput(update);
		enableCommitWidgets(false);
		updateSectionText();
		form.setText(UIText.StagingView_NoSelectionTitle);
	}

