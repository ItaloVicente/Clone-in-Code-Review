	protected Composite createComposite(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		GridData data = new GridData(
				GridData.FILL_BOTH | GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL);
		composite.setLayoutData(data);
		composite.setFont(parent.getFont());
