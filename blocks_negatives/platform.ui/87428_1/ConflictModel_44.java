		controller.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
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
					}
				} else if (BindingModel.PROP_BINDING_REMOVE.equals(event
						.getProperty())) {
					updateConflictsFor((BindingElement) event.getOldValue(),
							(BindingElement) event.getNewValue(), true);
