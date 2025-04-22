	private void buildResetGroup(Composite parent) {
		Group g = new Group(parent, SWT.NONE);
		g.setText(UIText.BranchSelectionDialog_ResetType);
		g.setLayoutData(GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).create());
		g.setLayout(new RowLayout(SWT.VERTICAL));

		Button soft = new Button(g, SWT.RADIO);
		soft.setText(UIText.BranchSelectionDialog_ResetTypeSoft);
		soft.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				resetType = ResetType.SOFT;
			}
		});

		Button medium = new Button(g, SWT.RADIO);
		medium.setSelection(true);
		medium.setText(UIText.BranchSelectionDialog_ResetTypeMixed);
		medium.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				resetType = ResetType.MIXED;
			}
		});

		Button hard = new Button(g, SWT.RADIO);
		hard.setText(UIText.BranchSelectionDialog_ResetTypeHard);
		hard.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				resetType = ResetType.HARD;
			}
		});
	}

