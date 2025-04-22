			main.setLayout(new GridLayout(2, false));

			Label uriLabel = new Label(main, SWT.NONE);
			uriLabel.setText(UIText.ConfigureUriPage_FetchUri_label);
			uriText = new Text(main, SWT.BORDER);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(uriText);
			uriText.setEnabled(false);

			Group pushGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
			pushGroup.setText(UIText.ConfigureUriPage_PushUriGroup);
			pushGroup.setLayout(new GridLayout(1, false));
			GridDataFactory.fillDefaults().span(2, 1).grab(true, true).applyTo(
					pushGroup);

			tv = new TableViewer(pushGroup);
