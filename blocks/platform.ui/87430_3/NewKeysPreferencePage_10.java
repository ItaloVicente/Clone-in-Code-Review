		IPropertyChangeListener whenListener = event -> {
			if (event.getSource() == keyController.getContextModel()
					&& CommonModel.PROP_SELECTED_ELEMENT.equals(event
							.getProperty())) {
				Object newVal = event.getNewValue();
				StructuredSelection structuredSelection = newVal == null ? null
						: new StructuredSelection(newVal);
				fWhenCombo.setSelection(structuredSelection, true);
