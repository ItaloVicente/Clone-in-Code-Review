		Label placeholder = new Label(container, SWT.NONE);
		placeholder.setText(""); //$NON-NLS-1$
		Label noteLabel = new Label(container, SWT.NONE);
		noteLabel.setText(Messages.FilterInputDialog_note_label);

		return area;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		checkInput();
		return contents;
