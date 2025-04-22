		List<CellEditor> list = new ArrayList<CellEditor>(Arrays.asList(v
				.getCellEditors()));
		list.add(columnIndex, new TextCellEditor(v.getTable()));

		CellEditor[] editors = new CellEditor[list.size()];
		list.toArray(editors);
		v.setCellEditors(editors);

		List<Object> list2 = new ArrayList<Object>(Arrays.asList(v
				.getColumnProperties()));
		list2.add(columnIndex, "email");

		String[] columnProperties = new String[list2.size()];
		list2.toArray(columnProperties);
		v.setColumnProperties(columnProperties);
