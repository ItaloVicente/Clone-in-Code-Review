		parent.setLayout(GridLayoutFactory.swtDefaults().create());
		new Label(parent, SWT.NONE).setText(UIText.BranchSelectionDialog_Refs);
		branchTree = new Tree(parent, SWT.BORDER);
		branchTree.setLayoutData(GridDataFactory.fillDefaults().grab(true,true).hint(500, 300).create());
		branchTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean oneSelected = branchTree.getSelection().length == 1;
