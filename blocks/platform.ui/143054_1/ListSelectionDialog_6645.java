		Dialog.applyDialogFont(composite);

		return composite;
	}

	protected CheckboxTableViewer getViewer() {
		return listViewer;
	}

	private void initializeViewer() {
		listViewer.setInput(inputElement);
	}

	@Override
