        checkboxViewer
                .addSelectionChangedListener(event -> {
				    if (event.getSelection() instanceof IStructuredSelection) {
				        IStructuredSelection sel = (IStructuredSelection) event
				                .getSelection();
				        DecoratorDefinition definition = (DecoratorDefinition) sel
				                .getFirstElement();
				        if (definition == null) {
							clearDescription();
						} else {
							showDescription(definition);
						}
				    }
				});
