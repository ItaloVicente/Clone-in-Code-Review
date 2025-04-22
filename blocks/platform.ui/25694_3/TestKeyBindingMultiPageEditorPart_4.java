	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new RowLayout());
		Text text1 = new Text(composite, SWT.NONE);
		text1.setText("Blue"); //$NON-NLS-1$
		Text text2 = new Text(composite, SWT.NONE);
		text2.setText("Red"); //$NON-NLS-1$
	}
