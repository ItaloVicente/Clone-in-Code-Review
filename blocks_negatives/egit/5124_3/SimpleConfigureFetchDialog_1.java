		Group remoteGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		remoteGroup.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, true).applyTo(remoteGroup);
		remoteGroup.setText(NLS.bind(
				UIText.SimpleConfigureFetchDialog_RemoteGroupHeader, config
						.getName()));

		addDefaultOriginWarningIfNeeded(remoteGroup);
