		if (!allowBare) {
			bareMsg = new Composite(main, SWT.NONE);
			bareMsg.setLayout(new RowLayout());
			bareMsg.setLayoutData(
					GridDataFactory.fillDefaults().grab(true, false).create());
			Label imageLabel = new Label(bareMsg, SWT.NONE);
			imageLabel.setImage(
					JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_INFO));
			Label textLabel = new Label(bareMsg, SWT.WRAP);
			textLabel.setText(
					UIText.GitSelectRepositoryPage_BareRepositoriesHidden);
			bareMsg.setVisible(false);
		}

