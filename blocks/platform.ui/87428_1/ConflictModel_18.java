		controller.addPropertyChangeListener(event -> {
			if (event.getSource() == ConflictModel.this
					&& CommonModel.PROP_SELECTED_ELEMENT.equals(event
							.getProperty())) {
				if (event.getNewValue() != null) {
					updateConflictsFor(
							(BindingElement) event.getOldValue(),
							(BindingElement) event.getNewValue());
					setConflicts((Collection) conflictsMap.get(event
							.getNewValue()));
				} else {
					setConflicts(null);
