	private void updateToolbar() {
		IToolBarManager toolbar = getViewSite().getActionBars()
				.getToolBarManager();

		amendPreviousCommitAction = new Action(
				UIText.StagingView_Ammend_Previous_Commit, IAction.AS_CHECK_BOX) {

			public void run() {
				commitMessageComponent.setAmendingButtonSelection(isChecked());
			}
		};
		amendPreviousCommitAction.setImageDescriptor(UIIcons.AMEND_COMMIT);
		toolbar.add(amendPreviousCommitAction);

		signedOffByAction = new Action(UIText.StagingView_Add_Signed_Off_By,
				IAction.AS_CHECK_BOX) {

			public void run() {
				commitMessageComponent.setSignedOffButtonSelection(isChecked());
			}
		};
		signedOffByAction.setImageDescriptor(UIIcons.SIGNED_OFF);
		toolbar.add(signedOffByAction);

		addChangeIdAction = new Action(UIText.StagingView_Add_Change_ID,
				IAction.AS_CHECK_BOX) {

			public void run() {
				commitMessageComponent.setChangeIdButtonSelection(isChecked());
			}
		};
		addChangeIdAction.setImageDescriptor(UIIcons.GERRIT);
		toolbar.add(addChangeIdAction);

		toolbar.add(new Separator());

		commitAction = new Action(UIText.StagingView_Commit,
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				commit();
			}
		};
		commitAction.setImageDescriptor(UIIcons.COMMIT);
		toolbar.add(commitAction);

		openNewCommitsAction = new Action(UIText.StagingView_OpenNewCommits,
				IAction.AS_CHECK_BOX) {

			public void run() {
				Activator
						.getDefault()
						.getPreferenceStore()
						.setValue(UIPreferences.STAGING_SHOW_NEW_COMMITS,
								isChecked());
			}
		};
		openNewCommitsAction.setChecked(Activator.getDefault()
				.getPreferenceStore()
				.getBoolean(UIPreferences.STAGING_SHOW_NEW_COMMITS));
		getViewSite().getActionBars().getMenuManager()
				.add(openNewCommitsAction);
	}

	private void updateSectionText() {
		Integer stagedCount = Integer.valueOf(stagedTableViewer.getTable()
				.getItemCount());
		stagedSection.setText(MessageFormat.format(
				UIText.StagingView_StagedChanges, stagedCount));
		Integer unstagedCount = Integer.valueOf(unstagedTableViewer.getTable()
				.getItemCount());
		unstagedSection.setText(MessageFormat.format(
				UIText.StagingView_UnstagedChanges, unstagedCount));
	}

