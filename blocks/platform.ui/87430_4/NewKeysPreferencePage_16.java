		fSchemeCombo.addSelectionChangedListener(event -> BusyIndicator.showWhile(fFilteredTree.getViewer().getTree().getDisplay(), () -> {
			SchemeElement scheme = (SchemeElement) ((IStructuredSelection) event.getSelection())
					.getFirstElement();
			keyController.getSchemeModel().setSelectedElement(scheme);
		}));
		IPropertyChangeListener listener = event -> {
			if (event.getSource() == keyController.getSchemeModel()
					&& CommonModel.PROP_SELECTED_ELEMENT.equals(event
							.getProperty())) {
				Object newVal = event.getNewValue();
				StructuredSelection structuredSelection = newVal == null ? null
						: new StructuredSelection(newVal);
				fSchemeCombo.setSelection(structuredSelection, true);
