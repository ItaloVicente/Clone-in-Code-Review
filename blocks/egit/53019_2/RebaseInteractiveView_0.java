		initInitialSelection(site);
	}

	private void initInitialSelection(IViewSite site) {
		this.initialSelection = new InitialSelection(
				site.getWorkbenchWindow().getSelectionService().getSelection());
		if (!isViewInputDerivableFromSelection(initialSelection.selection)) {
			this.initialSelection.activeEditor = site.getPage()
					.getActiveEditor();
		}
	}

	private static boolean isViewInputDerivableFromSelection(Object o) {
		return o instanceof StructuredSelection
				&& ((StructuredSelection) o).size() == 1;
