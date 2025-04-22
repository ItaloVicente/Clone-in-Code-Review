
		v.setLabelProvider(new LabelProvider());
		v.setContentProvider(ArrayContentProvider.getInstance());
		v.setCellModifier(modifier);
		v.setColumnProperties(new String[] { "column1" });
		v.setCellEditors(new CellEditor[] { new ComboBoxCellEditor(
				v.getTable(), new String[] { "Zero", "Ten", "Twenty", "Thirty",
						"Fourty", "Fifty", "Sixty", "Seventy", "Eighty",
						"Ninety" }) });
