		branchesEditor
				.setActivationStyle(ComboBoxCellEditor.DROP_DOWN_ON_KEY_ACTIVATION
						| ComboBoxCellEditor.DROP_DOWN_ON_MOUSE_ACTIVATION);
		((CCombo) branchesEditor.getControl()).addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CCombo combo = (CCombo) e.widget;
				TreeSelection sel = (TreeSelection) treeViewer.getSelection();
				int selectedIdx = combo.getSelectionIndex();
				Repository repo = (Repository) sel.getFirstElement();

				if (selectedIdx != -1) {
					selectedBranches.put(repo, combo.getItem(selectedIdx));
					setPageComplete(true);
				} else {
					selectedBranches.put(repo, null);
					setPageComplete(false);
				}
			}
		});
		dstColumn.setEditingSupport(new EditingSupport(treeViewer) {
