		restore.addSelectionListener(new SelectionAdapter() {
			@Override
			public final void widgetSelected(final SelectionEvent event) {
				try {
					fFilteredTree.setRedraw(false);
					BindingModel bindingModel = keyController.getBindingModel();
					bindingModel
							.restoreBinding(keyController.getContextModel());
				} finally {
					fFilteredTree.setRedraw(true);
				}
