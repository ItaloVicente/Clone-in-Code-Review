		}
		fViewer.setInput(fInput);
		return fViewer;
	}

	protected CheckboxTreeViewer getTreeViewer() {
		return fViewer;
	}

	protected Composite createSelectionButtons(Composite composite) {
		Composite buttonComposite = new Composite(composite, SWT.RIGHT);
		GridLayout layout = new GridLayout();
		layout.numColumns = 0;
