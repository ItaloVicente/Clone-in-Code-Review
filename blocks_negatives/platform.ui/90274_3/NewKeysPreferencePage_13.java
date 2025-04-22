		filtersButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				KeysPreferenceFiltersDialog dialog = new KeysPreferenceFiltersDialog(getShell());
				dialog.setFilterActionSet(fFilterActionSetContexts);
				dialog.setFilterInternal(fFilterInternalContexts);
				dialog.setFilterUncategorized(fFilteredTree.isFilteringCategories());
				if (dialog.open() == Window.OK) {
					fFilterActionSetContexts = dialog.getFilterActionSet();
					fFilterInternalContexts = dialog.getFilterInternal();
					fFilteredTree.filterCategories(dialog.getFilterUncategorized());

					keyController.filterContexts(fFilterActionSetContexts, fFilterInternalContexts);

					ISelection currentContextSelection = fWhenCombo.getSelection();
					fWhenCombo.setInput(keyController.getContextModel());
					fWhenCombo.setSelection(currentContextSelection);
				}
