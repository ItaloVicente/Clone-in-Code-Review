	private CellEditor defaultCellEditor(ComboBoxCellEditor branchesEditor,
			Object element) {
		List<SyncRefEntity> refs = getAllRepoEntities((Repository) element).getRefList();
		String[] items = new String[refs.size()];
		for (int i = 0; i < refs.size(); i++)
			items[i] = refs.get(i).getDescription();

		branchesEditor.setItems(items);

		return branchesEditor;
