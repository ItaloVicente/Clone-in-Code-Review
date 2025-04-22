	private void updateSectionText() {
		stagedSection.setText(MessageFormat.format(
				UIText.StagingView_StagedChanges,
				Integer.valueOf(stagedTableViewer.getTable().getItemCount())));
		unstagedSection
				.setText(MessageFormat.format(
						UIText.StagingView_UnstagedChanges, Integer
								.valueOf(unstagedTableViewer.getTable()
										.getItemCount())));
	}

