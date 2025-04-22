		Group remoteGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		remoteGroup.setLayout(new GridLayout());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(remoteGroup);
		remoteGroup.setText(NLS.bind(
				UIText.SimpleConfigurePushDialog_RemoteGroupTitle, config
						.getName()));
