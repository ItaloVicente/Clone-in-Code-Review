        listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
			public void selectionChanged(SelectionChangedEvent event) {
                handleSelectionChanged();
            }
        });
        listViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
			public void doubleClick(DoubleClickEvent event) {
            	Object obj = ((IStructuredSelection) listViewer.getSelection())
						.getFirstElement();
				listViewer.setCheckedElements(new Object[] {obj});
				buttonWindowSet.setSelection(false);
				buttonNoSet.setSelection(false);
				buttonSelectedSets.setSelection(true);
            	okPressed();
            }
        });
        listViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				buttonWindowSet.setSelection(false);
				buttonNoSet.setSelection(false);
				buttonSelectedSets.setSelection(true);
			}
