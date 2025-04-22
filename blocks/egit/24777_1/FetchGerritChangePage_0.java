		branchCheckoutButton = new Button(checkoutGroup, SWT.CHECK);
		GridDataFactory.fillDefaults().span(3, 1).align(SWT.END, SWT.BEGINNING)
				.applyTo(branchCheckoutButton);
		branchCheckoutButton.setFont(JFaceResources.getDialogFont());
		branchCheckoutButton
				.setText(UIText.FetchGerritChangePage_LocalBranchCheckout);
		branchCheckoutButton.setSelection(true);

