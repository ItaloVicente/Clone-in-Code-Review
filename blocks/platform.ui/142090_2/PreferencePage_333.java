	}

	protected Composite createNoteComposite(Font font, Composite composite,
			String title, String message) {
		Composite messageComposite = new Composite(composite, SWT.NONE);
		GridLayout messageLayout = new GridLayout();
		messageLayout.numColumns = 2;
		messageLayout.marginWidth = 0;
		messageLayout.marginHeight = 0;
		messageComposite.setLayout(messageLayout);
		messageComposite.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL));
		messageComposite.setFont(font);

		final Label noteLabel = new Label(messageComposite, SWT.BOLD);
		noteLabel.setText(title);
		noteLabel.setFont(JFaceResources.getFontRegistry().getBold(
