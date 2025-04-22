					minWidthText.setText("");
					maxWidthText.setText("");
					quantizedWidthText.setText("");
					minHeightText.setText("");
					maxHeightText.setText("");
					quantizedHeightText.setText("");
					fixedAreaText.setText("");
					applyPressed();
				}
			});
			buttonData.applyTo(clearButton);

			Button newViewButton = new Button(buttonBar, SWT.PUSH);
			newViewButton.setText("New View");
			newViewButton.addSelectionListener(new SelectionAdapter() {
				@Override
