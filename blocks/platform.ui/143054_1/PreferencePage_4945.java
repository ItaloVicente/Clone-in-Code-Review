		noteLabel
				.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		Label messageLabel = new Label(messageComposite, SWT.WRAP);
		messageLabel.setText(message);
		messageLabel.setFont(font);
		return messageComposite;
	}

	protected Button getApplyButton() {
		return applyButton;
	}

	protected Button getDefaultsButton() {
		return defaultsButton;
	}

	@Override
