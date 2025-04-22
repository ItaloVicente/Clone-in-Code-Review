		IPropertyChangeListener conflictsListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getSource() == keyController.getConflictModel()
						&& CommonModel.PROP_SELECTED_ELEMENT.equals(event
								.getProperty())) {
					if (keyController.getConflictModel().getConflicts() != null) {
						Object newVal = event.getNewValue();
						StructuredSelection structuredSelection = newVal == null ? null
								: new StructuredSelection(newVal);
						conflictViewer.setSelection(structuredSelection, true);
					}
				} else if (ConflictModel.PROP_CONFLICTS.equals(event
						.getProperty())) {
					conflictViewer.setInput(event.getNewValue());
				} else if (ConflictModel.PROP_CONFLICTS_ADD.equals(event
						.getProperty())) {
					conflictViewer.add(event.getNewValue());
				} else if (ConflictModel.PROP_CONFLICTS_REMOVE.equals(event
						.getProperty())) {
					conflictViewer.remove(event.getNewValue());
