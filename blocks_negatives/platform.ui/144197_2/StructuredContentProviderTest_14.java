			final Label someDoubles = new Label(shell, SWT.NONE);
			someDoubles.setLayoutData(new GridData(
					GridData.HORIZONTAL_ALIGN_FILL
							| GridData.VERTICAL_ALIGN_FILL));

			Control addRemoveComposite = createInputControl(shell, inputSet);

			GridData addRemoveData = new GridData(GridData.FILL_BOTH);
			addRemoveData.minimumHeight = 1;
			addRemoveData.minimumWidth = 1;

			addRemoveComposite.setLayoutData(addRemoveData);

			Group operation = new Group(shell, SWT.NONE);

				createRadioButton(operation, currentFunction, "f(x) = x", //$NON-NLS-1$
						SomeMathFunction.OP_IDENTITY);
				createRadioButton(operation, currentFunction, "f(x) = 2 * x", //$NON-NLS-1$
						SomeMathFunction.OP_MULTIPLY);
				createRadioButton(operation, currentFunction,
						"f(x) = floor(x)", //$NON-NLS-1$
						SomeMathFunction.OP_ROUND);

				GridLayout layout = new GridLayout();
				layout.numColumns = 1;
				operation.setLayout(layout);
			}
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

				}
			};
			sumLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.VERTICAL_ALIGN_FILL));
