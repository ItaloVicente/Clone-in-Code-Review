        viewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
			public void selectionChanged(SelectionChangedEvent event) {
                handleSelectionChanged(event);
            }
        });
        viewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
			public void doubleClick(DoubleClickEvent event) {
                handleDoubleClick(event);
            }
        });
