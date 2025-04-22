		detailsComposite = new Composite(res, SWT.NONE);
		detailsComposite.setVisible(false);
		detailsComposite.setLayout(new GridLayout(4, false));
		GridData gridDataDetails = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridDataDetails.horizontalSpan = 4;
		detailsComposite.setLayoutData(gridDataDetails);
		Link showDetectorsLink = new Link(detailsComposite, SWT.NONE);
