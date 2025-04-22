		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ModelElement binding = (ModelElement) ((IStructuredSelection) event
						.getSelection()).getFirstElement();
				keyController.getBindingModel().setSelectedElement(binding);
			}
