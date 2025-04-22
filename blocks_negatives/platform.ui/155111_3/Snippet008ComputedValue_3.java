		final Text formattedName;

		UI(Composite parent) {
			super(parent, SWT.NONE);

			GridLayoutFactory.swtDefaults().numColumns(2).applyTo(this);

			new Label(this, SWT.NONE).setText("First Name:");
			new Label(this, SWT.NONE).setText("Last Name");
