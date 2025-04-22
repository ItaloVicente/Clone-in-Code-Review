	}

	private void downPressed() {
		swap(false);
	}

	public Composite getButtonBoxControl(Composite parent) {
		if (buttonBox == null) {
			buttonBox = new Composite(parent, SWT.NULL);
			GridLayout layout = new GridLayout();
			layout.marginWidth = 0;
			buttonBox.setLayout(layout);
			createButtons(buttonBox);
			buttonBox.addDisposeListener(event -> {
				addButton = null;
				removeButton = null;
				upButton = null;
				downButton = null;
				buttonBox = null;
