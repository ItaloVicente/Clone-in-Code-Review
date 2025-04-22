		conflictViewer.addSelectionChangedListener(event -> {
			ModelElement binding = (ModelElement) event.getStructuredSelection().getFirstElement();
			BindingModel bindingModel = keyController.getBindingModel();
			if (binding != null && binding != bindingModel.getSelectedElement()) {
				StructuredSelection selection = new StructuredSelection(binding);

				bindingModel.setSelectedElement(binding);
				conflictViewer.setSelection(selection);

				boolean selectionVisible = false;
				TreeItem[] items = fFilteredTree.getViewer().getTree().getItems();
				for (TreeItem item : items) {
					if (item.getData().equals(binding)) {
						selectionVisible = true;
						break;
