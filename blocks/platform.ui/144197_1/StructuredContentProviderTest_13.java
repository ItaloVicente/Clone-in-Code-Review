		operation.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.VERTICAL_ALIGN_FILL));

		Control outputControl = createOutputComposite(shell);
		GridData outputData = new GridData(GridData.FILL_BOTH);
		outputData.minimumHeight = 1;
		outputData.minimumWidth = 1;
		outputData.widthHint = 300;
		outputData.heightHint = 150;

		outputControl.setLayoutData(outputData);

		final Label sumLabel = new Label(shell, SWT.NONE);
		new ControlUpdater(sumLabel) {
			@Override
			protected void updateControl() {
				double sum = sumOfOutputSet.getValue();
				int size = outputSet.size();

				sumLabel.setText("The sum of the above " + size //$NON-NLS-1$
						+ " doubles is " + sum); //$NON-NLS-1$
			}
		};
		sumLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.VERTICAL_ALIGN_FILL));

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		shell.setLayout(layout);
