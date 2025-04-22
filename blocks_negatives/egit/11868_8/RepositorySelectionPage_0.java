		remoteCombo = new Combo(remotePanel, SWT.READ_ONLY | SWT.DROP_DOWN);
		final String items[] = new String[configuredRemotes.size()];
		int i = 0;
		for (final RemoteConfig rc : configuredRemotes)
			items[i++] = getTextForRemoteConfig(rc);
		final int defaultIndex = configuredRemotes.indexOf(remoteConfig);
		remoteCombo.setItems(items);
		remoteCombo.select(defaultIndex);
		remoteCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final int idx = remoteCombo.getSelectionIndex();
				remoteConfig = configuredRemotes.get(idx);
