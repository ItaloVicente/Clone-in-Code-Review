		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			Object element = ((IStructuredSelection) event.getSelection())
					.getFirstElement();
			try {
				if (element instanceof ICategory) {
					descriptionText.setText(((ICategory) element)
							.getDescription());
				} else if (element instanceof IActivity) {
					descriptionText.setText(((IActivity) element)
							.getDescription());
				}
			} catch (NotDefinedException e) {
