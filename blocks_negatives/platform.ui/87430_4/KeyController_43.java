		addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (BindingElement.PROP_TRIGGER.equals(event.getProperty())) {
					updateTrigger((BindingElement) event.getSource(),
							(KeySequence) event.getOldValue(),
							(KeySequence) event.getNewValue());
				}
