		IPropertyChangeListener treeUpdateListener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getSource() == keyController.getBindingModel()
						&& CommonModel.PROP_SELECTED_ELEMENT.equals(event.getProperty())) {
					Object newVal = event.getNewValue();
					StructuredSelection structuredSelection = newVal == null ? null : new StructuredSelection(newVal);
					viewer.setSelection(structuredSelection, true);
				} else if (event.getSource() instanceof BindingElement
						&& ModelElement.PROP_MODEL_OBJECT.equals(event.getProperty())) {
					viewer.update(event.getSource(), null);
				} else if (BindingElement.PROP_CONFLICT.equals(event
						.getProperty())) {
					viewer.update(event.getSource(), null);
				} else if (BindingModel.PROP_BINDINGS.equals(event
						.getProperty())) {
					viewer.refresh();
				} else if (BindingModel.PROP_BINDING_ADD.equals(event
						.getProperty())) {
					viewer.add(keyController.getBindingModel(), event
							.getNewValue());
				} else if (BindingModel.PROP_BINDING_REMOVE.equals(event
						.getProperty())) {
					viewer.remove(event.getNewValue());
				} else if (BindingModel.PROP_BINDING_FILTER.equals(event
						.getProperty())) {
					viewer.refresh();
				}
