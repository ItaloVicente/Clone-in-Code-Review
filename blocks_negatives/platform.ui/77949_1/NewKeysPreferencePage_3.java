		conflictViewer
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

							bindingModel.setSelectedElement(binding);
							conflictViewer.setSelection(selection);

							boolean selectionVisible = false;
							TreeItem[] items = fFilteredTree.getViewer()
									.getTree().getItems();
							for (int i = 0; i < items.length; i++) {
								if (items[i].getData().equals(binding)) {
									selectionVisible = true;
									break;
								}
							}

							if (!selectionVisible) {
								fFilteredTree.getViewer().refresh();
								bindingModel.setSelectedElement(binding);
								conflictViewer.setSelection(selection);
							}
						}
					}
				});
