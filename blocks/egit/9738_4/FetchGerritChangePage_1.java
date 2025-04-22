	protected void updateBranchAndTag() {
		Change change = Change.fromRef(refText.getText());
		if (change != null) {
			if(baseBranchNameOnTaskButton!=null&& baseBranchNameOnTaskButton.getSelection()) {
				String branchName = TaskBranchNameSuggester.suggestBranchName(TasksUi.getTaskActivityManager().getActiveTask(), "task"); //$NON-NLS-1$
				branchText.setText(branchName);
			} else {
				branchText.setText(NLS
						.bind(UIText.FetchGerritChangePage_SuggestedRefNamePattern,
								change.getChangeNumber(),
								change.getPatchSetNumber()));
			}
			tagText.setText(branchText.getText());
		} else {
			branchText.setText(""); //$NON-NLS-1$
			tagText.setText(""); //$NON-NLS-1$
		}
		checkPage();
	}

