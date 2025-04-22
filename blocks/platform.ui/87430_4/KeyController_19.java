		addPropertyChangeListener(event -> {
			if (event.getSource() instanceof BindingElement
					&& ModelElement.PROP_MODEL_OBJECT.equals(event
							.getProperty())) {
				if (event.getNewValue() != null) {
					BindingElement element = (BindingElement) event
							.getSource();
					Object oldValue = event.getOldValue();
					Object newValue = event.getNewValue();
					if (oldValue instanceof Binding
							&& newValue instanceof Binding) {
						conflictModel.updateConflictsFor(element,
								((Binding) oldValue).getTriggerSequence(),
								((Binding) newValue).getTriggerSequence(),
								false);
					} else {
						conflictModel.updateConflictsFor(element, false);
					}
