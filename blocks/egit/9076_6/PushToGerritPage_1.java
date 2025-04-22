
		Label reviewerLabel = new Label(main, SWT.NONE);
		reviewerLabel.setText(UIText.PushToGerritPage_ReviewersLabel);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.applyTo(reviewerLabel);

		reviewersText = new Text(main, SWT.BORDER | SWT.MULTI | SWT.WRAP
				| SWT.V_SCROLL);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1)
				.hint(SWT.DEFAULT, 50).applyTo(reviewersText);

		configureContentProposalToReveiwerText();

