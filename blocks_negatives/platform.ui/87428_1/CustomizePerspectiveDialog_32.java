				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						Category category = (Category) ((IStructuredSelection) event
								.getSelection()).getFirstElement();
						menuItemsViewer.setInput(category);
					}
