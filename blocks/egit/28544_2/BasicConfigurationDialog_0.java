		CLabel configLocationInfoLabel = new CLabel(main, SWT.NONE);
		configLocationInfoLabel.setImage(JFaceResources
				.getImage(Dialog.DLG_IMG_MESSAGE_INFO));
		configLocationInfoLabel
				.setText(UIText.BasicConfigurationDialog_ConfigLocationInfo);
		GridDataFactory.fillDefaults().span(2, 1)
				.applyTo(configLocationInfoLabel);

