				}

				if (!selectionVisible) {
					fFilteredTree.getFilterControl().setText(""); //$NON-NLS-1$
					fFilteredTree.getViewer().refresh();
					bindingModel.setSelectedElement(binding);
					conflictViewer.setSelection(selection);
				}
			}
		});
