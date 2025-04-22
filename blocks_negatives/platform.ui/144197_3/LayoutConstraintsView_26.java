		{
			GridDataFactory buttonData = GridDataFactory.fillDefaults().grab(true, false);

			Button applyButton = new Button(buttonBar, SWT.PUSH);
			applyButton.setText("Apply");
			applyButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					applyPressed();
				}
			});
			buttonData.applyTo(applyButton);

			Button clearButton = new Button(buttonBar, SWT.PUSH);
			clearButton.setText("Reset");
			clearButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
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
				public void widgetSelected(SelectionEvent e) {
					try {
						getSite().getPage().showView("org.eclipse.ui.tests.layout.constraints.LayoutConstraintsView",
								"" + System.currentTimeMillis(), IWorkbenchPage.VIEW_ACTIVATE);
					} catch (PartInitException e1) {
						MessageDialog.openError(getSite().getShell(), "Error opening view", "Unable to open view.");
					}
