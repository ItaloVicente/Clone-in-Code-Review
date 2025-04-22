				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						ModelElement binding = (ModelElement) ((IStructuredSelection) event.getSelection())
								.getFirstElement();
						BindingModel bindingModel = keyController
								.getBindingModel();
						if (binding != null
								&& binding != bindingModel.getSelectedElement()) {
							StructuredSelection selection = new StructuredSelection(
									binding);
