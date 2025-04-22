		GridDataFactory.fillDefaults().grab(true, true)
				.hint(SWT.DEFAULT, TEXT_PREFERRED_HEIGHT).applyTo(text);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
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
				Object selected = structuredSelection.getFirstElement();
				if (selected instanceof RefUpdateElement)
					text.setText(getResult((RefUpdateElement) selected));
			}
		});
