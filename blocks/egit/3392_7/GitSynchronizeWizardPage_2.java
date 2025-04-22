		branchesEditor
				.setActivationStyle(ComboBoxCellEditor.DROP_DOWN_ON_KEY_ACTIVATION
						| ComboBoxCellEditor.DROP_DOWN_ON_MOUSE_ACTIVATION);
		((CCombo) branchesEditor.getControl()).addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CCombo combo = (CCombo) e.widget;
				setPageComplete(combo.getSelectionIndex() != -1);
			}
		});
		dstColumn.setEditingSupport(new EditingSupport(treeViewer) {
