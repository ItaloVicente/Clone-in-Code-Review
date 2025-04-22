	private void createRefsGroup(String refLabel) {
		Composite refsComposite = new Composite(this, SWT.NONE);
		refsComposite.setLayout(new GridLayout());
		refsComposite.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).hint(150, SWT.DEFAULT).create());
		Label ref = new Label(refsComposite, SWT.NONE);
		ref.setText(refLabel);
		refsCombo = new Combo(refsComposite, SWT.NONE);
		refsCombo.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).create());
	}

