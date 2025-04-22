		final Label someDoubles = new Label(shell, SWT.NONE);
		someDoubles.setText("A list of random Doubles"); //$NON-NLS-1$
		someDoubles.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL
						| GridData.VERTICAL_ALIGN_FILL));

		Control addRemoveComposite = createInputControl(shell, inputSet);

		GridData addRemoveData = new GridData(GridData.FILL_BOTH);
		addRemoveData.minimumHeight = 1;
		addRemoveData.minimumWidth = 1;

		addRemoveComposite.setLayoutData(addRemoveData);

		Group operation = new Group(shell, SWT.NONE);
		{ // Initialize operation group
			operation.setText("Select transformation"); //$NON-NLS-1$

			createRadioButton(operation, currentFunction, "f(x) = x", //$NON-NLS-1$
					SomeMathFunction.OP_IDENTITY);
			createRadioButton(operation, currentFunction, "f(x) = 2 * x", //$NON-NLS-1$
					SomeMathFunction.OP_MULTIPLY);
			createRadioButton(operation, currentFunction,
					"f(x) = floor(x)", //$NON-NLS-1$
					SomeMathFunction.OP_ROUND);
