		this.activeNaturesList.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				removeButton.setEnabled(!ProjectNaturesPage.this.activeNaturesList.getSelection().isEmpty());
			}
		});
