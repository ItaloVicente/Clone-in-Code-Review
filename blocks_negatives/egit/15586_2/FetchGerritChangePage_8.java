		branchTextlabel = new Label(checkoutGroup, SWT.NONE);
		GridDataFactory.defaultsFor(branchTextlabel).exclude(false)
				.applyTo(branchTextlabel);
		branchTextlabel.setText(UIText.FetchGerritChangePage_BranchNameText);
		branchText = new Text(checkoutGroup, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(branchText);
		branchText.addModifyListener(new ModifyListener() {
