		IPropertyChangeListener conflictsListener = event -> {
			if (event.getSource() == keyController.getConflictModel()
					&& CommonModel.PROP_SELECTED_ELEMENT.equals(event
							.getProperty())) {
				if (keyController.getConflictModel().getConflicts() != null) {
					Object newVal = event.getNewValue();
					StructuredSelection structuredSelection = newVal == null ? null
							: new StructuredSelection(newVal);
					conflictViewer.setSelection(structuredSelection, true);
