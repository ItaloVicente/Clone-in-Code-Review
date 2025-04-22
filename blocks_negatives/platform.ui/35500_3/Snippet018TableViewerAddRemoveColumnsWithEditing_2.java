		v.setCellEditors(new CellEditor[] { new TextCellEditor(v.getTable()),
				new TextCellEditor(v.getTable()),
				new TextCellEditor(v.getTable()) });
		v.setCellModifier(new MyCellModifier(v));

		v.setColumnProperties(new String[] { "givenname", "surname" });
