		if (showNewRemoteButton) {
			Button newRemoteButton = new Button(remoteGroup, SWT.PUSH);
			newRemoteButton.setText(UIText.PushBranchPage_NewRemoteButton);
			GridDataFactory.fillDefaults().applyTo(newRemoteButton);
			newRemoteButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					showNewRemoteDialog();
				}
			});
		}

