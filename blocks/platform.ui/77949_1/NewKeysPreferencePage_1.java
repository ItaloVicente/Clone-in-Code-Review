	}

	private void createOverrideViewer(final Composite rightDataArea) {
		overrideViewer = createBasicConflictViewer(rightDataArea, NewKeysPreferenceMessages.OverrriddenBy_Text);
		overrideViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ModelElement binding = (ModelElement) ((IStructuredSelection) event.getSelection()).getFirstElement();
				BindingModel bindingModel = keyController.getBindingModel();
				if (binding != null && binding != bindingModel.getSelectedElement()) {
					bindingModel.setSelectedElement(binding);
					overrideViewer.setSelection(StructuredSelection.EMPTY);
				}
			}
		});
		IPropertyChangeListener overridesListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (ConflictModel.PROP_OVERRIDES.equals(event.getProperty())) {
					overrideViewer.setInput(event.getNewValue());
				}
			}
		};
		keyController.addPropertyChangeListener(overridesListener);
	}
