		new Label(container, SWT.NONE); // Placeholder to push noteLabel to the second column.
		Label noteLabel = new Label(container, SWT.NONE);
		noteLabel.setText(Messages.FilterInputDialog_note_label);

		return area;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		checkInput();
		return contents;
