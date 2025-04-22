                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
					public void selectionChanged(SelectionChangedEvent event) {
                        handleSelectionChanged();
                    }
                });
        typesListViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
			public void doubleClick(DoubleClickEvent event) {
                handleDoubleClick();
            }
        });
