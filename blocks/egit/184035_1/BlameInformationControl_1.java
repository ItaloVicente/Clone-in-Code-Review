		GridDataFactory.fillDefaults().grab(true, false).applyTo(messageText);

		separator = new Label(displayArea, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(separator);

		ToolBarManager toolbar = getToolBarManager();
		if (toolbar != null) {
			openCommit = new Action(
					UIText.BlameInformationControl_OpenCommitLabel,
					UIIcons.OPEN_COMMIT) {

				@Override
				public void run() {
					openCommit();
				}
			};
			toolbar.add(openCommit);
			showInHistory = new Action(
					UIText.BlameInformationControl_ShowInHistoryLabel,
					UIIcons.HISTORY) {

				@Override
				public void run() {
					showCommitInHistory();
				}
			};
			toolbar.add(showInHistory);
			toolbar.update(true);
		}
