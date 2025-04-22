		SelectionType selectionType = sourceSelection ? SelectionType.FETCH : SelectionType.PUSH;
		remoteCombo = new RemoteSelectionCombo(remotePanel, SWT.NULL, selectionType);
		remoteConfig = remoteCombo.setItems(configuredRemotes);
		remoteCombo.addRemoteSelectionListener(new IRemoteSelectionListener() {
			public void remoteSelected(RemoteConfig rc) {
				remoteConfig = rc;
