		ComboBoxCellEditor editor = new ComboBoxCellEditor(v.getTable(),
				new String[] { "M", "F" });
		editor.setActivationStyle(ComboBoxCellEditor.DROP_DOWN_ON_TRAVERSE_ACTIVATION
				| ComboBoxCellEditor.DROP_DOWN_ON_PROGRAMMATIC_ACTIVATION
				| ComboBoxCellEditor.DROP_DOWN_ON_MOUSE_ACTIVATION
				| ComboBoxCellEditor.DROP_DOWN_ON_KEY_ACTIVATION);
