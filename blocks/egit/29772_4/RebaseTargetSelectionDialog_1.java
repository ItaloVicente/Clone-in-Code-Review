
	@Override
	protected void createCustomArea(Composite parent) {
		final Button interactivebutton = new Button(parent, SWT.CHECK);
		interactivebutton
				.setText(UIText.RebaseTargetSelectionDialog_InteractiveButton);
		interactivebutton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				interactive = interactivebutton.getSelection();
			}
		});
		final Button preserveMergesButton = new Button(parent, SWT.CHECK);
		preserveMergesButton
				.setText(UIText.RebaseTargetSelectionDialog_PreserveMergesButton);
		preserveMergesButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				preserveMerges = preserveMergesButton.getSelection();
			}
		});
	}

	public boolean isInteractive() {
		return interactive;
	}

	public boolean isPreserveMerges() {
		return preserveMerges;
	}
