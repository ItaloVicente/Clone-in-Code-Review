			main.setLayout(new GridLayout(2, false));

			Label uriLabel = new Label(main, SWT.NONE);
			uriLabel.setText(UIText.ConfigureUriPage_FetchUri_label);
			uriLabel.setToolTipText(UIText.ConfigureUriPage_UriTooltip);
			uriText = new Text(main, SWT.BORDER);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(uriText);
			uriText.setEnabled(false);

			Label pushLabel = new Label(main, SWT.NONE);
			pushLabel.setText(UIText.ConfigureUriPage_PushUriLabel);
			pushLabel.setToolTipText(UIText.ConfigureUriPage_PushUriTooltip);
			GridDataFactory.fillDefaults().span(2, 1).applyTo(pushLabel);

