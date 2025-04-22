	private void createSwitchToCommitItem(Menu menu, Repository repository) {
		MenuItem switchToCommit = new MenuItem(menu, SWT.PUSH);
		switchToCommit.setText(UIText.SwitchToMenu_CommitMenuLabel);
		switchToCommit.setImage(commitImage);
		switchToCommit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (repository != null) {
					CommitSelectionDialog dialog = new CommitSelectionDialog(
							e.display.getActiveShell(), repository);
					if (dialog.open() == Window.OK) {
						ObjectId commitId = dialog.getCommitId();
						if (commitId != null) {
							BranchOperationUI
									.checkout(repository, commitId.getName())
									.start();
						}
					}
				}
			}
		});
	}

