
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
