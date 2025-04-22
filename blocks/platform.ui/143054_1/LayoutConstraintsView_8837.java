					applyPressed();
				}
			});
			buttonData.applyTo(applyButton);

			Button clearButton = new Button(buttonBar, SWT.PUSH);
			clearButton.setText("Reset");
			clearButton.addSelectionListener(new SelectionAdapter() {
				@Override
