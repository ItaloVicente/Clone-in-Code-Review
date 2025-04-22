				final Button remove = new Button(buttonBar, SWT.PUSH);
				new ControlUpdater(remove) {
					@Override
					protected void updateControl() {

						remove.setEnabled(selectedInt.getValue() != null);
					}
				};

				remove.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						inputSet.remove(selectedInt.getValue());
						super.widgetSelected(e);
					}
				});
				remove.setLayoutData(new GridData(
						GridData.HORIZONTAL_ALIGN_FILL
								| GridData.VERTICAL_ALIGN_FILL));

				GridLayout buttonLayout = new GridLayout();
				buttonLayout.numColumns = 1;
				buttonBar.setLayout(buttonLayout);

			buttonBar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
					| GridData.VERTICAL_ALIGN_BEGINNING));

			GridLayout addRemoveLayout = new GridLayout();
			addRemoveLayout.numColumns = 2;
			addRemoveComposite.setLayout(addRemoveLayout);
		}
