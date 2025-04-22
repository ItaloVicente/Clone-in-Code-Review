	protected Composite createContentCoposite(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		final GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		comp.setLayout(layout);

		return comp;
	}

