		GridData textLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		textLayoutData.heightHint = TEXT_PREFERRED_HEIGHT;
		text.setLayoutData(textLayoutData);
		tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						ISelection selection = event.getSelection();
						if (!(selection instanceof IStructuredSelection)) {
							text.setText(EMPTY_STRING);
							return;
						}
						IStructuredSelection structuredSelection = (IStructuredSelection) selection;
						if (structuredSelection.size() != 1) {
							text.setText(EMPTY_STRING);
							return;
						}
						RefUpdateElement element = (RefUpdateElement) structuredSelection
								.getFirstElement();
						text.setText(getResult(element));
					}
				});
