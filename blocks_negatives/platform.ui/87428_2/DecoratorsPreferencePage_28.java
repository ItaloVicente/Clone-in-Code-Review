                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
					public void selectionChanged(SelectionChangedEvent event) {
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
                    }
                });

        checkboxViewer.addCheckStateListener(new ICheckStateListener() {
            @Override
			public void checkStateChanged(CheckStateChangedEvent event) {
                checkboxViewer.setSelection(new StructuredSelection(event
                        .getElement()));
            }
        });
