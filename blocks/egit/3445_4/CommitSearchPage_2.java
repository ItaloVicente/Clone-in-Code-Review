		GridDataFactory.swtDefaults().grab(true, false).span(2, 1)
				.applyTo(this.searchAllBranchesButton);

		repositoryGroup.setText(getRepositoryText());
	}

	private String getRepositoryText() {
		return MessageFormat.format(UIText.CommitSearchPage_Repositories,
				Integer.valueOf(repositoryViewer.getCheckedElements().length),
				Integer.valueOf(repositoryViewer.getTable().getItemCount()));
