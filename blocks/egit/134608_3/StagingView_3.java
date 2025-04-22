		signCommitAction = new Action(UIText.StagingView_Sign_Commit,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				commitMessageComponent
						.setSignCommitButtonSelection(isChecked());
			}
		};
		signCommitAction.setImageDescriptor(UIIcons.SIGN_COMMIT);
		commitMessageToolBarManager.add(signCommitAction);

