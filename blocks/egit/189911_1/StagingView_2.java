		previewAction = new Action(UIText.StagingView_Preview_Commit_Message,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				if (isChecked()) {
					previewLayout.topControl = commitMessagePreview;
					commitMessageSection.setText(
							UIText.StagingView_CommitMessagePreview);
					previewer
							.setText(commitMessageComponent.getRepository(),
									commitMessageComponent.getCommitMessage());
				} else {
					previewLayout.topControl = commitMessageText;
					commitMessageSection
							.setText(UIText.StagingView_CommitMessage);
				}
				previewLayout.topControl.getParent().layout(true, true);
				commitMessageSection.redraw();
				if (!isChecked()) {
					commitMessageText.setFocus();
				}
			}
		};
		previewAction.setImageDescriptor(UIIcons.ELCL16_PREVIEW);
		commitMessageToolBarManager.add(previewAction);
		commitMessageToolBarManager.add(new Separator());

