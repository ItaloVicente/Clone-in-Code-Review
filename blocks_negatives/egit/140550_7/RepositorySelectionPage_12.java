		remoteCombo.addRemoteSelectionListener(new IRemoteSelectionListener() {
			@Override
			public void remoteSelected(RemoteConfig rc) {
				remoteConfig = rc;
				checkPage();
			}
