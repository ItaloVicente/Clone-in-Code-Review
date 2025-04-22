		restore.addSelectionListener(widgetSelectedAdapter(event -> {
			try {
				fFilteredTree.setRedraw(false);
				BindingModel bindingModel = keyController.getBindingModel();
				bindingModel
						.restoreBinding(keyController.getContextModel());
			} finally {
				fFilteredTree.setRedraw(true);
