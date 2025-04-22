        viewer.addOpenListener(new IOpenListener() {
            @Override
			public void open(OpenEvent event) {
                openAction.run();
            }
        });
        viewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
			public void selectionChanged(SelectionChangedEvent event) {
                handleSelectionChanged((IStructuredSelection) event
                        .getSelection());
            }
        });
