        fViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
			public void selectionChanged(SelectionChangedEvent event) {
                access$setResult(((IStructuredSelection) event.getSelection())
                        .toList());
                updateOKStatus();
            }
        });
