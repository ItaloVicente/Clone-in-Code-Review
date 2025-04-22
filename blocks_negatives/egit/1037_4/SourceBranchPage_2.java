		addSelectionListener(new SelectionChangeListener() {
			public void selectionChanged() {
				selectB.setEnabled(selectedRefs.size() != availableRefs.size());
				unselectB.setEnabled(selectedRefs.size() != 0);
			}
		});

