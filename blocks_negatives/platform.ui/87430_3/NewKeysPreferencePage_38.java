		fSchemeCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public final void selectionChanged(final SelectionChangedEvent event) {
				BusyIndicator.showWhile(fFilteredTree.getViewer().getTree().getDisplay(), new Runnable() {
					@Override
					public void run() {
						SchemeElement scheme = (SchemeElement) ((IStructuredSelection) event.getSelection())
								.getFirstElement();
						keyController.getSchemeModel().setSelectedElement(scheme);
					}
				});
			}
		});
		IPropertyChangeListener listener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getSource() == keyController.getSchemeModel()
						&& CommonModel.PROP_SELECTED_ELEMENT.equals(event
								.getProperty())) {
					Object newVal = event.getNewValue();
					StructuredSelection structuredSelection = newVal == null ? null
							: new StructuredSelection(newVal);
					fSchemeCombo.setSelection(structuredSelection, true);
				}
