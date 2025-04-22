		showUntrackedAction = new Action(UIText.StagingView_ShowUntrackedFiles,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				unstagedViewer.refresh();
				((StagingViewContentProvider) unstagedViewer
						.getContentProvider()).setShowUntracked(isChecked());
				updateSectionText();
			}
		};
		showUntrackedAction.setImageDescriptor(UIIcons.UNTRACKED_FILE);
		showUntrackedAction.setId(SHOW_UNTRACKED_TOOLBAR_ID);
		showUntrackedAction.setChecked(true);

