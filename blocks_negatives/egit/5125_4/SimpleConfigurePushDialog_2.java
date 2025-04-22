		Group uriGroup = new Group(remoteGroup, SWT.SHADOW_ETCHED_IN);
		uriGroup.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, true).applyTo(uriGroup);
		uriGroup.setText(UIText.SimpleConfigurePushDialog_UriGroup);

		final Composite sameUriDetails = new Composite(uriGroup, SWT.NONE);
