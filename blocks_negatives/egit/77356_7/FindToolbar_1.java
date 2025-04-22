		currentPositionLabel = new Label(this, SWT.NULL);
		GridData totalLabelData = new GridData();
		totalLabelData.horizontalAlignment = SWT.FILL;
		totalLabelData.grabExcessHorizontalSpace = true;
		currentPositionLabel.setLayoutData(totalLabelData);
		currentPositionLabel.setAlignment(SWT.RIGHT);

		patternField.addModifyListener(new ModifyListener() {
