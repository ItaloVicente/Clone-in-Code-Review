		fWhenCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public final void selectionChanged(final SelectionChangedEvent event) {
				ContextElement context = (ContextElement) ((IStructuredSelection) event
						.getSelection()).getFirstElement();
				if (context != null) {
					keyController.getContextModel().setSelectedElement(context);
				}
