		addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getSource() == contextModel
						&& CommonModel.PROP_SELECTED_ELEMENT.equals(event
								.getProperty())) {
					updateBindingContext((ContextElement) event.getNewValue());
				}
