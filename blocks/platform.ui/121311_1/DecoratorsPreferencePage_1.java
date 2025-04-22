		checkboxViewer.addSelectionChangedListener(event -> {
			if (event.getSelection() instanceof IStructuredSelection) {
				IStructuredSelection sel = event.getStructuredSelection();
				DecoratorDefinition definition = (DecoratorDefinition) sel.getFirstElement();
				if (definition == null) {
					clearDescription();
				} else {
					showDescription(definition);
				}
			}
		});
