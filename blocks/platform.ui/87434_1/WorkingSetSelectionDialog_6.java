        listViewer.addSelectionChangedListener(event -> handleSelectionChanged());
        listViewer.addDoubleClickListener(event -> {
			Object obj = ((IStructuredSelection) listViewer.getSelection())
					.getFirstElement();
			listViewer.setCheckedElements(new Object[] {obj});
			buttonWindowSet.setSelection(false);
			buttonNoSet.setSelection(false);
			buttonSelectedSets.setSelection(true);
			okPressed();
		});
        listViewer.addCheckStateListener(event -> {
			buttonWindowSet.setSelection(false);
			buttonNoSet.setSelection(false);
			buttonSelectedSets.setSelection(true);
