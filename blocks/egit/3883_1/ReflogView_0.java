			showReflogFor(repository);
		}
	}

	public void showReflogFor(Repository repository) {
		if (repository != null) {
			refLogTableTreeViewer.setInput(repository);
			form.setText(getRepositoryName(repository));
