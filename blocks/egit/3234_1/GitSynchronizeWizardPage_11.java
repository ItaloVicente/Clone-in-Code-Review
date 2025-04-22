	private String getSelectedBranchName(ComboBoxCellEditor cellEditor,
			Object value) {
		int intValue = ((Integer) value).intValue();
		if (intValue == -1)
			return null;

		CCombo combo = (CCombo) cellEditor.getControl();
		String branch = combo.getItem(intValue);

		return branch;
