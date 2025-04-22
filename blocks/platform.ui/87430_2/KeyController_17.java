		addPropertyChangeListener(event -> {
			if (event.getSource() == bindingModel
					&& CommonModel.PROP_SELECTED_ELEMENT.equals(event
							.getProperty())) {
				BindingElement binding = (BindingElement) event
						.getNewValue();
				if (binding == null) {
					conflictModel.setSelectedElement(null);
					return;
				}
				conflictModel.setSelectedElement(binding);
				ContextElement context = binding.getContext();
				if (context != null) {
					contextModel.setSelectedElement(context);
