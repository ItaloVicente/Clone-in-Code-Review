
		List<?> list = new ArrayList<CellEditor>(Arrays.asList(v
				.getCellEditors()));
		list.remove(emailIndex);
		CellEditor[] editors = new CellEditor[list.size()];
		list.toArray(editors);
		v.setCellEditors(editors);

		list = new ArrayList<Object>(Arrays.asList(v.getColumnProperties()));
		list.remove(emailIndex);
		String[] columnProperties = new String[list.size()];
		list.toArray(columnProperties);
		v.setColumnProperties(columnProperties);

		v.getTable().getColumn(emailIndex).dispose();

		v.refresh();
