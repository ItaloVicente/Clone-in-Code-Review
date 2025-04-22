		showUntrackedAction = new Action(UIText.StagingView_ShowUntrackedFiles,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				updateUnstageViewer();
				refreshViewersPreservingExpandedElements();
			}

		};
		showUntrackedAction.setImageDescriptor(UIIcons.UNTRACKED_FILE);
		showUntrackedAction.setId(SHOW_UNTRACKED_TOOLBAR_ID);
		showUntrackedAction.setChecked(true);

