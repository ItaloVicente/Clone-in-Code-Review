				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						selectedActionSet[0] = (ActionSet) ((IStructuredSelection) event
								.getSelection()).getFirstElement();
						actionSetMenuViewer.setInput(menuItems);
						actionSetToolbarViewer.setInput(toolBarItems);
					}
