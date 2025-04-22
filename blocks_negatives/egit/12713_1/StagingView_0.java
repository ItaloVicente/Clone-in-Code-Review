		amendPreviousCommitAction = new Action(
				UIText.StagingView_Ammend_Previous_Commit, IAction.AS_CHECK_BOX) {

			public void run() {
				commitMessageComponent.setAmendingButtonSelection(isChecked());
				updateMessage();
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

