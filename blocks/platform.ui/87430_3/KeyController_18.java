		addPropertyChangeListener(event -> {
			if (event.getSource() == conflictModel
					&& CommonModel.PROP_SELECTED_ELEMENT.equals(event
							.getProperty())) {
				if (event.getNewValue() != null) {
					bindingModel.setSelectedElement((ModelElement) event
							.getNewValue());
